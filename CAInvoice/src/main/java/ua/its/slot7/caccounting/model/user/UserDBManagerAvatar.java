package ua.its.slot7.caccounting.model.user;

/**
 * CAccounting
 * 15.06.13 : 17:49
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
/**
 * Invoice DBManager Avatar (interface). DAO Layer.
 * */
public interface UserDBManagerAvatar {
	//CRUD & etc.
	/**
	 *
	 * Create (persist) given {@link User}
	 * */
	public void createUser (User user);

	/**
	 *
	 * Get {@link User} by given {@param id}
	 * */
	public User getUserById (long id);

	/**
	 *
	 * Get {@link User} by given {@param email}
	 * */
	public User getUserByEMail (String email);

	/**
	 *
	 * Get {@link User} by given {@param apiCode}
	 * */
	public User getUserByAPICode (String apiCode);

	/**
	 *
	 * Get {@link User} by given {@param pass}word
	 * */
	public User getUserByPass (String pass);

	/**
	 *
	 * Update given {@param user}
	 * */
	public void updateUser (User user);

	/**
	 *
	 * Delete given {@param user}
	 * */
	public void deleteUser (User user);
}
