package ua.its.slot7.caccounting.view.web.dm;

import org.apache.log4j.Logger;
import org.primefaces.model.SelectableDataModel;
import ua.its.slot7.caccounting.model.invoice.Invoice;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * CAccounting
 * 23.07.13 : 16:39
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class InvoiceDataModel
	extends ListDataModel<Invoice>
	implements SelectableDataModel<Invoice> {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	public InvoiceDataModel () {
	}

	public InvoiceDataModel(List<Invoice> data) {
		super(data);
	}

	@Override
	public Invoice getRowData(String rowKey) {
		Invoice ii = null;
		List<Invoice> invoices = (List<Invoice>) getWrappedData();
		for (Invoice invoice : invoices) {
			ii = invoice;
			if (ii.getNumber().equalsIgnoreCase(rowKey)) {
				return ii;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Invoice object) {
		return object.getNumber();
	}
}
