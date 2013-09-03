package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.service.InvoiceServiceAvatar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.Collections;

/**
 * CAccounting
 * 13.07.13 : 12:27
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Component
@ManagedBean(name = "MBInvoice")
@RequestScoped
public class MBInvoice implements Serializable {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private InvoiceServiceAvatar invoiceService;

	@ManagedProperty("#{param.invoiceId}")
	private long invoiceId;

	private Invoice invoiceLocal;

	public void loadAction () {
		Invoice ii = invoiceService.getInvoiceById(this.getInvoiceId());
		if (ii!=null) {
			this.setInvoiceLocal(ii);
			Collections.sort(this.getInvoiceLocal().getInvoicesLines());
		} else {
			this.setInvoiceLocal(null);
		}
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Invoice getInvoiceLocal() {
		return invoiceLocal;
	}

	public void setInvoiceLocal(Invoice invoiceLocal) {
		this.invoiceLocal = invoiceLocal;
	}

	public MBInvoice () {

	}
}
