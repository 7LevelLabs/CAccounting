package ua.its.slot7.caccounting.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.person.PersonDBManagerAvatar;
import ua.its.slot7.caccounting.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * CAccounting
 * 19.06.13 : 16:50
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Service("PersonServiceAvatar")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class PersonService implements PersonServiceAvatar {
	private final Logger LOGGER = Logger.getLogger("PersonService");

	@Autowired
	private PersonDBManagerAvatar personDBManager;

	@Override
	public void createPerson(Person person) {
		personDBManager.createPerson(person);
	}

	@Override
	public List<Person> getPersonsAll() {
		return personDBManager.getPersonsAll();
	}

	@Override
	public List<Person> getPersonsByTheUser(User user) {
		return personDBManager.getPersonsByTheUser(user);
	}

	@Override
	public List<Long> getPersonsIdsByTheUser(User user) {
		List<Long> res = new ArrayList<Long>();
		List<Person> personList = this.getPersonsByTheUser(user);
		for (Person aPersonList : personList) {
			res.add(new Long(aPersonList.getId()));
		}
		return res;
	}

	@Override
	public boolean personAreThereThisEMail(String email) {
		boolean res = false;
		Person person = null;
		person = this.getPersonByEMail(email);
		res = person != null;
		return res;
	}

	@Override
	public void updatePerson(Person person) {
		personDBManager.updatePerson(person);
	}

	@Override
	public void deletePerson(Person person) {
		personDBManager.deletePerson(person);
	}

	@Override
	public void writePersons(Set persons) {
		personDBManager.writePersons(persons);
	}

	@Override
	public Person getPersonByEMail(String email) {
		return personDBManager.getPersonByEMail(email);
	}

	@Override
	public Person getPersonById(long id) {
		return personDBManager.getPersonById(id);
	}
}
