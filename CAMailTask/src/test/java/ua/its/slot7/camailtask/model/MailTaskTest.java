package ua.its.slot7.camailtask.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * CAccounting
 * 27.08.13 : 13:54
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class MailTaskTest extends Assert {

	private MailTask mailTask;

	@Before
	public void setUp() throws Exception {
		this.mailTask = new MailTask("alex@test.com",
			"Alex from the Test-test",
			"alex@test-test.com",
			"Alex to the Test-test",
			"Test message : subject",
			"Test message : body",
			false);
	}

	@After
	public void tearDown() throws Exception {
		this.mailTask = null;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor() throws Exception {

		MailTask mt;

		String bf = "alex@test.com";
		String bfn = "";
		String bt = "alex@test-test.com";
		String btn = "Alex from the Test-test";
		String bs = "Test message : subject";
		String bb = "Test message : body";
		boolean bh = false;

		mt = new MailTask(null,
			bfn,
			bt,
			btn,
			bs,
			bb,
			bh);

		mt = null;

		mt = new MailTask(bf,
			bfn,
			null,
			btn,
			bs,
			bb,
			bh);

		mt = new MailTask(bf,
			bfn,
			bt,
			btn,
			null,
			bb,
			bh);

		mt = null;

		mt = new MailTask(bf,
			bfn,
			bt,
			btn,
			bs,
			null,
			bh);

		mt = null;

	}

	@Test
	public void testEmailVerify() throws Exception {
		assertTrue(this.mailTask.emailVerify("alex@alex.com"));
		assertTrue(!this.mailTask.emailVerify("alex@alex@com"));
		assertTrue(!this.mailTask.emailVerify("@alex@com"));
	}

	@Test
	public void testGetFrom() throws Exception {
		String assertRes = "alex@test.com";
		assertTrue(assertRes.equals(this.mailTask.getFrom()));
	}

	@Test
	public void testGetTo() throws Exception {
		String assertRes = "alex@test-test.com";
		assertEquals(assertRes, this.mailTask.getTo());
	}

	@Test
	public void testGetSubject() throws Exception {
		String assertRes = "Test message : subject";
		assertTrue(assertRes.equals(this.mailTask.getSubject()));
	}

	@Test
	public void testGetIsHTMLMessage() throws Exception {
		assertEquals(false, this.mailTask.getIsHTMLMessage());
	}

	@Test
	public void testGetMessageBody() throws Exception {
		String assertRes = "Test message : body";
		assertEquals(assertRes, this.mailTask.getMessageBody());
	}

	@Test
	public void testToString() throws Exception {
		String assertRes = "MailTask{from='alex@test.com', fromName='Alex from the Test-test', to='alex@test-test.com', toName='Alex to the Test-test', subject='Test message : subject', isHTMLMessage=false, messageBody='Test message : body'}";
		assertEquals(assertRes, this.mailTask.toString());
	}

}
