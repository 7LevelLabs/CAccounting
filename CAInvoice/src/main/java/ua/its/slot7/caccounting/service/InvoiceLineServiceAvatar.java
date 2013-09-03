package ua.its.slot7.caccounting.service;

/**
 * CAccounting
 * 19.06.13 : 16:27
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
 * InvoiceLine Service
 * */
public interface InvoiceLineServiceAvatar {
	/**
	 *
	 * Create (persist) given {@link InvoiceLine}
	 * */
	public void createInvoiceLine(InvoiceLine invoiceLine);

	/**
	 *
	 * Update given {@link InvoiceLine}
	 * */
	public void updateInvoiceLine (InvoiceLine invoiceLine);

	/**
	 *
	 * Delete given {@link InvoiceLine}
	 * */
	public void deleteInvoiceLine (InvoiceLine invoiceLine);
}
