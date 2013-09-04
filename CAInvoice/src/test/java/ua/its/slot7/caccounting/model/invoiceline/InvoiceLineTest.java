package ua.its.slot7.caccounting.model.invoiceline;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * CAccounting
 * 07.08.13 : 16:56
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class InvoiceLineTest extends Assert {

	private InvoiceLine invoiceLine;

	@BeforeClass
	public void setUp() throws Exception {
		invoiceLine = new InvoiceLine();
	}

	@Test
	public void testCalcLineSum() throws Exception {
		invoiceLine.setLinePrice(21.1f);
		invoiceLine.setLineQt(12);
		float res = invoiceLine.calcLineSum();
		assertEquals(253.2f,res,0.0001f);
	}

	@Test
	public void testCalcLineSum01() throws Exception {
		invoiceLine.setLinePrice(0);
		invoiceLine.setLineQt(12);
		float res = invoiceLine.calcLineSum();
		assertTrue(res==0);
	}

	@Test
	public void testCalcLineSum10() throws Exception {
		invoiceLine.setLinePrice(21.1f);
		invoiceLine.setLineQt(0);
		float res = invoiceLine.calcLineSum();
		assertTrue(res==0);
	}
}
