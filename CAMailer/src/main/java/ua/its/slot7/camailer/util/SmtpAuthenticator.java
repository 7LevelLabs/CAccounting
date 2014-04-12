package ua.its.slot7.camailer.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Alex Velichko
 *         12.04.14 : 14:43
 */
public class SmtpAuthenticator extends Authenticator {

	public SmtpAuthenticator(final String user, final String password) {
		super();
		this.user = user;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		String username = this.getUser();
		String password = this.getPassword();
		if ((username != null) && (username.length() > 0) && (password != null)
			&& (password.length() > 0)) {

			return new PasswordAuthentication(username, password);
		}
		return null;
	}

	private final String user;
	private final String password;

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
