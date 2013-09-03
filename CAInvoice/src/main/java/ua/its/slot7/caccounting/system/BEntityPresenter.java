package ua.its.slot7.caccounting.system;

import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.invoice.Invoice;

/**
 * CAccounting
 * 01.09.13 : 16:16
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Component("BEntityPresenterAvatar")
public class BEntityPresenter implements BEntityPresenterAvatar {

	@Override
	public String presentToHTML(Invoice invoice) {

		if (invoice==null) {
			throw new NullPointerException("Arguments can't be null.");
		}

		//TODO Fix this to FreeMarker

		StringBuilder sb = new StringBuilder();

		sb
			.append("<div style='width:100%'>")
			.append("<span style='width:30%'>")
			.append("Invoice Number")
			.append("</span>")
			.append("<span>")
			.append(invoice.getNumber())
			.append("</span>")
			.append("</div>")
		;

		return sb.toString();
	}


}
