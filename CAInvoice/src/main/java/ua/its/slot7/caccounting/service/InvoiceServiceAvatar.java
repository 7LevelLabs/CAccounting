package ua.its.slot7.caccounting.service;

/**
 * CAccounting
 * 19.06.13 : 16:26
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.user.User;

import java.util.Date;
import java.util.List;

/**
 * Invoice Service
 * */
public interface InvoiceServiceAvatar {

	/**
	 *
	 * Create (persist) given {@param invoice}
	 * */
	public void createInvoice (Invoice invoice);

	/**
	 *
	 * Get {@link Invoice} by given {@param invoiceId}
	 * */
	public Invoice getInvoiceById (long invoiceId);

	/**
	 *
	 * Get {@link Invoice} by given {@param invoiceNumber}
	 * */
	public Invoice getInvoiceByNumber (String invoiceNumber);

	/**
	 *
	 * Get {@link List} of {@link Invoice}s
	 * */
	public List<Invoice> getInvoicesAll();

	/**
	 *
	 * Get {@link List} of {@link Invoice}s for given period
	 * @param fd Period begin, inclusively
	 * @param td Period end, inclusively
	 * @return {@link List} of {@link Invoice}s for given period
	 * */
	public List<Invoice> getInvoicesByDatePeriod(Date fd, Date td);

	/**
	 *
	 * Get {@link List} of {@link Invoice}s for given {@param user}
	 * */
	public List<Invoice> getInvoicesByTheUser(User user);

	/**
	 *
	 * Get {@link List} of unpaid {@link Invoice}s
	 * */
	public List<Invoice> getInvoicesUnpaid();

	/**
	 *
	 * Get {@link List} of unpaid {@link Invoice}s for given {@param user}
	 * */
	public List<Invoice> getInvoicesUnpaidByTheUser(User user);

	/**
	 *
	 * Update given {@param invoice}
	 * */
	public void updateInvoice(Invoice invoice);

	/**
	 *
	 * Make paid given {@param invoice}
	 * */
	public void makePaid(Invoice invoice);

	/**
	 *
	 * Delete given {@param invoice}
	 * */
	public void deleteInvoice(Invoice invoice);
}
