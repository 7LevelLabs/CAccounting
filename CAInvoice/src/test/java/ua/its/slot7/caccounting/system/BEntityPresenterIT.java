package ua.its.slot7.caccounting.system;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class BEntityPresenterIT extends Assert {

	@Autowired
	private BEntityPresenter entityPresenter;

	@Test
	public void testPresentToHTML() throws Exception {

		User user = new User();
		user.setPreparedBy("User");

		Person person = new Person();
		person.setUser(user);
		person.setPreparedFor("Person");

		Invoice invoice = new Invoice();

		invoice.setNumber("001-001001");
		invoice.setPerson(person);
		//TODO Fix round
		invoice.setTotal(new BigDecimal(95.40).setScale(2, 2));
		invoice.setDateIssue(new Date(1398351236425L));
		invoice.setDatePaymentDue(new Date(1398351236425L));
		invoice.setSubTotal(new BigDecimal(106).setScale(2));
		invoice.setDiscount(10);

		InvoiceLine invoiceLine1, invoiceLine2;

		invoiceLine1 = new InvoiceLine();
		invoiceLine1.setInvoice(invoice);
		invoiceLine1.setLineText("Line 1");
		invoiceLine1.setLinePrice(new BigDecimal(10).setScale(2));
		invoiceLine1.setLineQt(5);
		invoiceLine1.setLineTax(new BigDecimal(7).setScale(2));
		invoiceLine1.setLineTotal(new BigDecimal(43).setScale(2));

		invoiceLine2 = new InvoiceLine();
		invoiceLine2.setInvoice(invoice);
		invoiceLine2.setLineText("Line 2");
		invoiceLine2.setLinePrice(new BigDecimal(10).setScale(2));
		invoiceLine2.setLineQt(7);
		invoiceLine2.setLineTax(new BigDecimal(7).setScale(2));
		invoiceLine2.setLineTotal(new BigDecimal(63).setScale(2));

		invoice.getInvoicesLines().add(invoiceLine1);
		invoice.getInvoicesLines().add(invoiceLine2);

		String result = entityPresenter.presentToHTML(invoice);

		assertTrue(result.contains("<p>Prepared for : Person</p>"));
		assertTrue(result.contains("<p>Subtotal : 106.00</p>"));
	}

	@Test
	public void testPresentToPDF1() throws Exception {
		User user = new User();
		user.setPreparedBy("User");

		Person person = new Person();
		person.setUser(user);
		person.setPreparedFor("Person");

		Invoice invoice = new Invoice();

		invoice.setNumber("001-001001");
		invoice.setPerson(person);
		//TODO Fix round
		invoice.setTotal(new BigDecimal(95.40).setScale(2, 2));
		invoice.setDateIssue(new Date(1398351236425L));
		invoice.setDatePaymentDue(new Date(1398351236425L));
		invoice.setSubTotal(new BigDecimal(106).setScale(2));
		invoice.setDiscount(10);

		InvoiceLine invoiceLine1, invoiceLine2;

		invoiceLine1 = new InvoiceLine();
		invoiceLine1.setInvoice(invoice);
		invoiceLine1.setLineText("Line 1");
		invoiceLine1.setLinePrice(new BigDecimal(10).setScale(2));
		invoiceLine1.setLineQt(5);
		invoiceLine1.setLineTax(new BigDecimal(7).setScale(2));
		invoiceLine1.setLineTotal(new BigDecimal(43).setScale(2));

		invoiceLine2 = new InvoiceLine();
		invoiceLine2.setInvoice(invoice);
		invoiceLine2.setLineText("Line 2");
		invoiceLine2.setLinePrice(new BigDecimal(10).setScale(2));
		invoiceLine2.setLineQt(7);
		invoiceLine2.setLineTax(new BigDecimal(7).setScale(2));
		invoiceLine2.setLineTotal(new BigDecimal(63).setScale(2));

		invoice.getInvoicesLines().add(invoiceLine1);
		invoice.getInvoicesLines().add(invoiceLine2);

		File pdfFile = entityPresenter.presentToPDF(invoice);

		System.out.println(pdfFile.getCanonicalPath());

	}
}