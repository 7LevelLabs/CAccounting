package ua.its.slot7.caccounting.model.invoice;

/**
 * CAccounting
 * 09.06.13 : 20:05
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import ua.its.slot7.caccounting.model.user.User;

import java.util.Date;
import java.util.List;

/**
 * Invoice DBManager Avatar (interface). DAO Layer.
 * */
public interface InvoiceDBManagerAvatar {
	//CRUD & etc.
	/**
	 *
	 * Persist new Invoice
	 * @param invoice Invoice instance to persist
	 * */
	public void createInvoice (Invoice invoice);

	/**
	 *
	 * Search invoice by its id
	 * @param invoiceId Invoice id to search
	 * */
	public Invoice getInvoiceById (long invoiceId);

	/**
	 *
	 * Search invoice by its number
	 * @param invoiceNumber Invoice number to search
	 * */
	public Invoice getInvoiceByNumber (String invoiceNumber);

	/**
	 *
	 * Get all invoices
	 * @return Invoices List
	 * */
	public List<Invoice> getInvoicesAll();

	/**
	 *
	 * Get all invoices by {@link User}
	 * @return Invoices List
	 * */
	public List<Invoice> getInvoicesByTheUser(User user);

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
	 * Get unpaid invoices
	 * @return Unpaid invoices
	 * */
	public List<Invoice> getInvoicesUnpaid();

	/**
	 *
	 * Get unpaid invoices by the {@link User}
	 * @return Unpaid invoices
	 * */
	public List<Invoice> getInvoicesUnpaidByTheUser(User user);

	/**
	 *
	 * Update given Invoice
	 * @param invoice Invoice instance to update
	 * */
	public void updateInvoice(Invoice invoice);

	/**
	 *
	 * Delete given Invoice
	 * @param invoice Invoice instance to delete
	 * */
	public void deleteInvoice(Invoice invoice);


}
