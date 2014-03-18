package ua.its.slot7.caccounting.model.invoiceline;

/**
 * CAccounting
 * 13.06.13 : 17:22
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * InvoiceLine Manager
 */
@Repository("InvoiceLineDBManagerAvatar")
public class InvoiceLineDBManager implements InvoiceLineDBManagerAvatar {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createInvoiceLine(InvoiceLine invoiceLine) {
		Session session = sessionFactory.getCurrentSession();
		session.save(invoiceLine);
	}

	@Override
	public void updateInvoiceLine(InvoiceLine invoiceLine) {
		Session session = sessionFactory.getCurrentSession();
		session.update(invoiceLine);
	}

	@Override
	public void deleteInvoiceLine(InvoiceLine invoiceLine) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(invoiceLine);
	}
}
