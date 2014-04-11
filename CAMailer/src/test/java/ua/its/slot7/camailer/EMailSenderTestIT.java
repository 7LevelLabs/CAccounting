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

import javax.mail.Session;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author Alex Velichko
 *         10.04.14 : 17:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class EMailSenderTestIT extends Assert {

	@Autowired
	private EMailSender eMailSender;

	@Value("${email.session}")
	private String sessionJndiName;

	@Before
	public void setUpClass() throws NamingException {

		//setup properties
		Properties properties = new Properties();

		//TODO Setup real values

		Session session = Session.getDefaultInstance(properties);
		SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();

		builder.bind(sessionJndiName, session);

		eMailSender.setSession(session);

	}

	@Test
	public void testSessionNotNull() throws Exception {
		assertNotNull(eMailSender.getSession());
	}

	@Test
	public void testSendEMailWithNullTask() throws Exception {
		eMailSender.sendEMail(null);
	}
}
