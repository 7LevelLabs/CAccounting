package ua.its.slot7.caccounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.helper.InvoiceHelper;
import ua.its.slot7.caccounting.helper.PersonHelper;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import java.math.BigDecimal;
import java.util.*;

/**
 * CAccounting
 * 10.06.13 : 17:53
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Service("BLServiceAvatar")
@Transactional(propagation = Propagation.REQUIRED)
public class BLService implements BLServiceAvatar {

	@Autowired
	private PersonServiceAvatar personService;

	@Autowired
	private InvoiceServiceAvatar invoiceService;

	@Autowired
	private UserServiceAvatar userService;

	@Autowired
	private PersonHelper personHelper;

	@Autowired
	private InvoiceHelper invoiceHelper;

	@Override
	public List<Invoice> personGetInvoices(Person person) {
		List<Invoice> sI = person.getInvoices();
		List<Invoice> pi = new ArrayList<Invoice>();
		pi.addAll(sI);
		return pi;
	}

	/**
	 * Get Person's overdue invoices (where {@link ua.its.slot7.caccounting.model.invoice.Invoice#getDatePaymentDue()} > current date)
	 *
	 * @param person Person to get it's overdue invoices
	 */
	@Override
	public List<Invoice> personGetInvoicesOverdue(Person person) {
		User user = person.getUser();
		return invoiceService.getInvoicesUnpaidOverdueByTheUser(user);
	}

	/**
	 * Get Person's overdue invoices numbers list
	 *
	 * @param person Person to get it's overdue invoices
	 * @see #personGetInvoicesOverdue(ua.its.slot7.caccounting.model.person.Person)
	 */
	@Override
	public List<String> personGetOverdueInvoicesNumbers(Person person) {

		List<Invoice> invoicesOverdue = personGetInvoicesOverdue(person);
		List<String> invoicesNumbers = new LinkedList<String>();

		for (Invoice anInvoicesOverdue : invoicesOverdue) {
			invoicesNumbers.add(anInvoicesOverdue.getNumber());
		}

		Collections.sort(invoicesNumbers);
		return invoicesNumbers;
	}

	/**
	 * Get number of Person's overdue invoices
	 *
	 * @param person Person to get it's overdue invoices
	 * @see #personGetInvoicesOverdue(ua.its.slot7.caccounting.model.person.Person)
	 */
	@Override
	public int personGetInvoicesOverdueNumber(Person person) {
		return personGetInvoicesOverdue(person).size();
	}

	/**
	 * Get boolean (yes / no) - Person has overdue invoices
	 *
	 * @param person Person to get it's overdue invoices
	 * @see #personGetInvoicesOverdueNumber(ua.its.slot7.caccounting.model.person.Person)
	 */
	@Override
	public boolean personHasOverdueInvoices(Person person) {
		return personGetInvoicesOverdueNumber(person) > 0;
	}

	@Override
	public Invoice personGetLastInvoice(Person person) {
		return personHelper.theLastInvoice(person);
	}

	@Override
	public boolean personGetOkStatus(Person person) {
		for (Invoice invoice : person.getInvoices()) {
			if (!invoice.getPaymentState().isPaid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void invoiceCreate(Invoice invoice) {
		Person person = invoice.getPerson();
		invoice.setNumber(personHelper.generateNextInvoiceNumber(person));
		person.getInvoices().add(invoice);
		personService.updatePerson(person);
	}

	@Override
	public Invoice invoiceCreateWithTemplate(Invoice invoiceTemplate) {
		Person person = invoiceTemplate.getPerson();
		Invoice n = new Invoice(person);
		Date date = new Date();

		List<InvoiceLine> invoiceLineListTemplate = invoiceTemplate.getInvoicesLines();
		List<InvoiceLine> invoiceLineList = new ArrayList<InvoiceLine>();

		for (InvoiceLine invoiceLineTemplate : invoiceLineListTemplate) {
			InvoiceLine invoiceLine = new InvoiceLine();

			invoiceLine.setInvoice(n);
			invoiceLine.setLinePrice(invoiceLineTemplate.getLinePrice());
			invoiceLine.setLineQt(invoiceLineTemplate.getLineQt());
			invoiceLine.setLineTotal(invoiceLineTemplate.getLineTotal());
			invoiceLine.setLineText(invoiceLineTemplate.getLineText());

			invoiceLineList.add(invoiceLine);
		}

		n.setInvoicesLines(invoiceLineList);

		n.setDateCreation(date);
		n.setDateUpdate(date);
		n.setTotal(invoiceTemplate.getTotal());

		this.invoiceCreate(n);

		return n;
	}

	@Override
	public Invoice invoiceGetByNumber(String invoiceNumber) {
		Invoice n = invoiceService.getInvoiceByNumber(invoiceNumber);
		return n;
	}

	@Override
	public List<Invoice> invoiceGetByPeriod(Date fd, Date td) {
		return invoiceService.getInvoicesByDatePeriod(fd, td);
	}

	@Override
	public BigDecimal calcInvoiceTotal(Invoice invoice) {
		return invoiceHelper.calcInvoiceSubTotal(invoice);
	}

	@Override
	public boolean isInvoiceRelateToTheUser(User user, Invoice invoice) {
		return invoice.getPerson().getUser().equals(user);
	}

	@Override
	public boolean isThereThatInvoiceLineText(Invoice invoice, String ilText) {
		boolean res = false;
		for (InvoiceLine invoiceLine : invoice.getInvoicesLines()) {
			if (ilText.equalsIgnoreCase(invoiceLine.getLineText())) {
				res = true;
				return res;
			}
		}
		return res;
	}

	@Override
	public long calcNextIlID(Invoice invoice) {
		long nid = 0;
		long cid = -1;
		InvoiceLine invoiceLine;
		for (InvoiceLine invoiceLine1 : invoice.getInvoicesLines()) {
			invoiceLine = invoiceLine1;
			cid = invoiceLine.getTid();
		}
		nid = cid + 1;
		return nid;
	}

	@Override
	public InvoiceLine locateInvoiceLineById(Invoice invoice, long idToFind) {
		InvoiceLine invoiceLine = null;
		for (InvoiceLine invoiceLine1 : invoice.getInvoicesLines()) {
			invoiceLine = invoiceLine1;
			if (idToFind == invoiceLine.getId()) {
				return invoiceLine;
			}
		}
		return null;
	}

	@Override
	public InvoiceLine locateInvoiceLineByText(Invoice invoice, String sToFind) {
		InvoiceLine invoiceLine = null;
		for (InvoiceLine invoiceLine1 : invoice.getInvoicesLines()) {
			invoiceLine = invoiceLine1;
			if (sToFind.equalsIgnoreCase(invoiceLine.getLineText())) {
				return invoiceLine;
			}
		}
		return null;
	}

	@Override
	public void invoiceUpdatePaidStatus(Invoice invoice, boolean gp) {
		invoice.getPaymentState().setPaid(gp);
		invoiceService.updateInvoice(invoice);
	}

	@Override
	public Person getPersonForUserKey(String key, Long id) {
		Person person = null;

		User user = userService.getUserByAPICode(key);

		if (user == null) {
			return person;
		}
		person = personService.getPersonById(id);

		if (person == null) {
			return person;
		}
		if (person.getUser() != user) {
			return null;
		}
		return person;
	}
}
