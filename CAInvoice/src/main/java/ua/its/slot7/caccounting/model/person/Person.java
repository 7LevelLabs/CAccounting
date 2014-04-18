package ua.its.slot7.caccounting.model.person;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//TODO New fields to UI
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
	 * Prepared for
	 */
	@Column(nullable = false)
	@Index(name = "preparedFor")
	public String getPreparedFor() {
		return preparedFor;
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
	 * Last update
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * Optimistic locking
	 */
	@Version
	public Long getVersion() {
		return version;
	}

	/**
	 * Discount
	 */
	@Column(nullable = false)
	public int getDiscount() {
		return discount;
	}

	@PrePersist
	void prePersist() {
		setLastUpdate(new Date());
	}

	@PreUpdate
	void preUpdate() {
		setLastUpdate(new Date());
	}

	/**
	 * Constructor
	 */
	public Person() {
		this.setNick("");
		this.setName("");
		this.setPreparedFor("");
		this.setPhone("");
		this.setEmail("");
		this.setInvoices(new ArrayList<Invoice>());
		this.setUser(null);
	}

	/**
	 * Constructor
	 */
	public Person(final String nick,
			final String name,
			final String email,
			final String phone) {
		this();
		if ((StringUtils.isBlank(nick)) ||
			(StringUtils.isBlank(name)) ||
			(StringUtils.isBlank(email)) ||
			(StringUtils.isBlank(phone))) {
			throw new IllegalArgumentException("Arguments must be not null or empty");
		}
		this.setNick(nick);
		this.setName(name);
		this.setPreparedFor(name);
		this.setPhone(phone);
		this.setEmail(email);
	}

	/**
	 * Constructor
	 */
	public Person(final String nick,
			final String name,
			final String email,
			final String phone,
			final User user) {
		this(nick, name, email, phone);
		if (user == null) {
			throw new IllegalArgumentException("Arguments must be not null");
		}
		this.setUser(user);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Person{");
		sb.append("id=").append(id);
		sb.append(", nick='").append(nick).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", phone='").append(phone).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", invoices=").append(invoices);
		sb.append(", user=").append(user);
		sb.append(", lastUpdate=").append(lastUpdate);
		sb.append(", preparedFor='").append(preparedFor).append('\'');
		sb.append(", discount=").append(discount);
		sb.append('}');
		return sb.toString();
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

	public void setPreparedFor(String preparedFor) {
		this.preparedFor = preparedFor;
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

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public void setVersion(Long version) {
		this.version = version;
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

	@NotNull
	private User user;

	private Date lastUpdate;

	@NotBlank(message = "Person's 'Prepared for' must be not blank")
	private String preparedFor;

	@Min(value = 0, message = "Person's 'Discount' must be >= 0")
	private int discount;

	private Long version;

}
