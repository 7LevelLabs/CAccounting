package ua.its.slot7.camailer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Alex Velichko
 *         10.04.14 : 17:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class EMailSenderTest extends Assert {

	@Autowired
	private EMailSender eMailSender;

	@Test
	public void testSessionNotNull() throws Exception {
		assertNotNull(eMailSender.getSession());
	}

	@Test
	public void testSendEMailWithNullTask() throws Exception {
		eMailSender.sendEMail(null);
	}
}
