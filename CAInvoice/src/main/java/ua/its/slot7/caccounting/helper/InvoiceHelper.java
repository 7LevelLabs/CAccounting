package ua.its.slot7.caccounting.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.person.Person;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

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

	@Autowired
	private InvoiceLineHelper invoiceLineHelper;

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

	public InvoiceVO getInvoiceVO(final Invoice invoice) {
		InvoiceVO invoiceVO = new InvoiceVO(invoice.getNumber(),
			invoice.getPerson().getUser().getPreparedBy(),
			invoice.getPerson().getPreparedFor(),
			invoice.getTotal().toString(),
			invoice.getDateIssue().toString(),
			invoice.getDatePaymentDue().toString(),
			invoice.getSubTotal().toString(),
			Integer.toString(invoice.getDiscount()));

		for (InvoiceLine invoiceLine : invoice.getInvoicesLines()) {
			invoiceVO.addLine(invoiceLineHelper.getInvoiceLineVO(invoiceLine));
		}
		return invoiceVO;
	}

	public class InvoiceVO {

		private String number;
		private String preparedBy;
		private String preparedFor;
		private String total;
		private String issueDate;
		private String paymentDueDate;
		private String subtotal;
		private String discount;

		private Collection<InvoiceLineHelper.InvoiceLineVO> lines;

		public InvoiceVO(final String number,
				   final String preparedBy,
				   final String preparedFor,
				   final String total,
				   final String issueDate,
				   final String paymentDueDate,
				   final String subtotal,
				   final String discount) {
			this.number = number;
			this.preparedBy = preparedBy;
			this.preparedFor = preparedFor;
			this.total = total;
			this.issueDate = issueDate;
			this.paymentDueDate = paymentDueDate;
			this.subtotal = subtotal;
			this.discount = discount;

			lines = new LinkedList<InvoiceLineHelper.InvoiceLineVO>();
		}

		public void addLine(final InvoiceLineHelper.InvoiceLineVO lineVO) {
			this.lines.add(lineVO);
		}

		public String getNumber() {
			return number;
		}

		public String getPreparedBy() {
			return preparedBy;
		}

		public String getPreparedFor() {
			return preparedFor;
		}

		public String getTotal() {
			return total;
		}

		public String getIssueDate() {
			return issueDate;
		}

		public String getPaymentDueDate() {
			return paymentDueDate;
		}

		public String getSubtotal() {
			return subtotal;
		}

		public String getDiscount() {
			return discount;
		}

		public Collection<InvoiceLineHelper.InvoiceLineVO> getLines() {
			return lines;
		}
	}

}
