package ua.its.slot7.camailer;

import ua.its.slot7.camailtask.model.MailTask;

import javax.ejb.Local;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * CAccounting
 * 27.08.13 : 20:44
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Local
public interface EMailSenderBeanAvatarLocal {
	/**
	 *
	 * Sending email with content of {@link MailTask}
	 * @param mailTask Mail task to send
	 * @throws MessagingException - if mail resource not ready
	 * */
	public void sendEmail(MailTask mailTask) throws MessagingException, UnsupportedEncodingException;

}
