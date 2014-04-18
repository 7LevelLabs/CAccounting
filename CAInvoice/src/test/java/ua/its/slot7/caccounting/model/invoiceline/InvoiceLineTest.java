package ua.its.slot7.caccounting.model.invoiceline;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

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

	private static Validator validator;

	@BeforeClass
	public static void setUpBeforeClass() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void setUp() throws Exception {
		invoiceLine = new InvoiceLine();
	}

	@Test
	public void testCalcLineSum() throws Exception {
		invoiceLine.setLinePrice(new BigDecimal(21.1));
		invoiceLine.setLineQt(12);
		BigDecimal res = invoiceLine.calcLineSum();
		assertEquals(new BigDecimal("253.20"), res.setScale(2, RoundingMode.HALF_EVEN));
	}

	@Test
	public void testCalcLineSum01() throws Exception {
		invoiceLine.setLinePrice(new BigDecimal(0));
		invoiceLine.setLineQt(12);
		BigDecimal res = invoiceLine.calcLineSum();
		assertEquals(new BigDecimal("0.0"), res);
	}

	@Test
	public void testCalcLineSum10() throws Exception {
		invoiceLine.setLinePrice(new BigDecimal(21.1));
		invoiceLine.setLineQt(0);
		BigDecimal res = invoiceLine.calcLineSum();
		assertEquals(new BigDecimal("0.0"), res);
	}

	@Test
	public void testValidation() {
		invoiceLine.setInvoice(null);
		invoiceLine.setLineText(" ");
		invoiceLine.setLineQt(0);
		invoiceLine.setLinePrice(BigDecimal.valueOf(-1));
		invoiceLine.setLineSum(BigDecimal.valueOf(-1));

		Set<ConstraintViolation<InvoiceLine>> constraintViolations =
			validator.validate(invoiceLine);
		org.assertj.core.api.Assertions
			.assertThat(constraintViolations)
			.hasSize(5);

	}
}
