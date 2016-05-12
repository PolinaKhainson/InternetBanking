package ua.nure.khainson.SummaryTask4.web.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.PaymentStatus;
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.db.entity.Payment;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Create PDF report of payment command.
 * 
 * @author P.Khainson
 * 
 */

public class CreatePdfReportCommand extends Command {
	private static final Logger LOG = Logger
			.getLogger(CreatePdfReportCommand.class);
	private static final long serialVersionUID = 1505265797117841123L;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String id = request.getParameter("paymentId");
		LOG.trace("Request parameter: paymentId --> " + id);

		Long paymentId = Long.parseLong(id);
		DBManager manager = DBManager.getInstance();
		// obtain payment from DB by paymentId from request
		Payment payment = manager.findPayment(paymentId);
		LOG.trace("Find from DB: payment --> " + payment);
		// obtain account from DB for payment
		Account account = manager.findAccount(payment.getAccountId());
		LOG.trace("Find from DB: account --> " + account);
		// obtain credit card from DB for payment
		CreditCard creditCard = manager.findCreditCard(payment
				.getCreditCardId());
		LOG.trace("Find from DB: creditCard --> " + creditCard);
		// obtain user from DB for payment
		User user = manager.findUser(creditCard.getUserId());
		LOG.trace("Find from DB: creditCard --> " + creditCard);

		response.setContentType("application/pdf");
		LOG.trace("Response content type --> application/pdf");
		try {
			// create pdf document object
			Document document = new Document();
			LOG.trace("Get pdf document --> " + document);
			ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baosPDF);
			document.open();
			// document header
			Paragraph header = new Paragraph("Report for Payment ¹" + paymentId);
			header.setAlignment(Element.ALIGN_CENTER);
			header.setSpacingAfter(25);
			header.setSpacingBefore(25);
			Font headerFont = new Font(FontFamily.TIMES_ROMAN, 20.0f);
			header.setFont(headerFont);

			document.add(header);
			// document table
			PdfPTable table = new PdfPTable(7);
			LOG.trace("Table created -->" + table);
			PdfPCell cell1 = new PdfPCell(new Paragraph("Payer"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Payment Number"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Payment Status"));
			PdfPCell cell4 = new PdfPCell(new Paragraph(
					"Number of \nCredit Card"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Number of \nAccount"));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Sum of Payment"));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Date/Time of Payment"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell8 = new PdfPCell(new Paragraph(user.getFirstName()
					+ " " + user.getLastName()));
			PdfPCell cell9 = new PdfPCell(new Paragraph(Long.toString(payment
					.getId())));
			PdfPCell cell10 = new PdfPCell(new Paragraph(PaymentStatus
					.getPaymentStatus(payment).getName()));
			PdfPCell cell11 = new PdfPCell(new Paragraph(
					Long.toString(creditCard.getCardNumber())));
			PdfPCell cell12 = new PdfPCell(new Paragraph(Long.toString(account
					.getAccountNumber())));
			PdfPCell cell13 = new PdfPCell(new Paragraph(payment
					.getSumOfMoney().toString()));
			PdfPCell cell14 = new PdfPCell(new Paragraph(payment.getDateTime()
					.toString()));
			LOG.trace("Cells added to pdf document");
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.addCell(cell10);
			table.addCell(cell11);
			table.addCell(cell12);
			table.addCell(cell13);
			table.addCell(cell14);
			// add table to document
			document.add(table);
			LOG.trace("Table added to document");
			// adding result balance on account to document
			Paragraph paragraphAccountBalance = new Paragraph(
					"Current ballance on Account " + account.getAccountNumber()
							+ " is: " + account.getSumOnAccount());
			paragraphAccountBalance.setIndentationLeft(100);
			document.add(paragraphAccountBalance);
			// close document
			document.close();
			LOG.trace("Close document");
			ServletOutputStream sos;
			// show document on page
			sos = response.getOutputStream();
			baosPDF.writeTo(sos);
			LOG.trace("Write document to outputStream");
			sos.flush();
		} catch (DocumentException ex) {
			throw new AppException("Cannot create a pdf report for payment", ex);
		}
		LOG.debug("Command finished");
		return Path.PAGE_LIST_USER_PAYMENTS;

	}

}
