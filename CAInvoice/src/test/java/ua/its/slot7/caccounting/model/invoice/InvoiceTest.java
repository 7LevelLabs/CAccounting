package ua.its.slot7.caccounting.model.invoice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;

import java.math.BigDecimal;
import java.util.List;

/**
 * CAccounting
 * 07.08.13 : 18:25
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class InvoiceTest extends Assert {

	private Invoice invoice;
	private InvoiceLine invoiceLine1, invoiceLine2;

	@Before
	public void setUp() throws Exception {
		invoice = new Invoice();
		invoiceLine1 = new InvoiceLine();
		invoiceLine2 = new InvoiceLine();
		invoice.getInvoicesLines().add(invoiceLine1);
		invoice.getInvoicesLines().add(invoiceLine2);
	}

	@Test
	public void testLinesNumber() throws Exception {

		List<InvoiceLine> invoiceLines = this.invoice.getInvoicesLines();

		org.assertj.core.api.Assertions
			.assertThat(invoiceLines)
			.isNotEmpty();
		org.assertj.core.api.Assertions
			.assertThat(invoiceLines)
			.hasSize(2);
	}

	@Test
	public void testCalcInvoiceSum() throws Exception {
		BigDecimal res;
		invoiceLine1.setLinePrice(BigDecimal.valueOf(12.1));
		invoiceLine1.setLineQt(2);
		invoiceLine2.setLinePrice(BigDecimal.valueOf(37.23));
		invoiceLine2.setLineQt(5);
		res = invoice.calcInvoiceSum();
		assertEquals(BigDecimal.valueOf(210.35), res);
	}

	@Test
	public void testCalcInvoiceSumEmptyLine() throws Exception {
		BigDecimal res;
		invoiceLine1.setLinePrice(BigDecimal.valueOf(12.1));
		invoiceLine1.setLineQt(2);
		res = invoice.calcInvoiceSum();
		assertEquals(BigDecimal.valueOf(24.2), res);
	}

	@Test
	public void testCalcInvoiceSumEmptyAllLines() throws Exception {
		BigDecimal res;
		res = invoice.calcInvoiceSum();
		assertEquals(BigDecimal.valueOf(0), res);
	}
}
