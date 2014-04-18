package ua.its.slot7.caccounting.model.invoice;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.invoicepaymentstate.InvoicePaymentState;
import ua.its.slot7.caccounting.model.person.Person;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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
	 * Invoice total
	 */
	@Column(nullable = false)
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * Date issue
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateIssue() {
		return dateIssue;
	}

	/**
	 * Payment due
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDatePaymentDue() {
		return datePaymentDue;
	}

	/**
	 * Discount, percents
	 */
	@Column
	public int getDiscount() {
		return discount;
	}

	/**
	 * Subtotal, dirty sum (without discount)
	 */
	@Column
	public BigDecimal getSubTotal() {
		return subTotal;
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
	public Long getVersion() {
		return version;
	}

	@PrePersist
	void prePersist() {
		Date d = new Date();
		this.setDateCreation(d);
		this.setDateUpdate(d);
	}

	@PreUpdate
	void preUpdate() {
		this.setDateUpdate(new Date());
	}

	/**
	 * Constructor
	 */
	public Invoice() {
		Date d = new Date();
		this.setDateCreation(d);
		this.setDateUpdate(d);
		this.setNumber("");
		this.setSubTotal(BigDecimal.valueOf(0));
		this.setTotal(BigDecimal.valueOf(0));
		this.setInvoicesLines(new ArrayList<InvoiceLine>());
		this.setPaymentState(new InvoicePaymentState(this));
	}

	public Invoice(final Person person) {
		this();
		if (person == null) {
			throw new IllegalArgumentException("Arguments must be not null");
		}
		this.setPerson(person);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Invoice{");
		sb.append("id=").append(id);
		sb.append(", number='").append(number).append('\'');
		sb.append(", dateCreation=").append(dateCreation);
		sb.append(", dateUpdate=").append(dateUpdate);
		sb.append(", subTotal=").append(subTotal);
		sb.append(", discount=").append(discount);
		sb.append(", total=").append(total);
		sb.append(", person=").append(person);
		sb.append(", invoicesLines=").append(invoicesLines);
		sb.append(", paymentState=").append(paymentState);
		sb.append(", dateIssue=").append(dateIssue);
		sb.append(", datePaymentDue=").append(datePaymentDue);
		sb.append(", version=").append(version);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Invoice invoice = (Invoice) o;

		if (!number.equals(invoice.number)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return number.hashCode();
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

	public void setTotal(BigDecimal total) {
		this.total = total;
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

	public void setVersion(Long version) {
		this.version = version;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}

	public void setDatePaymentDue(Date datePaymentDue) {
		this.datePaymentDue = datePaymentDue;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	private long id;

	@NotBlank(message = "Invoice number must be not blank")
	private String number;

	private Date dateCreation;
	private Date dateUpdate;

	@Min(value = 0, message = "Invoice subtotal must be > 0")
	private BigDecimal subTotal;

	private int discount;

	@Min(value = 0, message = "Invoice total must be > 0")
	private BigDecimal total;

	@NotNull(message = "Invoice person must be not null")
	private Person person;

	@NotNull(message = "Invoice lines must be not null")
	private List<InvoiceLine> invoicesLines = null;

	@NotNull(message = "Invoice payment state must be not null")
	private InvoicePaymentState paymentState = null;

	private Date dateIssue;

	private Date datePaymentDue;


	private Long version;
}
