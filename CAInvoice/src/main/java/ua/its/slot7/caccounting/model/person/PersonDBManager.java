package ua.its.slot7.caccounting.model.person;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.its.slot7.caccounting.model.user.User;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * CAccounting
 * 08.06.13 : 17:12
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Repository("PersonDBManagerAvatar")
public class PersonDBManager implements PersonDBManagerAvatar {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createPerson(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.save(person);
	}

	@Override
	public List<Person> getPersonsAll() {
		List<Person> list = null;
		Session session = sessionFactory.getCurrentSession();
		//HQL
		String selectString =
			"select person " +
				"from Person person " +
				"order by person.email asc";

		Query query = session.createQuery(selectString);
		list=query.list();
		return list;
	}

	@Override
	public List<Person> getPersonsByTheUser(User userToFind) {
		List<Person> list = null;
		Session session = sessionFactory.getCurrentSession();
//		HQL
		String selectString =
			"select person " +
				"from Person person " +
				"where person.user = ? " +
				"order by person.email asc";
		Query query = session.createQuery(selectString);
		query.setParameter(0,userToFind);
		list=query.list();
		return list;
	}

	@Override
	public void updatePerson(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.update(person);
	}

	@Override
	public void deletePerson(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(person);
	}

	@Override
	public void writePersons(Set persons) {
		Iterator<Person> iterator;
		Person person;
		iterator = persons.iterator();
		while (iterator.hasNext()) {
			person=iterator.next();
			this.createPerson(person);
			person=null;
		}
	}

	@Override
	public Person getPersonByEMail(String email) {
		Person person = null;
		Session session = sessionFactory.getCurrentSession();
		String selectString =
			"select person " +
				"from Person person " +
				"where email like :em";
		Query query = session.createQuery(selectString);
		query.setParameter("em",email);
		person = (Person)query.uniqueResult();
		return person;
	}

	@Override
	public Person getPersonById(long id) {
		Person person = null;
		Session session = sessionFactory.getCurrentSession();
		String selectString =
			"select person " +
				"from Person person " +
				"where id = :id";
		Query query = session.createQuery(selectString);
		query.setParameter("id",id);
		person = (Person)query.uniqueResult();
		return person;
	}
}
