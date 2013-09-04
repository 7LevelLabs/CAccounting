package ua.its.slot7.caccounting.model.user;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * CAccounting
 * 15.06.13 : 17:53
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Repository("UserDBManagerAvatar")
public class UserDBManager implements UserDBManagerAvatar {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Override
	public User getUserById(long id) {
		User user = null;
		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select user " +
				"from User user " +
				"where user.id = :id";

		Query query = session.createQuery(selectString);
		query.setParameter("id",id);

		user = (User)query.uniqueResult();
		return user;
	}

	@Override
	public User getUserByEMail(String email) {
		User user = null;
		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select user " +
				"from User user " +
				"where user.email like :em";

		Query query = session.createQuery(selectString);
		query.setParameter("em",email);

		user = (User)query.uniqueResult();
		return user;
	}

	@Override
	public User getUserByAPICode(String apiCode) {
		User user = null;
		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select user " +
				"from User user " +
				"where user.apiCode like :ac";

		Query query = session.createQuery(selectString);
		query.setParameter("ac",apiCode);

		user = (User)query.uniqueResult();
		return user;
	}

	@Override
	public User getUserByPass(String pass) {
		User user = null;
		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select user " +
				"from User user " +
				"where user.pass like :p";

		Query query = session.createQuery(selectString);
		query.setParameter("p",pass);

		user = (User)query.uniqueResult();
		return user;
	}

	@Override
	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public void deleteUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}
}
