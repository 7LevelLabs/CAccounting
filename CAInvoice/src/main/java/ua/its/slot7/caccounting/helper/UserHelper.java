package ua.its.slot7.caccounting.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.user.User;

/**
 * @author Alex Velichko
 *         16.04.14 : 18:13
 */
@Component
public class UserHelper {

	@Value("${user.default.discount}")
	private int userDefaultDiscount;

	public int getUserDefaultDiscount() {
		return userDefaultDiscount;
	}

	public User getNewUser(final String nick, final String email, final String pass) {
		User user = new User(nick, email, pass);

		//set defaults
		user.setDiscount(getUserDefaultDiscount());

		return user;
	}
}
