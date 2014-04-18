package ua.its.slot7.caccounting.model.invoiceline;

/**
 * CAccounting
 * 09.06.13 : 23:37
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import ua.its.slot7.caccounting.model.invoice.Invoice;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Invoice line
 * Key field - {@link #getLineText()}
 */
@Entity
public class InvoiceLine implements Serializable, Comparable<InvoiceLine> {
	/**
	 * InvoiceLine ID
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public long getId() {
		return id;
	}

	/**
	 * InvoiceLine tech-ID</br>
	 * For local identifications only
	 */
	@Transient
	public long getTid() {
		return tid;
	}

	/**
	 * Invoice
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * InvoiceLine text
	 */
	@Column(nullable = false)
	@Type(type = "text")
	public String getLineText() {
		return lineText;
	}

	/**
	 * InvoiceLine Quantity
	 */
	@Column(nullable = false)
	public int getLineQt() {
		return lineQt;
	}

	/**
	 * InvoiceLine Price
	 */
	@Column(nullable = false)
	public BigDecimal getLinePrice() {
		return linePrice;
	}

	/**
	 * InvoiceLine Sum
	 */
	@Column(nullable = false)
	public BigDecimal getLineSum() {
		return lineSum;
	}

	@Column
	@Version
	public Long getVersion() {
		return version;
	}

	@PrePersist
	void prePersist() {
		this.calcLineSum();
	}

	@PreUpdate
	void preUpdate() {
		this.calcLineSum();
	}

	/**
	 * Constructor
	 */
	public InvoiceLine() {}

	public InvoiceLine(final Invoice invoice,
			     final String lineText,
			     int lineQt,
			     BigDecimal linePrice,
			     BigDecimal lineSum) {
		if (invoice == null) {
			throw new IllegalArgumentException("Arguments must be not null");
		}
		if (StringUtils.isEmpty(lineText)) {
			throw new IllegalArgumentException("Arguments must be not null or empty");
		}
		this.invoice = invoice;
		this.lineText = lineText;
		this.lineQt = lineQt;
		this.linePrice = linePrice;
		this.lineSum = lineSum;
	}

	/**
	 * Calc the {@link #lineSum} of this InvoiceLine
	 */
	public BigDecimal calcLineSum() {
		BigDecimal res = new BigDecimal(0);
		res = BigDecimal.valueOf(this.getLinePrice().floatValue() * this.getLineQt());
		return res;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("InvoiceLine{");
		sb.append("id=").append(id);
		sb.append(", tid=").append(tid);
		sb.append(", invoice=").append(invoice);
		sb.append(", lineText='").append(lineText).append('\'');
		sb.append(", lineQt=").append(lineQt);
		sb.append(", linePrice=").append(linePrice);
		sb.append(", lineSum=").append(lineSum);
		sb.append(", version=").append(version);
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Based on {@link #getLineText()#hashCode()}
	 */
	@Override
	public boolean equals(Object anInvoiceLine) {
		if (this == anInvoiceLine) return true;

		if (!(anInvoiceLine instanceof InvoiceLine)) return false;

		InvoiceLine that = (InvoiceLine) anInvoiceLine;

		return this.getLineText().equalsIgnoreCase(that.getLineText());
	}

	/**
	 * Based on {@link #getLineText()#hashCode()}
	 */
	@Override
	public int hashCode() {
		return this.getLineText().hashCode();
	}

	@Override
	public int compareTo(InvoiceLine o) {
		int res = 0;
		res = o.getLineText().compareTo(this.getLineText());
		return res;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public void setLineText(String lineText) {
		this.lineText = lineText;
	}

	public void setLineQt(int lineQt) {
		this.lineQt = lineQt;
	}

	public void setLinePrice(BigDecimal linePrice) {
		this.linePrice = linePrice;
	}

	public void setLineSum(BigDecimal lineSum) {
		this.lineSum = lineSum;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	private long id;
	private long tid;

	@NotNull(message = "Invoice must be not null")
	private Invoice invoice;

	@NotBlank(message = "Invoice line must be not blank")
	private String lineText;

	@Min(value = 1, message = "Invoice line quantity must be > 0")
	private int lineQt;

	@Min(value = 0, message = "Invoice line price must be > 0")
	private BigDecimal linePrice;

	@Min(value = 0, message = "Invoice line sum must be > 0")
	private BigDecimal lineSum;

	private Long version;


}
