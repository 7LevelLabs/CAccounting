package ua.its.slot7.caccounting.system;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	@Override
	public String presentToHTML(Invoice invoice) {

		if (invoice == null) {
			throw new NullPointerException("Arguments can't be null.");
		}

		String invoiceTemplate = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EBT_INVOICE");

//		//delimiter - $
//		STGroup tplGroup = new STGroupDir("/", '$', '$');
//		ST mbST = new ST(tplGroup, invoiceTemplate);
//
//		mbST.add("invoice_number", invoice.getNumber());
//
////		prepared by
//		mbST.add("invoice_prepared_by_name", invoice.getPerson().getUser().getPreparedBy());
//		mbST.add("invoice_prepared_by_email", invoice.getPerson().getUser().getEmail());
//
////		prepared for
//		mbST.add("invoice_prepared_for_name", invoice.getPerson().getPreparedFor());
//		mbST.add("invoice_prepared_for_email", invoice.getPerson().getEmail());
//		mbST.add("invoice_prepared_for_phone", invoice.getPerson().getPhone());
//
////		total
//		mbST.add("invoice_total", new Float(invoice.getTotal().floatValue()).toString());
//
////		date of issue
//		mbST.add("invoice_date_of_issue", invoice.getDateCreation().toString());
//
//		return mbST.render();
		return "";
	}


}
