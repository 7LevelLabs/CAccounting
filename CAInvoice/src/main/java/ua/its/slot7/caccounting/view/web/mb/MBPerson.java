package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.service.PersonServiceAvatar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CAccounting
 * 07.07.13 : 14:10
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
@ManagedBean(name = "MBPerson")
@Scope("request")
@RequestScoped
public class MBPerson implements Serializable {
	private final Logger LOGGER = Logger.getLogger("MBPerson");

	@Autowired
	private PersonServiceAvatar personService;

	@ManagedProperty("#{param.personId}")
	private long personId;

	private String nick;
	private String name;
	private String phone;
	private String email;

	private Person person;
	private List<Invoice> personInvoices;

	private void init () {
		person=personService.getPersonById(this.personId);
		personInvoices = new ArrayList<Invoice>();
		if (person!=null) {
			personInvoices.addAll(person.getInvoices());

			this.setNick(person.getNick());
			this.setName(person.getName());
			this.setPhone(person.getPhone());
			this.setEmail(person.getEmail());

			Collections.sort(personInvoices);
		}
	}

	public void loadAction () {
		this.init();
	}

	public String updatePerson () {
		String res = "persons?faces-redirect=true";
		Person lp = personService.getPersonByEMail(this.getEmail());
		if (this.getPerson()==null) {
			person = personService.getPersonById(this.personId);
		}

		if (lp!=null && (lp.getId()!=person.getId())) {
			FacesMessage msg =
				new FacesMessage("EMail already in use by other Person.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null,msg);
			return null;
		}
		person.setEmail(this.getEmail());
		person.setNick(this.getNick());
		person.setName(this.getName());
		person.setPhone(this.getPhone());
		personService.updatePerson(person);
		return res;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Invoice> getPersonInvoices() {
		return personInvoices;
	}

	public void setPersonInvoices(List<Invoice> personInvoices) {
		this.personInvoices = personInvoices;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public MBPerson () {

	}
}
