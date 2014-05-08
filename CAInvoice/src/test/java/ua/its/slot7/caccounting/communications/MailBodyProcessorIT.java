package ua.its.slot7.caccounting.communications;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.its.slot7.caccounting.helper.InvoiceHelper;
import ua.its.slot7.caccounting.helper.PersonHelper;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class MailBodyProcessorIT extends Assert {

	@Autowired
	private MailBodyProcessor mailBodyProcessor;

	@Autowired
	private PersonHelper personHelper;

	@Autowired
	private InvoiceHelper invoiceHelper;

	@Test
	public void testUserWAboard() throws Exception {

		String result = mailBodyProcessor.userWelcomeAboard("Nick", "test@test.com");

		assertTrue(result.contains("Hi, Nick ( test@test.com )!"));
	}

	@Test
	public void testAccessRecoverPh1() throws Exception {

		String result = mailBodyProcessor.userAccessRecoverPh1("Nick",
			"EMail",
			"Code",
			"ApplicationURL",
			"Ph2URL",
			"ExpTime");

		assertTrue(result.contains("Hi, Nick ( EMail )!"));
		assertTrue(result.contains("Please, pay your attention : period of use of the code will expire on ExpTime"));
	}

	@Test
	public void testAccessRecoverPh2() throws Exception {

		String result = mailBodyProcessor.userAccessRecoverPh2("Nick", "EMail", "ApplicationURL");

		assertTrue(result.contains("Hi, Nick ( EMail )!"));
		assertTrue(result.contains("<p>Your access to <strong><a href='ApplicationURL' target='_blank'>Cloud Accounting</a></strong> was just recovered.</p>"));
	}

	@Test
	public void testPersonOverdueInvoicesReminder() throws Exception {

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

		List<Invoice> invoiceList = new LinkedList<Invoice>();
		invoiceList.add(invoice1);
		invoiceList.add(invoice2);
		invoiceList.add(invoice3);

		String result = mailBodyProcessor.personOverdueInvoicesReminder(user,
			person,
			invoiceList);

		assertTrue(result.contains("Hi, name ( email )!"));
		assertTrue(result.contains("<b>User 1</b> ( <b>user1@test.com</b>"));
	}

	@Test
	public void testGetEmailEncoding() throws Exception {
		assertEquals(mailBodyProcessor.getEmailEncoding(), "UTF-8");
	}
}