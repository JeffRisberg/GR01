package com.incra

import java.awt.Color

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.io.Resource

import com.incra.dto.ReportPanelDTO
import com.lowagie.text.Document
import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.Image
import com.lowagie.text.PageSize
import com.lowagie.text.Paragraph
import com.lowagie.text.Phrase
import com.lowagie.text.Rectangle
import com.lowagie.text.pdf.ColumnText
import com.lowagie.text.pdf.PdfContentByte
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfPageEventHelper
import com.lowagie.text.pdf.PdfWriter

/**
 * The <i>ActivityReportController</i> is a test case for the PDF generation technology.
 * 
 * @author Jeffrey Risberg
 * @since October 2011
 */
class ActivityReportController implements ApplicationContextAware {

    ApplicationContext applicationContext

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        log.debug("index running");

        // Create a document, using the response
        Document document = new Document(PageSize.LETTER, 35, 35, 55, 55);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream())

        // Prepare a few fonts
        Font font = new Font(Font.COURIER, 10, Font.BOLD);
        font.setColor(new Color(0xFF, 0xFF, 0xFF));
        Font headingFont = new Font(Font.COURIER, 10, Font.BOLD);
        headingFont.setColor(new Color(0xFF, 0x00, 0x00));

        HeaderFooter event = new HeaderFooter();
        writer.setBoxSize("art", new Rectangle(35, 35, 577, 757));
        writer.setPageEvent(event);

        // open the document
        document.open();

        List<ActivityType> activityTypes = ActivityType.findAll()

        for (ActivityType activityType : activityTypes) {
            document.add(new Paragraph(activityType.name))
        }

        PdfPTable table
        PdfPCell cell

        table = new PdfPTable(7);
        table.setWidths((int[])[1, 1, 1, 1, 1, 1, 1])
        table.setWidthPercentage((float) 95.0);

        Font tableFont = new Font(Font.HELVETICA, 9);
        Font legalFont = new Font(Font.HELVETICA, 7, Font.ITALIC);

        // Create and add a title across both columns.
        cell = new PdfPCell(new Paragraph("Activities", tableFont));
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(0xCC, 0xCC, 0xCC));
        cell.setPadding(4.0f);
        table.addCell(cell);

        // Add header cells for these columns.
        cell = new PdfPCell(new Paragraph("Facility", tableFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new Color(255, 255, 255));
        cell.setPadding(5.0f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Activity", tableFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new Color(255, 255, 255));
        cell.setPadding(5.0f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("From Resource", tableFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new Color(0xFF, 0x66, 0x33));
        cell.setPadding(5.0f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("From Amount", tableFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new Color(0xFF, 0x66, 0x33));
        cell.setPadding(5.0f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("To Resource", tableFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new Color(0x33, 0xCC, 0x00));
        cell.setPadding(5.0f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("To Amount", tableFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new Color(0x33, 0xCC, 0x00));
        cell.setPadding(5.0f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Description", tableFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new Color(255, 255, 255));
        cell.setPadding(5.0f);
        table.addCell(cell);
        document.add(table)

        ////////////////////////////////////////////
        // prepare an image
        Resource resource1 = applicationContext.getResource("images/activity/logo1.jpg")
        Image image1 = Image.getInstance(resource1.getURL());
        Resource resource2 = applicationContext.getResource("images/activity/logo2.jpg")
        Image image2 = Image.getInstance(resource2.getURL());
        Resource resource3 = applicationContext.getResource("images/activity/logo3.jpg")
        Image image3 = Image.getInstance(resource3.getURL());

        image1.scalePercent((float) 47)
        image2.scalePercent((float) 47)
        image3.scalePercent((float) 47)

        Font titleFont = new Font(Font.HELVETICA, 9, Font.NORMAL);
        titleFont.setColor(new Color(0xA0, 0xA0, 0xA0));
        Font labelFont = new Font(Font.HELVETICA, 8, Font.NORMAL);
        labelFont.setColor(new Color(0x00, 0x00, 0x00));
        Font valueFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
        Color gridBGColor = new Color(0xF0, 0xF0, 0xF0)
        Paragraph paragraph

        String groupTitle = "Health Outcomes"
        table = new PdfPTable(6)
        table.setWidths((int[])[4, 7, 4, 7, 4, 7])
        table.setSpacingBefore((float) 20)
        table.setHorizontalAlignment(0)
        table.setWidthPercentage((float) 44)

        cell = new PdfPCell(new Phrase(groupTitle, titleFont))
        cell.setBorder(0)
        cell.setBackgroundColor(gridBGColor)
        cell.setPaddingBottom((float) 5)
        cell.setColspan(6)
        table.addCell(cell)

        cell = new PdfPCell(image1)
        cell.setBorder(0)
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT)
        table.addCell(cell)
        cell = new PdfPCell()
        cell.setPadding((float) 2)
        cell.setBorder(0)
        paragraph = new Paragraph(7, "Miles Walked:", labelFont)
        paragraph.setAlignment(Element.ALIGN_CENTER)
        cell.addElement(paragraph)
        paragraph = new Paragraph(15, "56,789", valueFont)
        paragraph.setAlignment(Element.ALIGN_CENTER)
        cell.addElement(paragraph)
        table.addCell(cell)

        cell = new PdfPCell(image2)
        cell.setBorder(0)
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT)
        table.addCell(cell)
        cell = new PdfPCell()
        cell.setPadding((float) 2)
        cell.setBorder(0)
        paragraph = new Paragraph(7, "Pounds Lost:", labelFont)
        paragraph.setAlignment(Element.ALIGN_CENTER)
        cell.addElement(paragraph)
        paragraph = new Paragraph(15, "567,712", valueFont)
        paragraph.setAlignment(Element.ALIGN_CENTER)
        cell.addElement(paragraph)
        table.addCell(cell)

        cell = new PdfPCell(image3)
        cell.setBorder(0)
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT)
        table.addCell(cell)
        cell = new PdfPCell()
        cell.setPadding((float) 2)
        cell.setBorder(0)
        paragraph = new Paragraph(7, "Exercise Minutes:", labelFont)
        paragraph.setAlignment(Element.ALIGN_CENTER)
        cell.addElement(paragraph)
        paragraph = new Paragraph(15, "1,123,456", valueFont)
        paragraph.setAlignment(Element.ALIGN_CENTER)
        cell.addElement(paragraph)
        table.addCell(cell)

        document.add(table)

        // Now create using utility code
        ReportPanelDTO reportPanel = new ReportPanelDTO(applicationContext, "Health Outcomes")
        reportPanel.addIndicator("images/activity/logo1.jpg", 47.0, "Miles Walked:", "56,789")
        reportPanel.addIndicator("images/activity/logo2.jpg", 47.0, "Pounds Lost:", "567,712")
        reportPanel.addIndicator("images/activity/logo3.jpg", 47.0, "Exercise Minutes:", "1,123,456")

        table = reportPanel.render()
        table.setSpacingBefore((float) 20)
        table.setHorizontalAlignment(0)
        table.setWidthPercentage((float) 44)
        document.add(table)

        // stream the document
        response.setContentType("application/pdf");
        document.close();
        log.info("index done")
    }
}

/** Inner class to add a header and a footer. */
class HeaderFooter extends PdfPageEventHelper {
    /** Current page number (will be reset for every chapter). */
    int pageNumber;

    /**
     * Initialize one of the headers.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
        println "openDocument called"
        pageNumber = 1;
    }

    /**
     * Increase the page number.   
     */
    public void onStartPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Rectangle rect = writer.getBoxSize("art");

        ColumnText.showTextAligned(cb,
                (int) Element.ALIGN_LEFT, new Phrase("Activity Tracker"),
                (float) rect.getLeft(), (float) (rect.getTop() + 6), (float) 0);
        ColumnText.showTextAligned(cb,
                (int) Element.ALIGN_RIGHT, new Phrase(String.format("Page %d", pageNumber)),
                (float) rect.getRight(), (float) (rect.getTop() + 6), (float) 0);

        cb.rectangle((float) rect.getLeft(), (float) rect.getTop(),
                (float) (rect.getRight() - rect.getLeft()), (float) 1);
        cb.fill();
    }

    /**
     * Adds the header and the footer.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Rectangle rect = writer.getBoxSize("art");

        ColumnText.showTextAligned(cb,
                (int) Element.ALIGN_LEFT, new Phrase("Activity Tracker"),
                (float) rect.getLeft(), (float) (rect.getBottom()-12), (float) 0);
        ColumnText.showTextAligned(cb,
                (int) Element.ALIGN_RIGHT, new Phrase(String.format("Page %d", pageNumber)),
                (float) rect.getRight(), (float) (rect.getBottom()-12), (float) 0);

        cb.rectangle((float) rect.getLeft(), (float) rect.getBottom(),
                (float) (rect.getRight() - rect.getLeft()), (float) 1);
        cb.fill();

        pageNumber++;
    }
}
