package ua.its.slot7.caccounting.model.userartoken;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * CAccounting
 * 26.08.13 : 0:30
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Repository("UserARTokenDBManagerAvatar")
public class UserARTokenDBManager implements UserARTokenDBManagerAvatar {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createUserARToken(UserARToken userARToken) {
		sessionFactory.getCurrentSession().save(userARToken);
	}

	@Override
	public UserARToken getUserARTokenById(long tokenId) {
		UserARToken userARToken = null;
		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select userARToken " +
				"from UserARToken userARToken " +
				"where userARToken.id = :id";

		Query query = session.createQuery(selectString);
		query.setParameter("id",tokenId);
		userARToken = (UserARToken)query.uniqueResult();
		return userARToken;
	}

	@Override
	public UserARToken getUserARTokenByEMail(String tokenEMail) {
		UserARToken userARToken = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select userARToken " +
				"from UserARToken userARToken " +
				"where userARToken.email = :email "+
				"order by userARToken.periodBegin desc";

		Query query = session.createQuery(selectString);
		query.setParameter("email",tokenEMail);
		query.setMaxResults(1);
		userARToken = (UserARToken)query.uniqueResult();
		return userARToken;
	}

	@Override
	public void deleteUserARToken(UserARToken userARToken) {
		sessionFactory.getCurrentSession().delete(userARToken);
	}
}
