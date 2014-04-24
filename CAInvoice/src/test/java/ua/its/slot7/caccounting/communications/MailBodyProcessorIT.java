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
		assertEquals(mailBodyProcessor.userWelcomeAboard("Nick", "test@test.com"), "<div>\n" +
			"    <p style=\"font-weight: normal;\">Hi, Nick ( test@test.com )! We are pleased to welcome you as a customer with <a href=\"http://7levellabs.com:7001/CAccounting\" target=\"_blank\">Cloud Accounting</a>.</p>\n" +
			"    <p style=\"font-weight: normal;\">Please feel free to take a moment to browse our service. Feel free to get started and let us know if there is anything we can do to help you get setup quicker.</p>\n" +
			"</div>\n" +
			"<div>\n" +
			"    <p>We look forward to serving you.<br/>\n" +
			"    <strong>Cheers</strong>,<br/>\n" +
			"    <a href=\"http://ca.7levellabs.com\" target=\"_blank\" style=\"font-weight: normal;\">Cloud Accounting</a> Team<br/>\n" +
			"    <a href=\"mailto:ca@7levellabs.com\"><strong>ca@7levellabs.com</strong></a><br />\n" +
			"    <a href=\"http://7levellabs.com:7001/CAccounting/about.xhtml\" target=\"_blank\">About Cloud Accounting</a> | <a href=\"http://7levellabs.com:7001/CAccounting/api-docs.xhtml\" target=\"_blank\">API Docs</a> | <a href=\"http://7levellabs.com:7001/CAccounting/terms-of-service.xhtml\" target=\"_blank\">Terms of Service</a> | <a href=\"http://7levellabs.com:7001/CAccounting/privacy-policy.xhtml\">Privacy policy</a></p></div>");
	}

	@Test
	public void testAccessRecoverPh1() throws Exception {
		assertEquals(mailBodyProcessor.userAccessRecoverPh1("Nick",
			"EMail",
			"Code",
			"ApplicationURL",
			"Ph2URL",
			"ExpTime"), "<div>\n" +
			"    <p style=\"font-weight: normal;\">Hi, Nick ( EMail )!</p>\n" +
			"    <p>You just requested to recover access to <strong><a href=\"ApplicationURL\" target=\"_blank\">Cloud Accounting</a></strong>.</p>\n" +
			"    <p>Your email : EMail ,<br />\n" +
			"        <strong>Your code</strong> : Code .</p>\n" +
			"    <p>Please, <strong>put this code and your new password on the Access recovery page</strong> : <a href=\"Ph2URL\" target=\"_blank\">Ph2URL</a> .</p>\n" +
			"    <p>Please, pay your attention : period of use of the code will expire on ExpTime .</p>\n" +
			"</div>\n" +
			"<div>\n" +
			"    <p>We look forward to serving you.<br/>\n" +
			"    <strong>Cheers</strong>,<br/>\n" +
			"    <a href=\"http://ca.7levellabs.com\" target=\"_blank\" style=\"font-weight: normal;\">Cloud Accounting</a> Team<br/>\n" +
			"    <a href=\"mailto:ca@7levellabs.com\"><strong>ca@7levellabs.com</strong></a><br />\n" +
			"    <a href=\"http://7levellabs.com:7001/CAccounting/about.xhtml\" target=\"_blank\">About Cloud Accounting</a> | <a href=\"http://7levellabs.com:7001/CAccounting/api-docs.xhtml\" target=\"_blank\">API Docs</a> | <a href=\"http://7levellabs.com:7001/CAccounting/terms-of-service.xhtml\" target=\"_blank\">Terms of Service</a> | <a href=\"http://7levellabs.com:7001/CAccounting/privacy-policy.xhtml\">Privacy policy</a></p></div>");

	}

	@Test
	public void testAccessRecoverPh2() throws Exception {
		assertEquals(mailBodyProcessor.userAccessRecoverPh2("Nick", "EMail", "ApplicationURL"), "<div>\n" +
			"    <p style=\"font-weight: normal;\">Hi, Nick ( EMail )!</p>\n" +
			"    <p>Your access to <strong><a href=\"ApplicationURL\" target=\"_blank\">Cloud Accounting</a></strong> was just recovered.</p>\n" +
			"</div>\n" +
			"<div>\n" +
			"    <p>We look forward to serving you.<br/>\n" +
			"    <strong>Cheers</strong>,<br/>\n" +
			"    <a href=\"http://ca.7levellabs.com\" target=\"_blank\" style=\"font-weight: normal;\">Cloud Accounting</a> Team<br/>\n" +
			"    <a href=\"mailto:ca@7levellabs.com\"><strong>ca@7levellabs.com</strong></a><br />\n" +
			"    <a href=\"http://7levellabs.com:7001/CAccounting/about.xhtml\" target=\"_blank\">About Cloud Accounting</a> | <a href=\"http://7levellabs.com:7001/CAccounting/api-docs.xhtml\" target=\"_blank\">API Docs</a> | <a href=\"http://7levellabs.com:7001/CAccounting/terms-of-service.xhtml\" target=\"_blank\">Terms of Service</a> | <a href=\"http://7levellabs.com:7001/CAccounting/privacy-policy.xhtml\">Privacy policy</a></p></div>");
	}

	@Test
	public void testGetEmailEncoding() throws Exception {
		assertEquals(mailBodyProcessor.getEmailEncoding(), "UTF-8");
	}
}