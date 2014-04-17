package ua.its.slot7.caccounting.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.its.slot7.caccounting.model.user.User;

/**
 * @author Alex Velichko
 *         16.04.14 : 18:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class UserHelperTest extends Assert {

	@Autowired
	private UserHelper userHelper;

	@Test
	public void testGetUserDefaultDiscount() throws Exception {
		assertEquals(userHelper.getUserDefaultDiscount(), 5);
	}

	@Test
	public void testGetUser() throws Exception {
		User user = userHelper.getUser("nick", "email", "pass");
		assertEquals(user.getDiscount(), 5);
	}
}
