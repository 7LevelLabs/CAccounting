package ua.its.slot7.caccounting.model.user;

/**
 * CAccounting
 * 15.06.13 : 17:18
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import ua.its.slot7.caccounting.model.userrole.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * User of the system. With login / password.</br>
 * Key field - {@link #getEmail()}
 */
@Entity
public class User implements Serializable, Comparable<User> {

	/**
	 * Invoice tech-ID
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public long getId() {
		return id;
	}

	/**
	 * User nick
	 *
	 * @return User's nick
	 */
	@Column(nullable = false)
	@Index(name = "nick")
	public String getNick() {
		return nick;
	}

	/**
	 * User email
	 *
	 * @return User's email
	 */
	@Column(nullable = false)
	@Index(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * User password
	 *
	 * @return User's password
	 */
	@Column(nullable = false)
	public String getPass() {
		return pass;
	}

	/**
	 * User API-code
	 *
	 * @return User's API code
	 */
	@Column(nullable = false)
	public String getApiCode() {
		return apiCode;
	}

	/**
	 * User userrole
	 *
	 * @return User's userrole
	 */
	@OneToOne(cascade = CascadeType.ALL)
	public UserRole getUserRole() {
		return userRole;
	}

	/**
	 * Is the user active?
	 *
	 * @return true or false
	 */
	@Column(nullable = false)
	public boolean isActive() {
		return isActive;
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
	public User() {
		this.setNick("");
		this.setEmail("");
		this.setPass("");
		UUID uuid = UUID.randomUUID();
		this.setApiCode(uuid.toString());
		this.setActive(false);
		this.setUserRole(new UserRole(UserRole.USER_ROLE_USER));
	}

	public User(final String nick, final String email, final String pass) {
		this();
		if ((StringUtils.isBlank(nick)) ||
			(StringUtils.isBlank(email)) ||
			(StringUtils.isBlank(pass))) {
			throw new IllegalArgumentException("Arguments must be not null or empty");
		}
		this.setNick(nick);
		this.setEmail(email);
		this.setPass(pass);
	}

	/**
	 * Based on {@link #email#hashCode()}
	 */
	@Override
	public boolean equals(Object aUser) {
		if (this == aUser) return true;
		if (!(aUser instanceof User)) return false;
		User that = (User) aUser;
		return this.getEmail().equalsIgnoreCase(that.getEmail());
	}

	/**
	 * Based on {@link #email#hashCode()}
	 */
	@Override
	public int hashCode() {
		return this.getEmail().hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("User { ");
		sb.append("id=").append(id);
		sb.append(", nick='").append(nick).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", pass='").append(pass).append('\'');
		sb.append(", apiCode='").append(apiCode).append('\'');
		sb.append(", userRole=").append(userRole.getRole());
		sb.append(", isActive=").append(isActive);
		sb.append(", lastUpdate=").append(lastUpdate);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public int compareTo(User o) {
		int res = 0;
		res = o.getNick().compareTo(this.getNick());
		return res;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	private long id;
	private String nick;
	private String email;
	private String pass;
	private String apiCode;
	private UserRole userRole;
	private boolean isActive;
	private Date lastUpdate;
}
