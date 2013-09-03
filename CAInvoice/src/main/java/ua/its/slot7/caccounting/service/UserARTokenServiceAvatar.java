package ua.its.slot7.caccounting.service;

/**
 * CAccounting
 * 26.08.13 : 11:23
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import ua.its.slot7.caccounting.model.userartoken.UserARToken;

/**
 * UserARToken Service Avatar
 * */
public interface UserARTokenServiceAvatar {
	//CRUD (partly) & etc.
	/**
	 *
	 * Persist new UserARToken
	 * @param userARToken User Access Recovery Token instance to persist
	 * */
	public void createUserARToken (UserARToken userARToken);

	/**
	 *
	 * Search UserARToken by its id
	 * @param tokenId Token id to search
	 * */
	public UserARToken getUserARTokenById (long tokenId);

	/**
	 *
	 * Search UserARToken by its email
	 * @param tokenEMail Token email to search
	 * */
	public UserARToken getUserARTokenByEMail (String tokenEMail);

	/**
	 *
	 * Delete given UserARToken
	 * @param userARToken UserARToken instance to delete
	 * */
	public void deleteUserARToken(UserARToken userARToken);
}
