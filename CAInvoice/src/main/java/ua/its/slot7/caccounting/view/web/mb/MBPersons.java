package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.helper.PersonHelper;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.service.PersonServiceAvatar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;

/**
 * CAccounting
 * 04.07.13 : 16:50
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

@Component
@ManagedBean(name = "MBPersons")
@Scope("request")
@RequestScoped
public class MBPersons implements Serializable {

	private final Logger LOGGER = Logger.getLogger("MBPersons");

	@Autowired
	private PersonServiceAvatar personService;

	@Autowired
	private MBUserLogin mbUserLogin;

	@Autowired
	private PersonHelper personHelper;

	private String nick;
	private String name;
	private String phone;
	private String email;

	private List<Person> personsList;

	private List<Person> personsListFiltered;

	private long personToRedirectId;
	private Person person;

	private void loadPersonsList() {
		mbUserLogin.refreshLoggedUser();
		this.personsList = personService.getPersonsByTheUser(mbUserLogin.getLoggedUser());
	}

	public void actionLoadPerson() {
		person = personService.getPersonById(this.personToRedirectId);
		this.setNick(person.getNick());
		this.setName(person.getName());
		this.setEmail(person.getEmail());
		this.setPhone(person.getPhone());
	}

	public String showPersonDetails(long personId) {
		this.personToRedirectId = personId;
		String res = "person";
		return res;
	}

	public void refreshPersonsList() {
		this.loadPersonsList();
	}

	public String createPerson() {
		mbUserLogin.refreshLoggedUser();
		User loggedUser = mbUserLogin.getLoggedUser();

		Person person = personHelper.getNewPerson(this.getNick(),
			this.getName(),
			this.getEmail(),
			this.getPhone(),
			loggedUser,
			PersonHelper.PersonDiscountSourceSign.USER);

		personService.createPerson(person);

		String res = "persons?faces-redirect=true";
		return res;
	}

	public String deletePerson(Person person) {
		this.personsList.remove(person);
		personService.deletePerson(person);
		String res = "";
		return res;
	}

	public void setPersonsList(List<Person> personsList) {
		this.personsList = personsList;
	}

	public List<Person> getPersonsListFiltered() {
		return personsListFiltered;
	}

	public void setPersonsListFiltered(List<Person> personsListFiltered) {
		this.personsListFiltered = personsListFiltered;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Person> getPersonsList() {
		return personsList;
	}

	public long getPersonToRedirectId() {
		return personToRedirectId;
	}

	public void setPersonToRedirectId(long personToRedirectId) {
		this.personToRedirectId = personToRedirectId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public MBPersons() {

	}
}
