package ua.its.slot7.caccounting.model.userrole;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * CAccounting
 * 24.07.13 : 15:16
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
 * User Role in security
 * */
@Entity
public class UserRole implements Serializable {

	public static final int USER_ROLE_USER = 1;
	public static final int USER_ROLE_ADMIN_USER = 2;

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public long getId() {
		return id;
	}

	@Column(nullable = false)
	public int getRole() {
		return role;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public UserRole() {}

	public UserRole (int role) {
		this.setRole(role);
	}

	private long id;
	private int role;

}
