package ua.its.slot7.caccounting.system;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.velocity.VelocityEngineUtils;
import ua.its.slot7.caccounting.helper.InvoiceHelper;
import ua.its.slot7.caccounting.model.invoice.Invoice;

import java.util.HashMap;
import java.util.Map;

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
public class BEntityPresenter implements BEntityPresenterAvatar {

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	@Autowired
	private InvoiceHelper invoiceHelper;

	@Value("${template.encoding}")
	private String templateEncoding;

	@Value("${model.invoice}")
	private String templateInvoice;

	private VelocityEngine velocityEngine;

	@Override
	public String presentToHTML(final Invoice invoice) {
		String result;
		Map model = new HashMap();
		InvoiceHelper.InvoiceVO invoiceVO = invoiceHelper.getInvoiceVO(invoice);

		model.put("invoice", invoiceVO);

		result = VelocityEngineUtils.mergeTemplateIntoString(
			velocityEngine, templateInvoice, templateEncoding, model);
		return result;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
}
