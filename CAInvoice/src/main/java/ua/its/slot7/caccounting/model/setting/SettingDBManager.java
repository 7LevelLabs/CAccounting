package ua.its.slot7.caccounting.model.setting;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CAccounting
 * 29.08.13 : 16:55
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Repository("SettingDBManagerAvatar")
public class SettingDBManager implements SettingDBManagerAvatar {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createSetting(Setting setting) {
		Session session = sessionFactory.getCurrentSession();
		session.save(setting);
	}

	@Override
	public Setting getSettingById(long id) {
		Setting setting = null;
		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select setting " +
				"from Setting setting " +
				"where setting.id = :id";

		Query query = session.createQuery(selectString);
		query.setParameter("id",id);

		setting = (Setting)query.uniqueResult();
		return setting;
	}

	@Override
	public List<Setting> getSettingsByScope(String scope) {
		List<Setting> results = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select setting " +
				"from Setting setting " +
				"where " +
				"setting.settingscope like :scope";

		Query query = session.createQuery(selectString);
		query.setParameter("scope",scope);
		results = query.list();

		return results;
	}

	@Override
	public Setting getSettingByScopeAndKey(String scope, String k) {
		Setting setting = null;
		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select setting " +
				"from Setting setting " +
				"where "+
				"setting.settingscope like :scope and "+
				"setting.settingkey like :k";

		Query query = session.createQuery(selectString);
		query.setParameter("scope",scope);
		query.setParameter("k",k);

		setting = (Setting)query.uniqueResult();
		return setting;
	}

	@Override
	public void updateSetting(Setting setting) {
		Session session = sessionFactory.getCurrentSession();
		session.update(setting);
	}

	@Override
	public void deleteSetting(Setting setting) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(setting);
	}
}
