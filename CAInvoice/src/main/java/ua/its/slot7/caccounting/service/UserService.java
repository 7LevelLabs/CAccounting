package ua.its.slot7.caccounting.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.model.user.UserDBManagerAvatar;

/**
 * CAccounting
 * 19.06.13 : 16:55
 * Alex Velichko
 * alex.itstudio@gmail.com
 */
@Service("UserServiceAvatar")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class UserService implements UserServiceAvatar {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private UserDBManagerAvatar userDBManager;

	@Override
	public void createUser(User user) {
		userDBManager.createUser(user);
	}

	@Override
	public boolean areThereThisEMail(String email) {
		User user = this.getUserByEMail(email);
		return user!=null;
	}

	@Override
	public User getUserById(long id) {
		return userDBManager.getUserById(id);
	}

	@Override
	public User getUserByEMail(String email) {
		return userDBManager.getUserByEMail(email);
	}

	@Override
	public User getUserByAPICode(String apiCode) {
		return userDBManager.getUserByAPICode(apiCode);
	}

	@Override
	public User getUserByPass(String pass) {
		return userDBManager.getUserByPass(pass);
	}

	@Override
	public void updateUser(User user) {
		userDBManager.updateUser(user);
	}

	@Override
	public void setUserActive(User user) {
		user.setActive(true);
		this.updateUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userDBManager.deleteUser(user);
	}
}
