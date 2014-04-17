package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.helper.PersonHelper;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.service.BLServiceAvatar;
import ua.its.slot7.caccounting.service.InvoiceServiceAvatar;
import ua.its.slot7.caccounting.service.PersonServiceAvatar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Iterator;

/**
 * CAccounting
 * 13.07.13 : 16:52
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Component
@ManagedBean(name = "MBInvoiceNew")
@Scope("session")
@SessionScoped
public class MBInvoiceNew implements Serializable {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private InvoiceServiceAvatar invoiceService;

	@Autowired
	private PersonServiceAvatar personService;

	@Autowired
	private BLServiceAvatar blService;

	@Autowired
	private PersonHelper personHelper;

	@ManagedProperty("#{param.personIdForNewInvoice}")
	private long personIdForNewInvoice;

	private Person personLocal;
	private Invoice invoiceLocal;
	private String lilLineText;
	private float lilLinePrice;
	private int lilLineQt;
	private float lilLineSum;
	private long lilSelectedID;

	public void loadAction() {
		Person pp = personService.getPersonById(this.getPersonIdForNewInvoice());
		if (pp != null) {
			this.setPersonLocal(pp);
			this.getInvoiceLocal().setNumber(personHelper.generateNextInvoiceNumber(pp));
			this.getInvoiceLocal().setPerson(pp);
		} else {
			this.setInvoiceLocal(null);
			this.setPersonLocal(null);
		}
	}

	public void ilCreate() {
		if (blService.isThereThatInvoiceLineText(this.getInvoiceLocal(), this.lilLineText)) {
			FacesMessage msg =
				new FacesMessage("This Invoice line text already exist. Strings can't duplicate.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		//calc and set line total
		this.lilCalcAndSetLineTotal();

		InvoiceLine il = new InvoiceLine();

		il.setInvoice(this.getInvoiceLocal());
		il.setLineText(this.getLilLineText());
		il.setLinePrice(this.getLilLinePrice());
		il.setLineQt(this.getLilLineQt());
		il.setLineSum(this.getLilLineSum());

		/**
		 * calc new {@link InvoiceLine#tid}
		 * */
		il.setTid(blService.calcNextIlID(this.getInvoiceLocal()));

		this.getInvoiceLocal().getInvoicesLines().add(il);

		//calc and set Invoice total
		this.lilCalcAndSetInvoiceTotal();

		//clear fields
		this.lilFieldsClear();

		//clear Selected ID
		this.lilClearSelectedID();

		//Show this.getInvoiceLocal() lines
		this.showILIDs();

	}

	public void ilDelete(long id) {
		long ii = id;

		//delete il by id from the local invoice lines

		InvoiceLine il = blService.locateInvoiceLineById(this.getInvoiceLocal(), ii);

		this.getInvoiceLocal().getInvoicesLines().remove(il);

		//clear fields
		this.lilFieldsClear();

		//clear Selected ID
		this.lilClearSelectedID();

		//calc and set Invoice total
		this.lilCalcAndSetInvoiceTotal();

		//Show this.getInvoiceLocal() lines
		this.showILIDs();
	}

	public String createInvoice() {
		String res;

		if (this.getInvoiceLocal().getInvoicesLines().size() > 0) {
			Invoice invoice = this.getInvoiceLocal();
			invoiceService.createInvoice(invoice);
			res = "invoices?faces-redirect=true";
		} else {
			res = null;
		}
		return res;
	}

	private void showILIDs() {
		Invoice invoice = this.getInvoiceLocal();
		Iterator<InvoiceLine> iterator = invoice.getInvoicesLines().iterator();
		InvoiceLine invoiceLine;
		while (iterator.hasNext()) {
			invoiceLine = iterator.next();
		}
	}

	private void lilClearSelectedID() {
		this.setLilSelectedID(0);
	}

	private void lilCalcAndSetInvoiceTotal() {
		this.getInvoiceLocal().setSum(blService.calcInvoiceSum(this.getInvoiceLocal()));
	}

	private void lilCalcAndSetLineTotal() {
		float pr = this.getLilLinePrice();
		int qq = this.getLilLineQt();
		this.setLilLineSum(pr * qq);
	}

	private void lilFieldsClear() {
		this.setLilLineText("");
		this.setLilLinePrice(0);
		this.setLilLineQt(0);
		this.setLilLineSum(0);
	}

	public float getLilLineSum() {
		return lilLineSum;
	}

	public void setLilLineSum(float lilLineSum) {
		this.lilLineSum = lilLineSum;
	}

	public long getLilSelectedID() {
		return lilSelectedID;
	}

	public void setLilSelectedID(long lilSelectedID) {
		this.lilSelectedID = lilSelectedID;
	}

	public String getLilLineText() {
		return lilLineText;
	}

	public void setLilLineText(String lilLineText) {
		this.lilLineText = lilLineText;
	}

	public int getLilLineQt() {
		return lilLineQt;
	}

	public void setLilLineQt(int lilLineQt) {
		this.lilLineQt = lilLineQt;
	}

	public float getLilLinePrice() {
		return lilLinePrice;
	}

	public void setLilLinePrice(float lilLinePrice) {
		this.lilLinePrice = lilLinePrice;
	}

	public Invoice getInvoiceLocal() {
		return invoiceLocal;
	}

	public void setInvoiceLocal(Invoice invoiceLocal) {
		this.invoiceLocal = invoiceLocal;
	}

	public Person getPersonLocal() {
		return personLocal;
	}

	public void setPersonLocal(Person personLocal) {
		this.personLocal = personLocal;
	}

	public long getPersonIdForNewInvoice() {
		return personIdForNewInvoice;
	}

	public void setPersonIdForNewInvoice(long personIdForNewInvoice) {
		this.personIdForNewInvoice = personIdForNewInvoice;
	}

	public MBInvoiceNew() {
		invoiceLocal = new Invoice();
		this.setLilLineQt(1);
	}

}
