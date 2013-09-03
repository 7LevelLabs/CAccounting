package ua.its.slot7.caccounting.model.person;

/**
 * CAccounting
 * 08.06.13 : 17:02
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import ua.its.slot7.caccounting.model.user.User;

import java.util.List;
import java.util.Set;

/**
 * Person DBManager Avatar (interface). DAO Layer.
 * */
public interface PersonDBManagerAvatar {
	//CRUD & etc.
	/**
	 *
	 * Create and persist new Person
	 * @param person Person instance to persist
	 * */
	public void createPerson(Person person);

	/**
	 *
	 * Read all Persons
	 * Sorting by {@link Person#email}
	 * @return all persons
	 * */
	public List<Person> getPersonsAll();

	/**
	 *
	 * Read all Persons by User
	 * Sorting by {@link Person#email}
	 * @param userToFind
	 * */
	public List<Person> getPersonsByTheUser(User userToFind);

	/**
	 *
	 * Update given person
	 * @param person Person instance to update
	 * */
	public void updatePerson(Person person);

	/**
	 *
	 * Delete given person
	 * @param person Person instance to delete from DB
	 * */
	public void deletePerson(Person person);

	/**
	 *
	 * Persist Persons Set
	 * @param persons Persons Set to persist
	 * */
	public void writePersons (Set persons);

	/**
	 *
	 * Search and load Person by EMail
	 * @param email EMail to search with
	 * @return Searched person
	 * */
	public Person getPersonByEMail (String email);

	/**
	 *
	 * Search and load Person by id
	 * @param id id to search with
	 * @return Searched person
	 * */
	public Person getPersonById (long id);
}
