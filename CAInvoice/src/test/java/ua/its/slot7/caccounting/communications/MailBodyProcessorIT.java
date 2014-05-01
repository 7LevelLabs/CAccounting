package ua.its.slot7.caccounting.communications;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class MailBodyProcessorIT extends Assert {

	@Autowired
	private MailBodyProcessor mailBodyProcessor;

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

		assertTrue(result.contains("Hi, Nick ( test@test.com )!"));
		assertTrue(result.contains("Please, pay your attention : period of use of the code will expire on ExpTime"));
	}

	@Test
	public void testAccessRecoverPh2() throws Exception {

		String result = mailBodyProcessor.userAccessRecoverPh2("Nick", "EMail", "ApplicationURL");

		System.out.println(result);

		assertTrue(result.contains("Hi, Nick ( test@test.com )!"));
		assertTrue(result.contains("<p>Your access to <strong><a href=\"ApplicationURL\" target=\"_blank\">Cloud Accounting</a></strong> was just recovered.</p>"));
	}

	@Test
	public void testGetEmailEncoding() throws Exception {
		assertEquals(mailBodyProcessor.getEmailEncoding(), "UTF-8");
	}
}