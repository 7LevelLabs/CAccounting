package ua.its.slot7.caccounting.model.invoiceline;

/**
 * CAccounting
 * 13.06.13 : 17:21
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;

/**
 * InvoiceLine DBManager Avatar (interface). DAO Layer.
 * */
public interface InvoiceLineDBManagerAvatar {
	//CUD & etc.
	/**
	 *
	 * Create (persist) {@link ua.its.slot7.caccounting.model.invoiceline.InvoiceLine}
	 * */
	public void createInvoiceLine(InvoiceLine invoiceLine);

	/**
	 *
	 * Update {@link InvoiceLine}
	 * */
	public void updateInvoiceLine (InvoiceLine invoiceLine);

	/**
	 *
	 * Delete {@link InvoiceLine}
	 * */
	public void deleteInvoiceLine (InvoiceLine invoiceLine);
}
