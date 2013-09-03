package ua.its.slot7.caccounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLineDBManagerAvatar;

/**
 * CAccounting
 * 19.06.13 : 16:43
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Service("InvoiceLineServiceAvatar")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class InvoiceLineService implements InvoiceLineServiceAvatar {

	@Autowired
	private InvoiceLineDBManagerAvatar invoiceLineDBManager;

	@Override
	public void createInvoiceLine(InvoiceLine invoiceLine) {
		invoiceLineDBManager.createInvoiceLine(invoiceLine);
	}

	@Override
	public void updateInvoiceLine(InvoiceLine invoiceLine) {
		invoiceLineDBManager.updateInvoiceLine(invoiceLine);
	}

	@Override
	public void deleteInvoiceLine(InvoiceLine invoiceLine) {
		invoiceLineDBManager.deleteInvoiceLine(invoiceLine);
	}
}
