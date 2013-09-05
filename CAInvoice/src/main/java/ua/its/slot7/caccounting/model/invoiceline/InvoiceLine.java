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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.its.slot7.caccounting.model.invoice.Invoice;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Invoice line
 * Key field - {@link #getLineText()}
 * */
@Entity
public class InvoiceLine implements Serializable, Comparable<InvoiceLine> {
	/**
	 *
	 * InvoiceLine ID
	 * */
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public long getId() {
		return id;
	}

	/**
	 *
	 * InvoiceLine tech-ID</br>
	 * For local identifications only
	 * */
	@Transient
	public long getTid() {
		return tid;
	}

	/**
	 *
	 * Invoice
	 * */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 *
	 * InvoiceLine text
	 * */
	@Column(nullable = false)
	@Type(type="text")
	public String getLineText() {
		return lineText;
	}

	/**
	 *
	 * InvoiceLine Quantity
	 * */
	@Column(nullable = false)
	public int getLineQt() {
		return lineQt;
	}

	/**
	 *
	 * InvoiceLine Price
	 * */
	@Column(nullable = false)
	public float getLinePrice() {
		return linePrice;
	}

	/**
	 *
	 * InvoiceLine Sum
	 * */
	@Column(nullable = false)
	public float getLineSum() {
		return lineSum;
	}

	/**
	 *
	 * Constructor
	 * */
	public InvoiceLine () {}

	public InvoiceLine(Invoice invoice, String lineText, int lineQt, float linePrice, float lineSum) {
		this.invoice=invoice;
		this.lineText = lineText;
		this.lineQt = lineQt;
		this.linePrice = linePrice;
		this.lineSum = lineSum;
	}

	/**
	 *
	 * Calc the {@link #lineSum} of this InvoiceLine</br>
	 * */
	public float calcLineSum () {
		float res = 0;
		res=this.getLinePrice()*this.getLineQt();
		return res;
	}

	/**
	 *
	 * Fields sequence: {@link #hashCode()} , {@link #getId()} , {@link #getLineText()} , {@link #getLinePrice()} , {@link #getLineQt()} , {@link #getLineSum()} , {@link ua.its.slot7.caccounting.model.invoice.Invoice#hashCode()} , {@link Invoice#getId()}
	 * */
	@Override
	public String toString() {
		String res = null;
		StringBuilder sb = new StringBuilder();
		sb.append("Invoice line : { ")
			.append(this.hashCode())
			.append(" | ")
			.append(this.getId())
			.append(" | ")
			.append(this.getLineText())
			.append(" | ")
			.append(this.getLinePrice())
			.append(" | ")
			.append(this.getLineQt())
			.append(" | ")
			.append(this.getLineSum())
			.append(" | ")
			.append(this.getInvoice().hashCode())
			.append(" | ")
			.append(this.getInvoice().getId())
			.append(" }");
		res=sb.toString();
		return res;
	}

	/**
	 *
	 * Based on {@link #getLineText()#hashCode()}
	 * */
	@Override
	public boolean equals (Object anInvoiceLine) {
		if ( this == anInvoiceLine ) return true;

		if ( !(anInvoiceLine instanceof InvoiceLine) ) return false;

		InvoiceLine that = (InvoiceLine) anInvoiceLine;

		return this.getLineText().equalsIgnoreCase(that.getLineText());
	}

	/**
	 *
	 * Based on {@link #getLineText()#hashCode()}
	 * */
	@Override
	public int hashCode() {
		return this.getLineText().hashCode();
	}

	@Override
	public int compareTo(InvoiceLine o) {
		int res = 0;
		res=o.getLineText().compareTo(this.getLineText());
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

	public void setLinePrice(float linePrice) {
		this.linePrice = linePrice;
	}

	public void setLineSum(float lineSum) {
		this.lineSum = lineSum;
	}

	private long id;
	private long tid;
	private Invoice invoice;
	private String lineText;
	private int lineQt;
	private float linePrice;
	private float lineSum;
}
