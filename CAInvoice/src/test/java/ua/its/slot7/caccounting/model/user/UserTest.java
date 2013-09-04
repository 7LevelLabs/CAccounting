package ua.its.slot7.caccounting.model.user;

import org.junit.*;
import ua.its.slot7.caccounting.model.userrole.UserRole;

/**
 * CAccounting
 * 04.09.13 : 13:24
 * Alex Velichko
 * test@test.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class UserTest extends Assert {

	private User user;

	@BeforeClass
	public void setUp() throws Exception {
		String uEmail = "test@test.com";
		String uNick = "AlexIT";

		String uPass = "b799931b47707582e32947d";
		String uAPICode = "3ff21377-c089-4640-9765-fc033072ffad";

		this.user = new User(uNick,uEmail,uPass);
		this.user.setApiCode(uAPICode);
	}

	@AfterClass
	public void tearDown() throws Exception {
		this.user=null;
	}

	@Test
	public void testGetNick() throws Exception {
		assertTrue(this.user.getNick().equals("AlexIT"));
	}


	@Test(expected = NullPointerException.class)
	public void testConstructorNull() throws Exception {
		User u;
		u = new User (null,"test@test.com","Mr.Test");
		fail();
		u = new User ("Mr.Test",null,"212121");
		fail();
		u = new User ("Mr.Test","test@test.com",null);
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEmpty() throws Exception {
		User u;
		u = new User ("","test@test.com","Mr.Test");
		fail();
		u = new User ("Mr.Test","","212121");
		fail();
		u = new User ("Mr.Test","test@test.com","");
		fail();
	}

	@Test
	public void testGetEmail() throws Exception {
		assertTrue(this.user.getEmail().equals("test@test.com"));
	}

	@Test
	public void testGetPass() throws Exception {
		assertTrue(this.user.getPass().equals("b799931b47707582e32947d"));
	}

	@Test
	public void testGetApiCode() throws Exception {
		assertTrue(this.user.getApiCode().equals("3ff21377-c089-4640-9765-fc033072ffad"));
	}

	@Test
	public void testGetUserRole() throws Exception {
		assertTrue(this.user.getUserRole().getRole()==UserRole.USER_ROLE_USER);
	}

	@Test
	public void testIsActive() throws Exception {
		assertTrue(!this.user.isActive());
	}

	@Test
	public void testEquals() throws Exception {

		String uEmail = "test@test.com";
		String uNick = "AlexIT";

		String uPass = "b799931b47707582e32947d";
		String uAPICode = "3ff21377-c089-4640-9765-fc033072ffad";

		User tu = new User(uNick,uEmail,uPass);
		tu.setApiCode(uAPICode);

		assertTrue(this.user.equals(tu));

		User tu1 = new User (uNick,"testEquals@test.com",uPass);

		assertTrue(!this.user.equals(tu1));
	}

	@Test
	public void testHashCode() throws Exception {
		int hash = 2099811859;
		assertTrue(hash==this.user.hashCode());
	}

	@Test
	public void testToString() throws Exception {
		String assertRes = "User { id=0, nick='AlexIT', email='test@test.com', pass='b799931b47707582e32947d', apiCode='3ff21377-c089-4640-9765-fc033072ffad', userRole=1, isActive=false, lastUpdate=null}";
		assertTrue(assertRes.equals(this.user.toString()));
	}
}
