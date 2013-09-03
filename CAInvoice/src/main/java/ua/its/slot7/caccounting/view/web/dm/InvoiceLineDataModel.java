package ua.its.slot7.caccounting.view.web.dm;

import org.apache.log4j.Logger;
import org.primefaces.model.SelectableDataModel;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * CAccounting
 * 24.07.13 : 14:21
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class InvoiceLineDataModel
	extends ListDataModel<InvoiceLine>
	implements SelectableDataModel<InvoiceLine> {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	public InvoiceLineDataModel () {

	}

	public InvoiceLineDataModel(List<InvoiceLine> data) {
		super(data);
	}

	@Override
	public Object getRowKey(InvoiceLine object) {
		return object.getLineText();
	}

	@Override
	public InvoiceLine getRowData(String rowKey) {
		InvoiceLine invoiceLine = null;
		List<InvoiceLine> invoiceLines = (List<InvoiceLine>) getWrappedData();
		for (InvoiceLine invoiceLine1 : invoiceLines) {
			invoiceLine = invoiceLine1;
			if (invoiceLine.getLineText().equalsIgnoreCase(rowKey)) {
				return invoiceLine;
			}
		}
		return null;
	}
}
