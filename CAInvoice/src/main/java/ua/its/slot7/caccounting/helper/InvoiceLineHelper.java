package ua.its.slot7.caccounting.helper;

import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.invoiceline.InvoiceLine;

/**
 * @author Alex Velichko
 *         18.04.14 : 14:04
 */
@Component
public class InvoiceLineHelper {

	public InvoiceLineVO getInvoiceLineVO(final InvoiceLine invoiceLine) {
		return new InvoiceLineVO(invoiceLine.getLineText(),
			Integer.toString(invoiceLine.getLineQt()),
			invoiceLine.getLinePrice().toString(),
			invoiceLine.getLineTotal().toString(),
			invoiceLine.getLineTax().toString());
	}

	public class InvoiceLineVO {

		private String lineText;
		private String lineQt;
		private String linePrice;
		private String lineTotal;
		private String lineTax;

		public InvoiceLineVO(final String lineText,
					final String lineQt,
					final String linePrice,
					final String lineTotal,
					final String lineTax) {
			this.lineText = lineText;
			this.lineQt = lineQt;
			this.linePrice = linePrice;
			this.lineTotal = lineTotal;
			this.lineTax = lineTax;
		}
	}

}
