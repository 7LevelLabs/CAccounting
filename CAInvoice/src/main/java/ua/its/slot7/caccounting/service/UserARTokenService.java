package ua.its.slot7.caccounting.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.model.userartoken.UserARToken;
import ua.its.slot7.caccounting.model.userartoken.UserARTokenDBManagerAvatar;

/**
 * CAccounting
 * 26.08.13 : 11:25
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Service("UserARTokenServiceAvatar")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class UserARTokenService implements UserARTokenServiceAvatar {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private UserARTokenDBManagerAvatar userARTokenDBManager;

	@Override
	public void createUserARToken(UserARToken userARToken) {
		userARTokenDBManager.createUserARToken(userARToken);
	}

	@Override
	public UserARToken getUserARTokenById(long tokenId) {
		return userARTokenDBManager.getUserARTokenById(tokenId);
	}

	@Override
	public UserARToken getUserARTokenByEMail(String tokenEMail) {
		return userARTokenDBManager.getUserARTokenByEMail(tokenEMail);
	}

	@Override
	public void deleteUserARToken(UserARToken userARToken) {
		userARTokenDBManager.deleteUserARToken(userARToken);
	}
}
