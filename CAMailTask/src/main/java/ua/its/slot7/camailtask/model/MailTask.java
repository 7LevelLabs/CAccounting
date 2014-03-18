package ua.its.slot7.camailtask.model;

/**
 * CAccounting
 * 27.08.13 : 12:38
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
import org.apache.commons.validator.routines.EmailValidator;

import java.io.Serializable;
import java.util.Objects;

/**
 * Mail task - contain task to email: from, to, subject, text, etc.
 */
public class MailTask implements Serializable {

	/**
	 * Constructor
	 *
	 * @param from        Field of the future email - From (email part). Email address validation according to RFC 822 standards. Can't be null, can't be empty.
	 * @param fromName    Field of the future email - From (name part). Can be empty, not null.
	 * @param to          Field of the future email - To (email part). Email address validation according to RFC 822 standards. Can't be null, can't be empty.
	 * @param toName      Field of the future email - To (name part). Can be empty, not null.
	 * @param subject     Field of the future email - Subject. Can't be null, can't be empty.
	 * @param messageBody Field of the future email - Subject. Can't be null, can't be empty.
	 */
	public MailTask(final String from,
			  final String fromName,
			  final String to,
			  final String toName,
			  final String subject,
			  final String messageBody,
			  final boolean isHTML) {

		if (StringUtils.isBlank(from) ||
			(StringUtils.isBlank(fromName)) ||
			(StringUtils.isBlank(to)) ||
			(StringUtils.isBlank(toName)) ||
			(StringUtils.isBlank(subject)) ||
			(StringUtils.isBlank(messageBody))) {
			throw new IllegalArgumentException("Arguments can't be null or empty.");
		}

		if ((!this.emailVerify(from)) ||
			(!this.emailVerify(to))) {
			throw new IllegalArgumentException("EMail address must be valid according to RFC 822 standards.");
		}

		this.setFrom(from);
		this.setFromName(fromName);
		this.setTo(to);
		this.setToName(toName);
		this.setSubject(subject);
		this.setMessageBody(messageBody);
		this.setIsHTMLMessage(isHTML);
	}

	/**
	 * Return From field in full form (RFC 822) , if possible.
	 */
	public String getFromFullForm() {
		String res;
		if (this.getFromName().length() > 0) {
			res = this.getFromName() + " " + this.getFrom();
		} else {
			res = this.getFrom();
		}
		return res;
	}

	/**
	 * Return To field in full form (RFC 822) , if possible.
	 */
	public String getToFullForm() {
		String res;
		if (this.getToName().length() > 0) {
			res = this.getToName() + " " + this.getTo();
		} else {
			res = this.getTo();
		}
		return res;
	}

	/**
	 * Email address validation according to RFC 822 standards
	 *
	 * @param emailToVerify Email to verify. Can't be null, can't be empty.
	 */
	public boolean emailVerify(String emailToVerify) {
		if (emailToVerify == null) {
			throw new NullPointerException("Arguments can't be null.");
		}
		if (emailToVerify.length() == 0) {
			throw new IllegalArgumentException("Arguments can't be empty.");
		}

		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(emailToVerify);
	}

	public String getFrom() {
		return from;
	}

	private void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	private void setTo(String to) {
		this.to = to;
	}

	public String getFromName() {
		return fromName;
	}

	private void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getSubject() {
		return subject;
	}

	private void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean getIsHTMLMessage() {
		return isHTMLMessage;
	}

	private void setIsHTMLMessage(boolean HTMLMessage) {
		isHTMLMessage = HTMLMessage;
	}

	public String getMessageBody() {
		return messageBody;
	}

	private void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, fromName, to, toName, subject, messageBody);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final MailTask other = (MailTask) obj;
		return Objects.equals(this.from, other.from) && Objects.equals(this.fromName, other.fromName) && Objects.equals(this.to, other.to) && Objects.equals(this.toName, other.toName) && Objects.equals(this.subject, other.subject) && Objects.equals(this.messageBody, other.messageBody);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("MailTask{");
		sb.append("from='").append(from).append('\'');
		sb.append(", fromName='").append(fromName).append('\'');
		sb.append(", to='").append(to).append('\'');
		sb.append(", toName='").append(toName).append('\'');
		sb.append(", subject='").append(subject).append('\'');
		sb.append(", isHTMLMessage=").append(isHTMLMessage);
		sb.append(", messageBody='").append(messageBody).append('\'');
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Future mail message - email-part of the From field
	 */
	private String from;

	/**
	 * Future mail message - name-part of the From field
	 */
	private String fromName;

	/**
	 * Future mail message - field To
	 */
	private String to;

	/**
	 * Future mail message - name-part of the To field
	 */
	private String toName;

	/**
	 * Future mail message - field Subject
	 */
	private String subject;

	/**
	 * Future mail message - will it be HTML message?
	 */
	private boolean isHTMLMessage;

	/**
	 * Future mail message - field Body
	 */
	private String messageBody;
}
