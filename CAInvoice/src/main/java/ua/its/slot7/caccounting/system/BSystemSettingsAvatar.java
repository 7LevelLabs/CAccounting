package ua.its.slot7.caccounting.system;

/**
 * CAccounting
 * 31.08.13 : 11:55
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public interface BSystemSettingsAvatar {

	/**
	 *
	 * System settings initiating
	 * */
	public void initSystemSettings();

	/**
	 *
	 * Get value of system setting by key
	 * */
	public String getSettingStringByKey(String key);

	/**
	 *
	 * Set value of the system setting (if not exist - create & persist)
	 * */
	public void setSystemSettingByKeyAndValue (String key, String value);

	/**
	 *
	 * Update value of the system setting (update & persist)
	 * */
	public void updateSystemSettingByKeyAndValue (String key, String value);
}
