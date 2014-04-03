package ua.its.slot7.caccounting.model.person;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

/**
 * CAccounting
 * 08.06.13 : 15:07
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

/**
 * Person</br>
 * Key-field - {@link #getEmail()}
 */
@Entity
public class Person implements Serializable, Comparable<Person> {

	/**
	 * Person ID
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public long getId() {
		return id;
	}

	/**
	 * Person Nick
	 */
	@Column(nullable = false)
	@Index(name = "nick")
	public String getNick() {
		return nick;
	}

	/**
	 * Person Name
	 */
	@Column(nullable = false)
	@Index(name = "name")
	public String getName() {
		return name;
	}

	/**
	 * Person Phone
	 */
	@Column(nullable = false)
	@Index(name = "phone")
	public String getPhone() {
		return phone;
	}

	/**
	 * Person EMail
	 */
	@Column(nullable = false)
	@Index(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * Invoices</br>
	 * The {@link java.util.List} of {@link ua.its.slot7.caccounting.model.invoice.Invoice}
	 */
	@OneToMany(fetch = FetchType.LAZY,
		mappedBy = "person",
		cascade = {CascadeType.ALL})
	public List<Invoice> getInvoices() {
		return invoices;
	}

	/**
	 * {@link ua.its.slot7.caccounting.model.user.User}, wich register this Person
	 */
	@OneToOne
	public User getUser() {
		return user;
	}

	/**
	 * Optimistic locking
	 */
	@Version
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * Constructor
	 */
	public Person() {
		this.setName("");
		this.setNick("");
		this.setPhone("");
		this.setEmail("");
		this.setInvoices(new ArrayList<Invoice>());
		this.setUser(null);
	}

	/**
	 * Constructor
	 */
	public Person(final String name,
			final String nick,
			final String email,
			final String phone) {
		this();
		if ((StringUtils.isBlank(name)) ||
			(StringUtils.isBlank(nick)) ||
			(StringUtils.isBlank(email)) ||
			(StringUtils.isBlank(phone))) {
			throw new IllegalArgumentException("Arguments must be not null or empty");
		}
		this.setName(name);
		this.setNick(nick);
		this.setPhone(phone);
		this.setEmail(email);
	}

	/**
	 * Constructor
	 */
	public Person(String name, String nick, String email, String phone, User user) {
		this(name, nick, email, phone);
		if (user == null) {
			throw new IllegalArgumentException("Arguments must be not null");
		}
		this.setUser(user);
	}

	/**
	 * Return the next free invoice number
	 *
	 * @return The next free invoice number, based on {@link #theLastInvoice()}
	 */
	public String generateNextInvoiceNumber() {
		String res = null;
		String in = null;
		Invoice invoiceLatest = null;

		if (this.getInvoices().size() > 0) {

			invoiceLatest = this.theLastInvoice();

			String str = invoiceLatest.getNumber();
			StringTokenizer st = new StringTokenizer(str, "-");

			//the first part of invoiceLatest.getNumber()
			String temp = (String) st.nextElement();
			//the second part of invoiceLatest.getNumber()
			String temp1 = (String) st.nextElement();

			Long l = new Long(temp1);

			l++;

			String temp2 = new DecimalFormat("000").format(l);

			in = temp.concat("-");
			in = in.concat(temp2);

		} else {
			in = new String(new Long(this.getId()).toString() + "-" + "001");
		}

		res = in;

		return res;
	}

	/**
	 * Return the latest invoice from {@link #invoices}
	 *
	 * @return The latest invoice from {@link #invoices} or null, if {@link #invoices} is empty
	 */
	public Invoice theLastInvoice() {
		String res = null;
		String in = null;

		Iterator<Invoice> invoiceIterator = this.getInvoices().iterator();
		Invoice invoice = null;
		Invoice invoiceLatest = null;

		Date d = null;
		Date dmax = null;

		if (this.getInvoices().size() > 0) {
			invoiceLatest = invoiceIterator.next();
			dmax = invoiceLatest.getDateCreation();

			while (invoiceIterator.hasNext()) {
				invoice = invoiceIterator.next();

				d = invoice.getDateCreation();
				if (d.after(dmax)) {
					dmax = d;
					invoiceLatest = invoice;
				}
			}
		}
		return invoiceLatest;
	}

	/**
	 * Fields sequence: {@link #hashCode()} , {@link #getId()} , {@link #getNick()} , {@link #getName()} , {@link #getPhone()} , {@link #getEmail()} , {@link #getLastUpdate()}
	 */
	@Override
	public String toString() {
		String res = null;
		StringBuilder sb = new StringBuilder();
		sb.append("Person : { ")
			.append(this.hashCode())
			.append(" | ")
			.append(this.getId())
			.append(" | ")
			.append(this.getNick())
			.append(" | ")
			.append(this.getName())
			.append(" | ")
			.append(this.getPhone())
			.append(" | ")
			.append(this.getEmail())
			.append(" | ")
			.append(this.getLastUpdate())
			.append(" }");
		res = sb.toString();
		return res;
	}

	@Override
	public boolean equals(Object aPerson) {
		if (this == aPerson) return true;

		if (!(aPerson instanceof Person)) return false;

		Person that = (Person) aPerson;

		return this.getEmail().equalsIgnoreCase(that.getEmail());
	}

	@Override
	public int hashCode() {
		return this.getEmail().hashCode();
	}

	@Override
	public int compareTo(Person o) {
		int res = 0;
		res = o.getName().compareTo(this.getName());
		return res;
	}

	public void setId(long ID) {
		this.id = ID;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public long id;

	@NotBlank(message = "Person's nick must be not blank")
	private String nick;

	@NotBlank(message = "Person's name must be not blank")
	private String name;

	private String phone;

	@NotBlank(message = "Person's email must be not blank")
	private String email;
	private List<Invoice> invoices = null;
	private User user;
	private Date lastUpdate;
}
