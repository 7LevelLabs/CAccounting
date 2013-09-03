package ua.its.slot7.caccounting.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoice.InvoiceDBManagerAvatar;
import ua.its.slot7.caccounting.model.user.User;

import java.util.Date;
import java.util.List;

/**
 * CAccounting
 * 19.06.13 : 16:36
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Service("InvoiceServiceAvatar")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class InvoiceService implements InvoiceServiceAvatar {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private InvoiceDBManagerAvatar invoiceDBManager;

	@Override
	public void createInvoice(Invoice invoice) {
		invoiceDBManager.createInvoice(invoice);
	}

	@Override
	public Invoice getInvoiceById(long invoiceId) {
		return invoiceDBManager.getInvoiceById(invoiceId);
	}

	@Override
	public Invoice getInvoiceByNumber(String invoiceNumber) {
		return invoiceDBManager.getInvoiceByNumber(invoiceNumber);
	}

	@Override
	public List<Invoice> getInvoicesAll() {
		return invoiceDBManager.getInvoicesAll();
	}

	@Override
	public List<Invoice> getInvoicesByDatePeriod(Date fd, Date td) {
		return invoiceDBManager.getInvoicesByDatePeriod(fd,td);
	}

	@Override
	public List<Invoice> getInvoicesByTheUser(User user) {
		return invoiceDBManager.getInvoicesByTheUser(user);
	}

	@Override
	public List<Invoice> getInvoicesUnpaid() {
		return invoiceDBManager.getInvoicesUnpaid();
	}

	@Override
	public List<Invoice> getInvoicesUnpaidByTheUser(User user) {
		return invoiceDBManager.getInvoicesUnpaidByTheUser(user);
	}

	@Override
	public void updateInvoice(Invoice invoice) {
		invoiceDBManager.updateInvoice(invoice);
	}

	@Override
	public void makePaid(Invoice invoice) {
		Date d = new Date();
		invoice.getPaymentState().setPaid(true);
		invoice.getPaymentState().setLastDate(d);
		invoice.setDateUpdate(d);
		this.updateInvoice(invoice);
	}

	@Override
	public void deleteInvoice(Invoice invoice) {
		invoiceDBManager.deleteInvoice(invoice);
	}
}
