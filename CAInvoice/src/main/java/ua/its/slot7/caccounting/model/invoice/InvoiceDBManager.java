package ua.its.slot7.caccounting.model.invoice;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.its.slot7.caccounting.model.user.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * CAccounting
 * 09.06.13 : 20:09
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Repository("InvoiceDBManagerAvatar")
public class InvoiceDBManager implements InvoiceDBManagerAvatar {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createInvoice(Invoice invoice) {
		sessionFactory.getCurrentSession().save(invoice);
	}

	@Override
	public Invoice getInvoiceById(long invoiceId) {
		Invoice invoice = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select invoice " +
				"from Invoice invoice " +
				"where invoice.id = :id";

		Query query = session.createQuery(selectString);
		query.setParameter("id",invoiceId);

		invoice = (Invoice)query.uniqueResult();

		return invoice;
	}

	@Override
	public Invoice getInvoiceByNumber(String invoiceNumber) {
		Invoice invoice = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select invoice " +
				"from Invoice invoice " +
				"where invoice.number like :in";

		Query query = session.createQuery(selectString);
		query.setParameter("in",invoiceNumber);

		invoice = (Invoice)query.uniqueResult();

		return invoice;
	}

	@Override
	public List<Invoice> getInvoicesAll() {
		List<Invoice> results = null;

		Session session = sessionFactory.getCurrentSession();

		results = session.createCriteria(Invoice.class).list();

		Collections.sort(results);

		return results;
	}

	@Override
	public List<Invoice> getInvoicesByTheUser(User user) {
		List<Invoice> results = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select invoice " +
				"from Invoice as invoice " +
				"where invoice.person.user = :u "+
				"order by invoice.dateCreation desc";

		Query query = session.createQuery(selectString);
		query.setParameter("u",user);

		results = query.list();

		Collections.sort(results);

		return results;
	}

	@Override
	public List<Invoice> getInvoicesByDatePeriod(Date fd, Date td) {
		List<Invoice> results = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString =
			"select Invoice " +
				"from Invoice invoice " +
				"where " +
				"invoice.dateCreation >= :fdp and " +
				"invoice.dateCreation <= :tdp";

		Query query = session.createQuery(selectString);
		query.setParameter("fdp",fd);
		query.setParameter("tdp",td);

		results = query.list();

		Collections.sort(results);

		return results;
	}

	@Override
	public List<Invoice> getInvoicesUnpaidByTheUser(User user) {
		List<Invoice> results = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString = "select invoice " +
			"from Invoice as invoice " +
			"where invoice.person.user = :u and invoice.paymentState.paid = false " +
			"order by invoice.dateCreation desc";

		Query query = session.createQuery(selectString);
		query.setParameter("u",user);

		results = query.list();
		Collections.sort(results);

		return results;

	}

	@Override
	public List<Invoice> getInvoicesUnpaid() {
		List<Invoice> results = null;

		Session session = sessionFactory.getCurrentSession();

		String selectString = "select invoice " +
			"from Invoice as invoice " +
			"where invoice.paymentState.paid = false " +
			"order by invoice.dateCreation desc";

		Query query = session.createQuery(selectString);

		results = query.list();
		Collections.sort(results);

		return results;
	}

	@Override
	public void updateInvoice(Invoice invoice) {
		Session session = sessionFactory.getCurrentSession();
		session.update(invoice);
	}

	@Override
	public void deleteInvoice(Invoice invoice) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(invoice);
	}
}

