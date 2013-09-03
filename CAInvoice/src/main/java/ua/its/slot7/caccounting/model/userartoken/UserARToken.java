package ua.its.slot7.caccounting.model.userartoken;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * CAccounting
 * 26.08.13 : 0:00
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Entity
public class UserARToken implements Serializable {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public long getId() {
		return id;
	}

	@Column(nullable = false)
	@Index(name="email")
	public String getEmail() {
		return email;
	}

	@Column(nullable = false)
	@Index(name="tokenCode")
	public String getTokenCode() {
		return tokenCode;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPeriodBegin() {
		return periodBegin;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPeriodEnd() {
		return periodEnd;
	}

	public UserARToken () {}

	public UserARToken (String email) {
		Date d = new Date();
		UUID uuid = UUID.randomUUID();

		this.setEmail(email);
		this.setPeriodBegin(d);
		//10 minutes, yes

		long pe = this.getPeriodBegin().getTime()+10L*60L*1000L;
		this.setPeriodEnd(new Date(pe));
		this.setTokenCode(uuid.toString());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserARToken : {");
		sb.append("id=").append(id);
		sb.append(", email='").append(email).append('\'');
		sb.append(", tokenCode='").append(tokenCode).append('\'');
		sb.append(", periodBegin=").append(periodBegin);
		sb.append(", periodEnd=").append(periodEnd);
		sb.append('}');
		return sb.toString();
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	public void setPeriodBegin(Date periodBegin) {
		this.periodBegin = periodBegin;
	}

	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	private long id;
	private String email;
	private String tokenCode;
	private Date periodBegin;
	private Date periodEnd;
}
