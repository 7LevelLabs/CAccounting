package ua.its.slot7.camailer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.its.slot7.camailtask.model.MailTask;

import javax.mail.Session;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author Alex Velichko
 *         10.04.14 : 17:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class EMailSenderIT extends Assert {

	@Autowired
	private EMailSender eMailSender;

	@Value("${email.session}")
	private String sessionJndiName;

	@Value("${test.email.address.from}")
	private String testEMailFrom;

	@Value("${test.email.address.to}")
	private String testEMailTo;

	@Before
	public void setUpClass() throws NamingException {

		//setup properties
		Properties properties = new Properties();

		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.user", "no-reply@7levellabs.com");
		properties.setProperty("mail.smtp.password", "zwcrTaGvmnumjdZcRb4bu");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.debug", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties);
		SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();

		builder.bind(sessionJndiName, session);

		eMailSender.setSession(session);

	}

	@Test
	public void testSessionNotNull() throws Exception {
		assertNotNull(eMailSender.getSession());
	}

	@Test(expected = NullPointerException.class)
	public void testSendEMailWithNullTask() throws Exception {
		eMailSender.sendEMail(null);
	}

	@Test
	public void testSendEMailNoHtml() throws Exception {

		MailTask mailTask = new MailTask(testEMailFrom,
			"CA Info",
			testEMailTo,
			"Alex To",
			"Subject",
			"Message body",
			false);

		eMailSender.sendEMail(mailTask);
	}

	@Test
	public void testSendEMailHtml() throws Exception {

		MailTask mailTask = new MailTask(testEMailFrom,
			"CA Info",
			testEMailTo,
			"Alex To",
			"Subject",
			"<b>Message</b> body : <a href='http://test.com'>test.com</a>",
			true);

		eMailSender.sendEMail(mailTask);
	}


}
