package ua.its.slot7.caccounting.helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

/**
 * @author Alex Velichko
 *         17.04.14 : 18:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class PersonHelperTest extends Assert {

	@Autowired
	private PersonHelper personHelper;

	private User aUser;

	private Person aPerson;

	@Before
	public void setUp() throws Exception {
		aUser = new User("userNick", "userEmail", "userPass");
		aPerson = new Person("personNick", "personName", "personEmail", "personPhone", aUser);
	}

	@Test
	public void testGetInvoiceNumberFormat() throws Exception {
		assertEquals(personHelper.getInvoiceNumberFormat(), "00000");
	}

	@Test
	public void testGetInvoiceNumberDefault() throws Exception {
		assertEquals(personHelper.getInvoiceNumberDefault(), "00001");
	}

	@Test
	public void testGetPersonDefaultDiscount() throws Exception {
		assertEquals(personHelper.getPersonDefaultDiscount(), 5);
	}

	@Test
	public void testGetNewPerson() throws Exception {
		User user = new User("userNick", "userEmail", "userPass");

		//from User
		user.setDiscount(7);
		Person person = personHelper.getNewPerson("personNick",
			"personName",
			"personEmail",
			"personPhone",
			user,
			PersonHelper.PersonDiscountSourceSign.USER);

		assertEquals(person.getNick(), "personNick");
		assertEquals(person.getName(), "personName");
		assertEquals(person.getEmail(), "personEmail");
		assertEquals(person.getPhone(), "personPhone");

		assertEquals(person.getDiscount(), 7);

		//from properties
		person = personHelper.getNewPerson("personNick",
			"personName",
			"personEmail",
			"personPhone",
			user,
			PersonHelper.PersonDiscountSourceSign.PROPERTIES);

		assertEquals(person.getDiscount(), 5);

	}

	@Test
	public void testGenerateNextInvoiceNumber() throws Exception {
		Invoice invoice = new Invoice();

		assertEquals(personHelper.generateNextInvoiceNumber(aPerson), "0-00001");
		invoice.setNumber(personHelper.generateNextInvoiceNumber(aPerson));

		aPerson.getInvoices().add(invoice);

		assertEquals(personHelper.generateNextInvoiceNumber(aPerson), "0-00002");
	}

	@Test
	public void testTheLastInvoice() throws Exception {
		Invoice invoice = new Invoice();
		invoice.setNumber(personHelper.generateNextInvoiceNumber(aPerson));

		Thread.sleep(500);

		Invoice invoice2 = new Invoice();
		invoice2.setNumber(personHelper.generateNextInvoiceNumber(aPerson));

		aPerson.getInvoices().add(invoice2);
		aPerson.getInvoices().add(invoice);

		assertEquals(personHelper.theLastInvoice(aPerson), invoice2);

	}
}
