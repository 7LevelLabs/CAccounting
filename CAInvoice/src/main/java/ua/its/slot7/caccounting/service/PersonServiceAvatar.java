package ua.its.slot7.caccounting.service;

/**
 * CAccounting
 * 19.06.13 : 16:28
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import java.util.List;
import java.util.Set;

/**
 * Person Service
 * */
public interface PersonServiceAvatar {
	/**
	 *
	 * Create (persist) given {@link Person}
	 * */
	public void createPerson(Person person);

	/**
	 *
	 * Get all {@link Person}s
	 * */
	public List<Person> getPersonsAll();

	/**
	 *
	 * Get all {@link Person}s for the given {@link User}
	 * */
	public List<Person> getPersonsByTheUser(User user);

	/**
	 *
	 * Get all {@link Person}'s IDs for the given {@link User}. Used for {@link ua.its.slot7.caccounting.rest.RSPersons} REST service
	 * */
	public List<Long> getPersonsIdsByTheUser(User user);

	/**
	 *
	 * To prevent Person creating fail - search given email.<br/>
	 * {@link Person#email} - is the key-field for {@link Person}.
	 * @param email Email to search
	 * */
	public boolean personAreThereThisEMail (String email);

	/**
	 *
	 * Update given {@link Person}
	 * */
	public void updatePerson(Person person);

	/**
	 *
	 * Delete given {@link Person}
	 * */
	public void deletePerson(Person person);

	/**
	 *
	 * Create each of {@link Person} from given Set
	 * */
	public void writePersons (Set persons);

	/**
	 *
	 * Get {@link Person} with given {@param email}
	 * */
	public Person getPersonByEMail (String email);

	/**
	 *
	 * Get {@link Person} with given {@param id}
	 * */
	public Person getPersonById (long id);

}
