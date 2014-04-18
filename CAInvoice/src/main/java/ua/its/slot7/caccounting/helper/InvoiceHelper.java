package ua.its.slot7.caccounting.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.person.Person;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alex Velichko
 *         18.04.14 : 13:57
 */
@Component
public class InvoiceHelper {

	@Value("${invoice.duepayment.period.default}")
	private String duePaymentPeriodDefault;

	@Autowired
	private PersonHelper personHelper;

	public String getDuePaymentPeriodDefault() {
		return duePaymentPeriodDefault;
	}

	public Date getDuePaymentDate(final Date periodStart) {
		return new Date(periodStart.getTime() +
			Long.parseLong(this.getDuePaymentPeriodDefault()) * 24L * 60L * 60L * 1000L);
	}

	public Invoice getNewInvoice(final Person person) {
		Invoice invoice = new Invoice(person);

		invoice.setNumber(personHelper.generateNextInvoiceNumber(person));

		//payment due date

		Date date = new Date();
		invoice.setDateIssue(date);
		invoice.setDateIssue(this.getDuePaymentDate(date));

		//defaults
		//discount
		invoice.setDiscount(person.getDiscount());

		return invoice;
	}

	/**
	 * Calc subTotal {@link ua.its.slot7.caccounting.model.invoice.Invoice#subTotal} of this Invoice
	 *
	 * @return Total of the Invoice
	 */
	public BigDecimal calcInvoiceSubTotal(final Invoice invoice) {
		BigDecimal res = new BigDecimal(0);
		for (InvoiceLine il : invoice.getInvoicesLines()) {
			res = res.add(il.calcLineTotal());
		}
		return res;
	}

	/**
	 * Calc subTotal {@link ua.its.slot7.caccounting.model.invoice.Invoice#subTotal} of this Invoice
	 *
	 * @return Total of the Invoice
	 */
	public BigDecimal calcInvoiceTotal(final Invoice invoice) {
		BigDecimal res;

		res = this.calcInvoiceSubTotal(invoice).subtract(
			this.calcInvoiceSubTotal(invoice)
				.divide(BigDecimal.valueOf(100))
				.multiply(BigDecimal.valueOf(invoice.getDiscount()))
		);

		return res;
	}


}
