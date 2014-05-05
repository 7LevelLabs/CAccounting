package ua.its.slot7.caccounting.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.helper.InvoiceHelper;
import ua.its.slot7.caccounting.helper.PersonHelper;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BLServiceIT extends Assert {

	@Autowired
	private BLServiceAvatar blService;

	@Autowired
	private InvoiceServiceAvatar invoiceService;

	@Autowired
	private PersonServiceAvatar personService;

	@Autowired
	private UserServiceAvatar userService;

	@Autowired
	private PersonHelper personHelper;

	@Autowired
	private InvoiceHelper invoiceHelper;


	@Test
	public void testPersonGetInvoicesOverdue() throws Exception {

		User user = new User();
		user.setNick("User1");
		user.setEmail("user1@test.com");
		user.setDiscount(10);
		user.setPreparedBy("User 1");

		Person person = personHelper.getNewPerson("nick",
			"name",
			"email",
			"phone",
			user,
			PersonHelper.PersonDiscountSourceSign.USER);
		person.setPreparedFor("Person 1");

		Invoice invoice1 = invoiceHelper.getDummyInvoice(person);
		Invoice invoice2 = invoiceHelper.getDummyInvoice(person);
		invoice2.getPaymentState().setPaid(true);
		Invoice invoice3 = invoiceHelper.getDummyInvoice(person);
		//for the test purpose
		invoice3.setDatePaymentDue(new Date((new Date().getTime() - 100000)));

		userService.createUser(user);
		personService.createPerson(person);

		invoiceService.createInvoice(invoice1);
		invoiceService.createInvoice(invoice2);
		invoiceService.createInvoice(invoice3);

		List<Invoice> invoicesOverdue = blService.personGetInvoicesOverdue(person);

		org.assertj.core.api.Assertions
			.assertThat(invoicesOverdue)
			.hasSize(1);

	}

	@Test
	public void testPersonGetOverdueInvoicesNumbers() throws Exception {

		User user = new User();
		user.setNick("User1");
		user.setEmail("user1@test.com");
		user.setDiscount(10);
		user.setPreparedBy("User 1");

		Person person = personHelper.getNewPerson("nick",
			"name",
			"email",
			"phone",
			user,
			PersonHelper.PersonDiscountSourceSign.USER);
		person.setPreparedFor("Person 1");

		Invoice invoice1 = invoiceHelper.getDummyInvoice(person);
		invoice1.setNumber("001");
		Invoice invoice2 = invoiceHelper.getDummyInvoice(person);
		invoice2.setNumber("002");
		invoice2.getPaymentState().setPaid(true);
		Invoice invoice3 = invoiceHelper.getDummyInvoice(person);
		invoice3.setNumber("003");

		//for the test purpose
		invoice3.setDatePaymentDue(new Date((new Date().getTime() - 100000)));

		userService.createUser(user);
		personService.createPerson(person);

		invoiceService.createInvoice(invoice1);
		invoiceService.createInvoice(invoice2);
		invoiceService.createInvoice(invoice3);

		List<String> invoicesOverdue = blService.personGetOverdueInvoicesNumbers(person);

		org.assertj.core.api.Assertions
			.assertThat(invoicesOverdue)
			.hasSize(1)
			.contains("003")
			.doesNotContain("001", "002");
	}

	@Test
	public void testPersonGetInvoicesOverdueNumber() throws Exception {
		User user = new User();
		user.setNick("User1");
		user.setEmail("user1@test.com");
		user.setDiscount(10);
		user.setPreparedBy("User 1");

		Person person = personHelper.getNewPerson("nick",
			"name",
			"email",
			"phone",
			user,
			PersonHelper.PersonDiscountSourceSign.USER);
		person.setPreparedFor("Person 1");

		Invoice invoice1 = invoiceHelper.getDummyInvoice(person);
		invoice1.setNumber("001");
		Invoice invoice2 = invoiceHelper.getDummyInvoice(person);
		invoice2.setNumber("002");
		invoice2.getPaymentState().setPaid(true);
		Invoice invoice3 = invoiceHelper.getDummyInvoice(person);
		invoice3.setNumber("003");

		//for the test purpose
		invoice3.setDatePaymentDue(new Date((new Date().getTime() - 100000)));

		userService.createUser(user);
		personService.createPerson(person);

		invoiceService.createInvoice(invoice1);
		invoiceService.createInvoice(invoice2);
		invoiceService.createInvoice(invoice3);

		int invoicesOverdueNumber = blService.personGetInvoicesOverdueNumber(person);

		assertTrue(invoicesOverdueNumber == 1);
	}

	@Test
	public void testPersonHasOverdueInvoices() throws Exception {

		User user = new User();
		user.setNick("User1");
		user.setEmail("user1@test.com");
		user.setDiscount(10);
		user.setPreparedBy("User 1");

		Person person = personHelper.getNewPerson("nick",
			"name",
			"email",
			"phone",
			user,
			PersonHelper.PersonDiscountSourceSign.USER);
		person.setPreparedFor("Person 1");

		Invoice invoice1 = invoiceHelper.getDummyInvoice(person);
		invoice1.setNumber("001");
		Invoice invoice2 = invoiceHelper.getDummyInvoice(person);
		invoice2.setNumber("002");
		invoice2.getPaymentState().setPaid(true);
		Invoice invoice3 = invoiceHelper.getDummyInvoice(person);
		invoice3.setNumber("003");

		//for the test purpose
		invoice3.setDatePaymentDue(new Date((new Date().getTime() - 100000)));

		userService.createUser(user);
		personService.createPerson(person);

		invoiceService.createInvoice(invoice1);
		invoiceService.createInvoice(invoice2);
		invoiceService.createInvoice(invoice3);

		boolean isOverdues = blService.personHasOverdueInvoices(person);

		assertTrue(isOverdues);
	}
}