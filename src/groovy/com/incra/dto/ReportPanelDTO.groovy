package com.incra.dto

import java.awt.Color

import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource

import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.Image
import com.lowagie.text.Paragraph
import com.lowagie.text.Phrase
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable

/**
 * The <i>ReportPanel</i> provides a utility for creating a table on a PDF that holds
 * indicators, along with their images and labels.  Depends upon iText 2.1.7.
 * 
 * @author Jeffrey Risberg
 * @since 10/28/11
 */
class ReportPanelDTO {
  private ApplicationContext applicationContext
  private String name
  private List<ReportPanelIndicator> indicators

  /** Constructor */
  public ReportPanelDTO(ApplicationContext applicationContext, String name) {
    this.applicationContext = applicationContext
    this.name = name;
    this.indicators = new ArrayList<ReportPanelIndicator>()
  }

  public void addIndicator(String imgSrc, Float scale, String label, String value) {
    ReportPanelIndicator rpi = new ReportPanelIndicator(imgSrc: imgSrc, scale: scale, label: label, value: value)
    indicators.add(rpi)
  }

  public PdfPTable render() {
    int size = indicators.size()
    PdfPTable table = new PdfPTable((int) 2*size)
    PdfPCell cell

    Font titleFont = new Font(Font.HELVETICA, 11, Font.NORMAL);
    titleFont.setColor(new Color(0x30, 0x30, 0x30));
    Font labelFont = new Font(Font.HELVETICA, 8, Font.NORMAL);
    labelFont.setColor(new Color(0x00, 0x00, 0x00));
    Font valueFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
    Color cellBgColor = new Color(0xE0, 0xE0, 0xE0)
    Paragraph paragraph

    int[] widths = new int[2*size]
    for (int i = 0; i < size; i++) {
      widths[2*i] = 4;
      widths[2*i+1] = 7
    }
    table.setWidths(widths)

    if (name) {
      cell = new PdfPCell(new Phrase(name, titleFont))
      cell.setBorder(0)
      cell.setBackgroundColor(cellBgColor)
      cell.setPaddingTop((float) 0)
      cell.setPaddingBottom((float) 3)
      cell.setColspan((int) 2*size)
      table.addCell(cell)
    }

    for (ReportPanelIndicator indicator : indicators) {
      if (indicator.imgSrc) {
        Resource resource = applicationContext.getResource(indicator.imgSrc)
        Image image = Image.getInstance(resource.getURL())

        image.scalePercent((float) indicator.scale)

        cell = new PdfPCell(image)
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT)
      }
      else {
        cell = new PdfPCell(new Phrase(""))
      }
      cell.setBorder(0)
      table.addCell(cell)

      cell = new PdfPCell()
      cell.setPadding((float) 2)
      cell.setPaddingLeft((float) 5)
      cell.setBorder(0)

      if (indicator.label) {
        paragraph = new Paragraph(7, indicator.label, labelFont)
        paragraph.setAlignment(Element.ALIGN_LEFT)
        cell.addElement(paragraph)
      }
      if (indicator.value) {
        paragraph = new Paragraph(15, indicator.value, valueFont)
        paragraph.setAlignment(Element.ALIGN_CENTER)
        cell.addElement(paragraph)
      }
      table.addCell(cell)
    }

    return table
  }
}

class ReportPanelIndicator {
  String imgSrc
  Float scale
  String label
  String value
}