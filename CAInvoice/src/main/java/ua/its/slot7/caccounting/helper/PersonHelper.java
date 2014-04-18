package ua.its.slot7.caccounting.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @author Alex Velichko
 *         17.04.14 : 16:01
 */
@Component
public class PersonHelper {

	@Value("${person.default.discount}")
	private int personDefaultDiscount;

	@Value("${invoice.number.default}")
	private String invoiceNumberDefault;

	@Value("${invoice.number.format}")
	private String invoiceNumberFormat;

	public String getInvoiceNumberFormat() {
		return invoiceNumberFormat;
	}

	public int getPersonDefaultDiscount() {
		return personDefaultDiscount;
	}

	public String getInvoiceNumberDefault() {
		return invoiceNumberDefault;
	}

	public Person getNewPerson(final String nick,
				      final String name,
				      final String email,
				      final String phone,
				      final User user,
				      final PersonDiscountSourceSign discountStrategy) {

		Person person = new Person(nick, name, email, phone, user);

		//set defaults
		switch (discountStrategy) {
			case PROPERTIES:
				person.setDiscount(getPersonDefaultDiscount());
				break;
			case USER:
				person.setDiscount(user.getDiscount());
		}

		return person;
	}

	/**
	 * Return the next free invoice number
	 *
	 * @return The next free invoice number, based on {@link #theLastInvoice(ua.its.slot7.caccounting.model.person.Person)}
	 */
	public String generateNextInvoiceNumber(final Person person) {
		String res = null;
		String in = null;
		Invoice invoiceLatest = null;

		if (person.getInvoices().size() > 0) {

			invoiceLatest = this.theLastInvoice(person);

			String str = invoiceLatest.getNumber();
			StringTokenizer st = new StringTokenizer(str, "-");

			//the first part of invoiceLatest.getNumber()
			String temp = (String) st.nextElement();
			//the second part of invoiceLatest.getNumber()
			String temp1 = (String) st.nextElement();

			Long l = new Long(temp1);

			l++;

			String temp2 = new DecimalFormat(getInvoiceNumberFormat()).format(l);

			in = temp.concat("-");
			in = in.concat(temp2);

		} else {
			in = new String(new Long(person.getId()).toString() + "-" + getInvoiceNumberDefault());
		}

		res = in;

		return res;
	}

	/**
	 * Return the latest invoice from {@link Person#invoices}
	 *
	 * @return The latest invoice from {@link Person#invoices} or null, if {@link Person#invoices} is empty
	 */
	public Invoice theLastInvoice(final Person person) {
		String res = null;
		String in = null;

		Iterator<Invoice> invoiceIterator = person.getInvoices().iterator();
		Invoice invoice = null;
		Invoice invoiceLatest = null;

		Date d = null;
		Date dmax = null;

		if (person.getInvoices().size() > 0) {
			invoiceLatest = invoiceIterator.next();
			dmax = invoiceLatest.getDateCreation();

			while (invoiceIterator.hasNext()) {
				invoice = invoiceIterator.next();

				d = invoice.getDateCreation();
				if (d.after(dmax)) {
					dmax = d;
					invoiceLatest = invoice;
				}
			}
		}
		return invoiceLatest;
	}

	public enum PersonDiscountSourceSign {
		PROPERTIES, USER
	}

}
