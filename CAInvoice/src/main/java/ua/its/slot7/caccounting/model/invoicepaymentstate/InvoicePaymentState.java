package ua.its.slot7.caccounting.model.invoicepaymentstate;

/**
 * CAccounting
 * 14.06.13 : 13:41
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import org.hibernate.annotations.GenericGenerator;
import ua.its.slot7.caccounting.model.invoice.Invoice;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Invoice payment state: is invoice full paid?
 */
@Entity
public class InvoicePaymentState implements Serializable {

	/**
	 * Invoice payment state tech-ID
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public long getId() {
		return id;
	}

	/**
	 * Invoice payment state
	 */
	@Column(nullable = false)
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * Invoice payment state changing date
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastDate() {
		return lastDate;
	}

	/**
	 * Invoice
	 */
	@OneToOne
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * Constructor
	 */
	public InvoicePaymentState() {
		this.setPaid(false);
		this.setLastDate(new Date());
	}

	public InvoicePaymentState(final Invoice invoice) {
		this();
		if (invoice == null) {
			throw new IllegalArgumentException("Arguments must be not null");
		}
		this.setInvoice(invoice);
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPaid(boolean paid) {
		isPaid = paid;
		this.setLastDate(new Date());
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	private long id;
	private boolean isPaid;
	private Date lastDate;

	@NotNull(message = "Invoice in the payment state must be not null")
	private Invoice invoice;
}
