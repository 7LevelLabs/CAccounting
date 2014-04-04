package ua.its.slot7.caccounting.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.model.userrole.UserRole;

/**
 * @author Alex Velichko
 *         02.04.14 : 19:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UserServiceIT extends Assert {

	private User user1;

	private String user1Nick = "User 1 nick";
	private String user1Email = "user1@email.com";
	private String user1Pass = "user1pass";

	@Autowired
	private UserServiceAvatar userService;

//	@BeforeClass
//	public static void beforeClass() {
//		System.getProperties().put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
//		System.getProperties().put("java.naming.provider.url", "t3://localhost:7001");
//	}

	@Before
	public void setUp() {
		user1 = new User(user1Nick, user1Email, user1Pass);
	}

	@Test
	public void testCreateUser() throws Exception {

		User readUser = null;

		boolean readUserActive;
		int readUserRole;

		//persist

		userService.createUser(user1);

		//read by key

		readUser = userService.getUserByEMail(user1Email);
		readUserActive = readUser.isActive();
		readUserRole = readUser.getUserRole().getRole();

		//asserts

		org.assertj.core.api.Assertions
			.assertThat(readUser.getNick()).isEqualTo(user1.getNick());

		org.assertj.core.api.Assertions
			.assertThat(readUser.getEmail()).isEqualTo(user1.getEmail());

		org.assertj.core.api.Assertions
			.assertThat(readUser.getPass()).isEqualTo(user1.getPass());

		assertTrue(!readUserActive);

		assertEquals(readUserRole, UserRole.USER_ROLE_USER);

	}
}
