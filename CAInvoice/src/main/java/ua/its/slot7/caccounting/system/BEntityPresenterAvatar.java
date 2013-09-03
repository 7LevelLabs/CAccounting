package ua.its.slot7.caccounting.system;

import ua.its.slot7.caccounting.model.invoice.Invoice;

/**
 * CAccounting
 * 01.09.13 : 16:14
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

public interface BEntityPresenterAvatar {

	/**
	 *
	 * Presents {@link Invoice} as HTML
	 * */
	public String presentToHTML(Invoice invoice);


}
