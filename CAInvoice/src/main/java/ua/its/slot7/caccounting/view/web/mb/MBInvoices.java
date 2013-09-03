package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.communications.MailerWorkerAvatar;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.service.BLServiceAvatar;
import ua.its.slot7.caccounting.service.InvoiceServiceAvatar;
import ua.its.slot7.caccounting.system.BEntityPresenterAvatar;
import ua.its.slot7.caccounting.system.BSystemSettingsAvatar;
import ua.its.slot7.caccounting.view.web.dm.InvoiceDataModel;
import ua.its.slot7.camailtask.model.MailTask;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.List;

/**
 * CAccounting
 * 10.07.13 : 13:36
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
@ManagedBean(name = "MBInvoices")
@Scope("session")
@SessionScoped
public class MBInvoices implements Serializable {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private InvoiceServiceAvatar invoiceService;

	@Autowired
	private BLServiceAvatar blService;

	@Autowired
	private MBUserLogin mbUserLogin;

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	@Autowired
	private MailerWorkerAvatar mailerWorker;

	@Autowired
	private BEntityPresenterAvatar bEntityPresenter;

	private long invoiceIdToShow;

	private List<Invoice> invoicesList;
	private InvoiceDataModel invoiceDataModel;

	private List<Invoice> invoicesUnpaidList;

	private String invoiceNumberToSearch;
	private List<InvoiceLine> liInvoiceLines;
	private Invoice localInvoice;

	public void refreshInvoicesList () {
		mbUserLogin.refreshLoggedUser();
		this.setInvoicesList(
			this.invoiceService.getInvoicesByTheUser(mbUserLogin.getLoggedUser())
		);
		invoiceDataModel = new InvoiceDataModel(this.getInvoicesList());
	}

	public void refreshInvoicesUnpaidList () {
		mbUserLogin.refreshLoggedUser();
		this.setInvoicesUnpaidList(
			this.invoiceService.getInvoicesUnpaidByTheUser(mbUserLogin.getLoggedUser())
		);
	}

	public String invoiceSearchByNumber() {
		String res;
		res = this.getInvoiceNumberToSearch();
		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Search invoice",
				"Searching for "+res));
		Invoice invoice = invoiceService.getInvoiceByNumber(res);
		if (invoice==null) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"No results",
					"No results for "+res));
			return null;
		}
		mbUserLogin.refreshLoggedUser();
		User user = mbUserLogin.getLoggedUser();
		if (!invoice.getPerson().getUser().equals(user)) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"No results",
					"No results for "+res));
			return null;

		}
		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Search Result",
				invoice.toString()));
		this.setInvoiceNumberToSearch("");
		return "invoice?invoiceId="+invoice.getId()+"&faces-redirect=true";
	}

	public void invoiceGeneratePDF(long invoiceId) {
		LOGGER.info("MBInvoices.invoiceGeneratePDF");
	}

	public void invoiceSendByEmailPerson(long invoiceId) {
		Invoice invoice = invoiceService.getInvoiceById(invoiceId);

		String mf, mfn, mt, mtn, ms, mb;

		mf = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_EMAIL");
		mfn = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_NAME");

		mt = invoice.getPerson().getEmail();
		mtn = invoice.getPerson().getName();

		//TODO
		ms = "CAccounting : Invoice "+invoice.getNumber();

		//TODO Fix this (must be full text + bEntityPresenter.presentToHTML(invoice) )
		mb = bEntityPresenter.presentToHTML(invoice);

		MailTask mailTask = new MailTask(mf,
			mfn,
			mt,
			mtn,
			ms,
			mb,
			true);

		try {
			mailerWorker.sendOutboundMailTaskQMessage(mailTask);

			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Send by EMail to the Person",
					"EMail sending for " + invoice.getNumber()));

		} catch (JMSException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"Communications error. Please, try later."));
		}
	}

	public void invoiceMakePaid (long invoiceId) {
		Invoice invoice;
		invoice = invoiceService.getInvoiceById(invoiceId);
		invoiceService.makePaid(invoice);
		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Make paid",
				"Make paid successful for " + invoice.getNumber()));
	}

	public String invoiceDuplicate(long invoiceId) {
		String res=null;
		Invoice invoiceFrom, invoiceTo;
		invoiceFrom = invoiceService.getInvoiceById(invoiceId);
		invoiceTo = blService.invoiceCreateWithTemplate(invoiceFrom);
		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Duplicating invoice",
				"Duplicating invoice for " + invoiceTo.getPerson().getNick()));
		return res;
	}

	public List<Invoice> getInvoicesUnpaidList() {
		return invoicesUnpaidList;
	}

	public void setInvoicesUnpaidList(List<Invoice> invoicesUnpaidList) {
		this.invoicesUnpaidList = invoicesUnpaidList;
	}

	public long getInvoiceIdToShow() {
		return invoiceIdToShow;
	}

	public void setInvoiceIdToShow(long invoiceIdToShow) {
		this.invoiceIdToShow = invoiceIdToShow;
	}

	public List<Invoice> getInvoicesList() {
		return invoicesList;
	}

	public void setInvoicesList(List<Invoice> invoicesList) {
		this.invoicesList = invoicesList;
	}

	public String getInvoiceNumberToSearch() {
		return invoiceNumberToSearch;
	}

	public void setInvoiceNumberToSearch(String invoiceNumberToSearch) {
		this.invoiceNumberToSearch = invoiceNumberToSearch;
	}

	public List<InvoiceLine> getLiInvoiceLines() {
		return liInvoiceLines;
	}

	public void setLiInvoiceLines(List<InvoiceLine> liInvoiceLines) {
		this.liInvoiceLines = liInvoiceLines;
	}

	public Invoice getLocalInvoice() {
		return localInvoice;
	}

	public void setLocalInvoice(Invoice localInvoice) {
		this.localInvoice = localInvoice;
	}

	public InvoiceDataModel getInvoiceDataModel() {
		return invoiceDataModel;
	}

	public void setInvoiceDataModel(InvoiceDataModel invoiceDataModel) {
		this.invoiceDataModel = invoiceDataModel;
	}

	public MBInvoices () {

	}
}
