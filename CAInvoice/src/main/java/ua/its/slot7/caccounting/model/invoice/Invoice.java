package ua.its.slot7.caccounting.model.invoice;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.invoicepaymentstate.InvoicePaymentState;
import ua.its.slot7.caccounting.model.person.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CAccounting
 * 09.06.13 : 7:23
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Entity
public class Invoice implements Serializable, Comparable<Invoice> {
	/**
	 * Invoice tech-ID
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public long getId() {
		return id;
	}

	/**
	 * Invoice number
	 *
	 * @return Invoice number, string-style
	 */
	@Column(nullable = false)
	@Index(name = "number")
	public String getNumber() {
		return number;
	}

	/**
	 * Invoice creation date
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * Invoice update date
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateUpdate() {
		return dateUpdate;
	}

	/**
	 * Invoice sum
	 */
	@Column(nullable = false)
	public float getSum() {
		return sum;
	}

	/**
	 * Person
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Person getPerson() {
		return person;
	}

	/**
	 * InvoiceLines</br>
	 * The {@link java.util.List} of {@link ua.its.slot7.caccounting.model.invoiceline.InvoiceLine}
	 */
	@OneToMany(fetch = FetchType.LAZY,
		mappedBy = "invoice",
		cascade = {CascadeType.ALL})
	public List<InvoiceLine> getInvoicesLines() {
		return invoicesLines;
	}

	/**
	 * Invoice payment state
	 */
	@OneToOne(cascade = CascadeType.ALL,
		mappedBy = "invoice")
	public InvoicePaymentState getPaymentState() {
		return paymentState;
	}

	/**
	 * Optimistic locking
	 */
	@Version
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * Constructor
	 */
	public Invoice() {
		Date d = new Date();
		this.setDateCreation(d);
		this.setDateUpdate(d);
		this.setNumber("");
		this.setSum(0);
		this.setInvoicesLines(new ArrayList<InvoiceLine>());
		this.setPaymentState(new InvoicePaymentState(this));
	}

	public Invoice(Person person) {
		Date d = new Date();
		this.setDateCreation(d);
		this.setDateUpdate(d);
		this.setPerson(person);
		this.setNumber("");
		this.setSum(0);
		this.setInvoicesLines(new ArrayList<InvoiceLine>());
		this.setPaymentState(new InvoicePaymentState(this));
	}

	public Invoice(Person person, float sum) {
		Date d = new Date();
		this.setDateCreation(d);
		this.setDateUpdate(d);
		this.setPerson(person);
		this.setNumber("");
		this.setSum(sum);
		this.setInvoicesLines(new ArrayList<InvoiceLine>());
		this.setPaymentState(new InvoicePaymentState(this));
	}

	/**
	 * Calc sum {@link #sum} of this Invoice
	 *
	 * @return Total of the Invoice
	 */
	public float calcInvoiceSum() {
		float res = 0;
		for (InvoiceLine il : this.getInvoicesLines()) {
			res = res + il.calcLineSum();
		}
		return res;
	}

	/**
	 * Fields sequence: {@link #hashCode()} , {@link #getId()} , {@link #getNumber()} , {@link #getDateCreation()} , {@link #getDateUpdate()} , {@link #getSum()} , {@link ua.its.slot7.caccounting.model.person.Person#hashCode()} , {@link ua.its.slot7.caccounting.model.person.Person#getId()} , {@link ua.its.slot7.caccounting.model.invoicepaymentstate.InvoicePaymentState#isPaid()} , {@link #getLastUpdate()}
	 */
	@Override
	public String toString() {
		String res = null;
		StringBuilder sb = new StringBuilder();
		sb.append("Invoice : { ")
			.append(this.hashCode())
			.append(" | ")
			.append(this.getId())
			.append(" | ")
			.append(this.getNumber())
			.append(" | ")
			.append(this.getDateCreation().toString())
			.append(" | ")
			.append(this.getDateUpdate().toString())
			.append(" | ")
			.append(this.getSum())
			.append(" | ")
			.append(this.getPerson().hashCode())
			.append(" | ")
			.append(this.getPerson().getId())
			.append(" | ")
			.append(this.getPaymentState().isPaid())
			.append(" }");
		res = sb.toString();
		return res;
	}

	/**
	 * Based on {@link #getNumber()#hashCode()}
	 */
	@Override
	public boolean equals(Object anInvoice) {
		//check for self-comparison
		if (this == anInvoice) return true;

		if (!(anInvoice instanceof Invoice)) return false;

		//cast
		Invoice that = (Invoice) anInvoice;

		//key field - number
		return this.getNumber().equalsIgnoreCase(that.getNumber());
	}

	/**
	 * Based on {@link #getNumber()#hashCode()}
	 */
	@Override
	public int hashCode() {
		return this.getNumber().hashCode();
	}

	@Override
	public int compareTo(Invoice o) {
		int res = 0;

		if (o.getDateCreation().before(this.getDateCreation())) {
			res = -1;
		} else {
			res = 1;
		}

		return res;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setInvoicesLines(List<InvoiceLine> invoicesLines) {
		this.invoicesLines = invoicesLines;
	}

	public void setPaymentState(InvoicePaymentState paymentState) {
		this.paymentState = paymentState;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	private long id;
	private String number;
	private Date dateCreation;
	private Date dateUpdate;
	private float sum;
	private Person person;
	private List<InvoiceLine> invoicesLines = null;
	private InvoicePaymentState paymentState = null;

	private Date lastUpdate;

}
