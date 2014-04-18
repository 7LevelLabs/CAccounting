package ua.its.slot7.caccounting.model.person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.its.slot7.caccounting.model.user.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Alex Velichko
 *         17.04.14 : 15:55
 */
public class PersonTest extends Assert {

	private User user;

	private Person person;

	private static Validator validator;

	@BeforeClass
	public static void setUpBeforeClass() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void setUp() throws Exception {
		String uEmail = "test@test.com";
		String uNick = "Alex";
		String uPass = "b799931b47707582e32947d";

		this.user = new User(uNick, uEmail, uPass);
		this.user.setApiCode("67419b24-4cbb-4d2f-af43-48cf28951b5a");

		this.person = new Person("personNick", "personName", "personEMail", "personPhone", this.user);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors() {
		Person aPerson = new Person(" ", " ", " ", " ");
		aPerson = new Person(" ", " ", " ", " ", null);
	}

	@Test
	public void testToString() throws Exception {
		assertEquals(this.person.toString(),
			"Person{id=0, nick='personNick', name='personName', phone='personPhone', email='personEMail', invoices=[], user=User{id=0, nick='Alex', email='test@test.com', pass='b799931b47707582e32947d', apiCode='67419b24-4cbb-4d2f-af43-48cf28951b5a', isActive=false, lastUpdate=null, preparedBy='Alex', discount=0}, lastUpdate=null, preparedFor='personName', discount=0}");
	}

	@Test
	public void testValidation() {
		person.setNick(" ");
		person.setName(" ");
		person.setPreparedFor(" ");
		person.setEmail(" ");
		person.setUser(null);
		person.setDiscount(-1);

		Set<ConstraintViolation<Person>> constraintViolations =
			validator.validate(person);
		org.assertj.core.api.Assertions
			.assertThat(constraintViolations)
			.hasSize(6);
	}

}
