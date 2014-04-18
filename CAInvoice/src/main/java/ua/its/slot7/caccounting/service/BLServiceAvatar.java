package ua.its.slot7.caccounting.service;

/**
 * CAccounting
 * 09.06.13 : 23:49
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
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface BLServiceAvatar {

	/**
	 *
	 * Get Person's invoices
	 * @param person Person to get it's invoices
	 * */
	public ArrayList<Invoice> personGetInvoices(Person person);

	/**
	 *
	 * Get Person's last invoice
	 * @param person Person to get the last invoice
	 * */
	public Invoice personGetLastInvoice(Person person);

	/**
	 *
	 * Get Person's payment status : true if Person have't unpaid invoices
	 * @param person Person to get status
	 * */
	public boolean personGetOkStatus(Person person);

	/**
	 *
	 * Create (persist) new Invoice
	 * @param invoice Invoice to create new invoice
	 * */
	public void invoiceCreate (Invoice invoice);

	/**
	 *
	 * Create new Invoice from given template
	 * @param invoiceTemplate Invoice - template for the new one
	 * */
	public Invoice invoiceCreateWithTemplate (Invoice invoiceTemplate);

	/**
	 *
	 * Get Invoice by its number
	 * @param invoiceNumber Invoice number
	 * */
	public Invoice invoiceGetByNumber (String invoiceNumber);

	/**
	 *
	 * Get {@link java.util.List} of {@link Invoice} from period of {@link ua.its.slot7.caccounting.model.invoice.Invoice#getDateCreation()}
	 * @param fd Period begin, inclusively
	 * @param td Period end, inclusively
	 * */
	public List<Invoice> invoiceGetByPeriod(Date fd, Date td);

	/**
	 *
	 * Calc sum of the Invoice
	 * @param invoice Invoice to Calc sum
	 * */
	public BigDecimal calcInvoiceSum(Invoice invoice);

	/**
	 *
	 * Is {@link Invoice} relate to the {@link User}?
	 * @param invoice {@link Invoice} to search
	 * @param user Authorized (logged-in) {@link User}
	 * */
	public boolean isInvoiceRelateToTheUser(User user, Invoice invoice);

	/**
	 *
	 * @param invoice Invoice to find line text
	 * @param ilText String to find
	 * */
	public boolean isThereThatInvoiceLineText (Invoice invoice, String ilText);

	/**
	 * Get the next free invoice line tid for non-persistent invoice
	 * @param invoice Invoice to find next tid
	 * */
	public long calcNextIlID(Invoice invoice);

	/**
	 * Get invoice line by its id (non-persistent invoice)
	 * @param invoice Invoice to find where
	 * @param idToFind Id to find
	 * */
	public InvoiceLine locateInvoiceLineById (Invoice invoice, long idToFind);

	/**
	 * Get invoice line by its text
	 * @param invoice Invoice to find where
	 * @param sToFind String to find line text
	 * */
	public InvoiceLine locateInvoiceLineByText (Invoice invoice, String sToFind);

	/**
	 *
	 * Update given Invoice paid status
	 * @param invoice Invoice to update
	 * @param gp paid - true, not paid - false
	 * */
	public void invoiceUpdatePaidStatus (Invoice invoice, boolean gp);

	/**
	 *
	 * Get {@link Person} by {@link Person#id}, if key belong to appropriate {@link ua.its.slot7.caccounting.model.user.User}.<br />
	 * Otherwise return null.
	 * @param key User key
	 * @param id Person id
	 * */
	public Person getPersonForUserKey (String key, Long id);

}
