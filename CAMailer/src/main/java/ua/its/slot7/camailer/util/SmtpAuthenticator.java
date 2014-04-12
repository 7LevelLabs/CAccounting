package ua.its.slot7.camailer.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Alex Velichko
 *         12.04.14 : 14:43
 */
public class SmtpAuthenticator extends Authenticator {

	public SmtpAuthenticator() {
		super();
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		String username = "user";
		String password = "password";
		if ((username != null) && (username.length() > 0) && (password != null)
			&& (password.length() > 0)) {

			return new PasswordAuthentication(username, password);
		}
		return null;
	}


}
