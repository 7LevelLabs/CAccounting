package ua.its.slot7.caccounting.service;

/**
 * CAccounting
 * 29.08.13 : 17:30
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import ua.its.slot7.caccounting.model.setting.Setting;
import java.util.List;

/**
 * Setting Service
 * */
public interface SettingServiceAvatar {
	//CRUD & etc.
	public void createSetting(Setting setting);

	public void createSettingIfCantFind (String scope, String key, String value);

	public void createSettingIfCantFind (Setting setting);

	public Setting getSettingById(long id);

	public List<Setting> getSettingsByScope(String scope);

	public Setting getSettingByScopeAndKey(String scope, String key);

	public void updateSetting(Setting setting);

	public void deleteSetting(Setting setting);
}
