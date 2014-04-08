package ua.its.slot7.camailer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InitialContext;
import java.util.Properties;

/**
 * @author Alex Velichko
 *         08.04.14 : 15:45
 */
public class EMailSenderBeanIT extends Assert {

	private EMailSenderBean eMailSenderBean;

	private static final String nameToTest = "java:global/CAMailer-0.1/EMailSenderBeanEJB";
	// or java:global/CAMailer-0.1/EMailSenderBeanEJB!ua.its.slot7.camailer.EMailSenderBeanAvatarLocal

	@Before
	private void setUp() throws Exception {
		Properties properties = new Properties();
		//
//		InitialContext initialContext = new InitialContext(properties);
		InitialContext initialContext = new InitialContext(properties);

		eMailSenderBean = (EMailSenderBean) initialContext.lookup(nameToTest);

		//
		System.out.println(eMailSenderBean.getClass().getCanonicalName());

	}

	@Test
	public void testSendEmail() throws Exception {

	}
}

