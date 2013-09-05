package ua.its.slot7.caccounting.model.invoice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;

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
		invoice = new Invoice ();
		invoiceLine1 = new InvoiceLine();
		invoiceLine2 = new InvoiceLine();
		invoice.getInvoicesLines().add(invoiceLine1);
		invoice.getInvoicesLines().add(invoiceLine2);
	}

	@Test
	public void testCalcInvoiceSum() throws Exception {
		float res;
		invoiceLine1.setLinePrice(12.1f);
		invoiceLine1.setLineQt(2);
		invoiceLine2.setLinePrice(37.23f);
		invoiceLine2.setLineQt(5);
		res = invoice.calcInvoiceSum();
		assertEquals(210.35f,res,0.0001f);
	}

	@Test
	public void testCalcInvoiceSumEmptyLine() throws Exception {
		float res;
		invoiceLine1.setLinePrice(12.1f);
		invoiceLine1.setLineQt(2);
		res = invoice.calcInvoiceSum();
		assertEquals(24.2f,res,0.0001f);
	}

	@Test
	public void testCalcInvoiceSumEmptyAllLines() throws Exception {
		float res;
		res = invoice.calcInvoiceSum();
		assertEquals(0f,res,0.0001f);
	}
}
