package ua.its.slot7.caccounting.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
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

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	@Override
	public String presentToHTML(Invoice invoice) {

		if (invoice==null) {
			throw new NullPointerException("Arguments can't be null.");
		}

		String invoiceTemplate = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EBT_INVOICE");

		//delimiter - $
		STGroup tplGroup = new STGroupDir("$", "$");
		ST mbST = new ST(tplGroup,invoiceTemplate);

		mbST.add("invoice_number",invoice.getNumber());

		return mbST.render();
	}


}
