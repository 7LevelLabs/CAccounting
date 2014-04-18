package ua.its.slot7.caccounting.helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author Alex Velichko
 *         18.04.14 : 14:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class InvoiceHelperTest extends Assert {

	@Autowired
	private InvoiceHelper invoiceHelper;

	private Invoice invoice;

	private InvoiceLine invoiceLine1;
	private InvoiceLine invoiceLine2;

	@Before
	public void setUp() throws Exception {
		invoice = new Invoice();
		invoiceLine1 = new InvoiceLine();
		invoiceLine2 = new InvoiceLine();
		invoice.getInvoicesLines().add(invoiceLine1);
		invoice.getInvoicesLines().add(invoiceLine2);
	}

	@Test
	public void testGetDuePaymentPeriodDefault() throws Exception {
		assertEquals(invoiceHelper.getDuePaymentPeriodDefault(), "5");
	}

	@Test
	public void testGetDuePaymentDate() throws Exception {
		Date date = new Date();

		Date dateEnd = invoiceHelper.getDuePaymentDate(date);
		long distance = dateEnd.getTime() - date.getTime();
		long period = distance / 1000L / 60L / 60L / 24L;

		assertEquals(period, Long.parseLong(invoiceHelper.getDuePaymentPeriodDefault()));
	}

	@Test
	public void testCalcInvoiceTotal() throws Exception {
		BigDecimal res;

		invoiceLine1.setLinePrice(BigDecimal.valueOf(50));
		invoiceLine1.setLineQt(1);
		invoiceLine2.setLinePrice(BigDecimal.valueOf(50));
		invoiceLine2.setLineQt(1);

		invoice.setDiscount(5);
		res = invoiceHelper.calcInvoiceTotal(invoice);
		assertEquals(
			new BigDecimal(95).setScale(1, RoundingMode.HALF_EVEN),
			res.setScale(1, RoundingMode.HALF_EVEN));
	}

	@Test
	public void testCalcInvoiceSubTotal() throws Exception {
		BigDecimal res;
		invoiceLine1.setLinePrice(BigDecimal.valueOf(12.1));
		invoiceLine1.setLineQt(2);
		invoiceLine2.setLinePrice(BigDecimal.valueOf(37.23));
		invoiceLine2.setLineQt(5);
		res = invoiceHelper.calcInvoiceSubTotal(invoice);
		assertEquals(BigDecimal.valueOf(210.35), res.setScale(2, RoundingMode.HALF_EVEN));
	}

}
