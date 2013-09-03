package ua.its.slot7.caccounting.model.setting;

/**
 * CAccounting
 * 29.08.13 : 16:37
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * Pair - a key ({@link String}) / value ({@link String}).
 * */
@Entity
public class Setting implements Serializable {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public long getId() {
		return id;
	}

	@Column(nullable = false)
	@Index(name="settingscope")
	public String getSettingscope() {
		return settingscope;
	}

	@Column(nullable = false)
	@Index(name="settingkey")
	public String getSettingkey() {
		return settingkey;
	}

	@Column(nullable = false)
	@Type(type="text")
	public String getSettingvalue() {
		return settingvalue;
	}

	/**
	 *
	 * Constructor
	 * @param scope Scope of the setting. Can't be null, can't be empty.
	 * @param key Key for value. Can't be null, can't be empty.
	 * @param value Value of the setting. Can't be null, can't be empty.
	 * */
	public Setting (String scope, String key, String value) {
		if ((scope==null) || (key==null) || (value==null)) {
			throw new NullPointerException("Arguments can't be null.");
		}
		if ((scope.length()==0) || (key.length()==0) || (value.length()==0)) {
			throw new IllegalArgumentException("Arguments can't be empty.");
		}
		this.setSettingscope(scope);
		this.setSettingkey(key);
		this.setSettingvalue(value);
	}

	public Setting () {}

	@Override
	public int hashCode() {
		return Objects.hash(settingscope, settingkey, settingvalue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Setting other = (Setting) obj;
		return Objects.equals(this.settingscope, other.settingscope) && Objects.equals(this.settingkey, other.settingkey) && Objects.equals(this.settingvalue, other.settingvalue);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Setting { ");
		sb.append("settingscope='").append(settingscope).append('\'');
		sb.append(", settingkey='").append(settingkey).append('\'');
		sb.append(", settingvalue='").append(settingvalue).append('\'');
		sb.append(", id=").append(id);
		sb.append('}');
		return sb.toString();
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSettingscope(String scope) {
		this.settingscope = scope;
	}

	public void setSettingkey(String key) {
		this.settingkey = key;
	}

	public void setSettingvalue(String value) {
		this.settingvalue = value;
	}

	/**
	 *
	 * Scope. Not null, not empty.
	 * */
	private String settingscope;

	/**
	 *
	 * Key. Not null, not empty.
	 * */
	private String settingkey;

	/**
	 *
	 * Value. Not null, not empty.
	 * */
	private String settingvalue;

	/**
	 *
	 * Id - tech-style.
	 * */
	private long id;

}
