package ua.its.slot7.caccounting.communications;

/**
 * @author Alex Velichko
 *         23.04.14 : 12:43
 */
public interface IMailBodyProcessor {
	/**
	 * Welcome aboard email to the new user
	 */
	public String userWelcomeAboard(final String nick, final String email);

	/**
	 * Access recover email, ph. 1
	 */
	public String userAccessRecoverPh1(final String nick,
						final String email,
						final String code,
						final String urlApplication,
						final String urlPh2,
						final String codeExpTime);

	/**
	 * Access recover email, ph. 2
	 */
	public String userAccessRecoverPh2(final String nick,
						final String email,
						final String urlApplication);


}
