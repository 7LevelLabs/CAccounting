package ua.its.slot7.caccounting.system;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ua.its.slot7.caccounting.helper.InvoiceHelper;
import ua.its.slot7.caccounting.model.invoice.Invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * CAccounting
 * 01.09.13 : 16:16
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class BEntityPresenter implements BEntityPresenterAvatar {

	@Autowired
	private InvoiceHelper invoiceHelper;

	@Value("${template.encoding}")
	private String templateEncoding;

	@Value("${xhtml.header}")
	private String templateXhtmlHeader;

	@Value("${model.invoice}")
	private String templateInvoice;

	private VelocityEngine velocityEngine;

	@Override
	public String presentToHTML(final Invoice invoice) {
		String result;
		Map model = new HashMap();
		InvoiceHelper.InvoiceVO invoiceVO = invoiceHelper.getInvoiceVO(invoice);

		model.put("invoice", invoiceVO);

		result = VelocityEngineUtils.mergeTemplateIntoString(
			velocityEngine, templateInvoice, templateEncoding, model);
		return result;
	}

	/**
	 * Presents {@link ua.its.slot7.caccounting.model.invoice.Invoice} as XHTML
	 *
	 * @param invoice
	 */
	@Override
	public String presentToXHTML(final Invoice invoice) {

		String xhtmlHeader;
		Map model = new HashMap();

		model.put("title", invoice.getNumber());

		xhtmlHeader = VelocityEngineUtils.mergeTemplateIntoString(
			velocityEngine, templateXhtmlHeader, templateEncoding, model);

		StringBuilder sb = new StringBuilder();

		sb
			.append(xhtmlHeader)
			.append("\n")

			.append(presentToHTML(invoice))

			.append("\n")
			.append("</body>\n" +
				"\n" +
				"</html>")
		;

		return sb.toString();
	}

	/**
	 * Presents {@link ua.its.slot7.caccounting.model.invoice.Invoice} as PDF
	 *
	 * @param invoice
	 */
	@Override
	public File presentToPDF(final Invoice invoice) throws IOException, com.lowagie.text.DocumentException {

		File outputFilePDF = File.createTempFile("invoice-" + invoice.getNumber() + "-", ".pdf");

		File outputFileXHTML = File.createTempFile("tmpInvoice-" + invoice.getNumber() + "-", ".html");

		String xhtmlContent = presentToXHTML(invoice);

		ITextRenderer renderer = new ITextRenderer();

		OutputStream os = new FileOutputStream(outputFilePDF);

		writeFileHTML(xhtmlContent, outputFileXHTML);

		renderer.setDocument(outputFileXHTML.getCanonicalPath());

		renderer.layout();
		renderer.createPDF(os);
		os.close();

		outputFileXHTML.delete();

		return outputFilePDF;
	}

	private void writeFileHTML(final String content, final File outputFileHTML) throws IOException {
		FileOutputStream fop = new FileOutputStream(outputFileHTML);
		fop.write(content.getBytes());
		fop.flush();
		fop.close();
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
}
