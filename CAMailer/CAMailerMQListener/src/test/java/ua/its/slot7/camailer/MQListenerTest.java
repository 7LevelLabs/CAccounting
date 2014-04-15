package ua.its.slot7.camailer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Alex Velichko
 *         12.04.14 : 17:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class MQListenerTest extends Assert {

	@Autowired
	private MQListener mqListener;

	@Test
	public void testSenderNotNull() {
		assertNotNull(mqListener.getEmailSender());
	}

}
