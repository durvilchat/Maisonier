package com.mahya.maisonier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.ImageView;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustumPageEvent extends PdfPageEventHelper {

    private final static String HEADER = "Ce document est la propriété du MINFOF";
    private final static String FOOTER = "@copiright 2015 IA's";
    protected PdfTemplate total;
    protected BaseFont helv;
    protected PdfGState gstate;
    protected Image image;
     protected Image filigranePhoto;
    //protected Color color;
//    public static final String RESOURCE = Utilitaires.path +"/resources/images/cic.gif";
    public static final String RESOURCE = "/cm/minfof/ressources/background.png";
//    public static final String FILIGRANE = "/home/gervais/NetBeansProjects/SIGIF/src/cm/minfof/ressources/icons/logoSIGIF_128x128.png";
  //  public static final String FILIGRANE = "/cm/minfof/ressources/icons/background.png";
   public static final String FILIGRANE = "/home/ulrich/NetBeansProjects/Maisonier2/filigrane.png";
    



    public CustumPageEvent(Context context) {
        super();
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            this.image  = Image.getInstance(stream.toByteArray());
            this.filigranePhoto = Image.getInstance(stream.toByteArray());
       } catch (BadElementException ex) {
            Logger.getLogger(CustumPageEvent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CustumPageEvent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CustumPageEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            total = writer.getDirectContent().createTemplate(30, 20);
            try {
                helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
            gstate = new PdfGState();
            gstate.setFillOpacity(0.3f);
            gstate.setStrokeOpacity(0.3f);
            PdfContentByte contentunder = writer.getDirectContentUnder();
            contentunder.addImage(image, image.getWidth()/4, 0, 0, image.getHeight()/4, 30, 500);
            
        } catch (DocumentException ex) {
            Logger.getLogger(CustumPageEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * public void onChapter(PdfWriter writer, Document document, float
     * paragraphPosition, Paragraph title) { header[1] = new
     * Phrase(title.getContent()); pagenumber = 1; } public void
     * onStartPage(PdfWriter writer, Document document) { pagenumber++; }
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        Font ft = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.ITALIC, BaseColor.GRAY);
        PdfPTable table = new PdfPTable(3);
        try {
            table.setWidths(new int[]{40, 8, 2});
        } catch (DocumentException e1) {
        }
        table.setTotalWidth(rect.getWidth());
        table.setLockedWidth(true);
        table.getDefaultCell().setFixedHeight(10);
        table.getDefaultCell().setBorder(Rectangle.TOP);

        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell cell = new PdfPCell(new Phrase(FOOTER, ft));
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Page : " + writer.getCurrentPageNumber() + " sur ", ft));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        try {
            cell = new PdfPCell(Image.getInstance(total));
        } catch (BadElementException e) {
        }
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        table.writeSelectedRows(0, -1, rect.getLeft(), rect.getBottom(), writer.getDirectContent());

//		ColumnText.showTextAligned(writer.getDirectContent(),
//				Element.ALIGN_LEFT, new Phrase(HEADER, ft), rect.getLeft(),
//				rect.getTop(), 0);
        PdfPTable table2 = new PdfPTable(2);
        try {
            table2.setWidths(new int[]{30, 20});
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            System.out.println( "Erreur innatendu dans le gestion de page");
//            e1.printStackTrace();
        }
        table2.setTotalWidth(rect.getWidth());
        table2.setLockedWidth(true);
        table2.getDefaultCell().setFixedHeight(10);
        table2.getDefaultCell().setBorder(Rectangle.BOTTOM);

        table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        cell = new PdfPCell(new Phrase(HEADER, ft));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(cell);
        Object[] obj = {new Date(System.currentTimeMillis())};
        cell = new PdfPCell(new Phrase(MessageFormat.format("A {0,time,medium}    Le  {0, date,long}.", obj), ft));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(cell);

        table2.writeSelectedRows(0, -1, rect.getLeft(), rect.getTop(),
                writer.getDirectContent());
         ColumnText.showTextAligned(writer.getDirectContent(),
         Element.ALIGN_LEFT, new Phrase(FOOTER, ft), rect.getLeft(),
         rect.getBottom(), 0);
         ColumnText.showTextAligned(writer.getDirectContent(),
         Element.ALIGN_RIGHT,
         new Phrase("Page " + writer.getCurrentPageNumber() + "/"
         + writer.getPageNumber(), ft), rect.getRight() - 50,
         rect.getBottom(), 0);

        PdfContentByte contentunder = writer.getDirectContentUnder();
        contentunder.saveState();
        contentunder.setGState(gstate);
        contentunder.beginText();
        contentunder.setFontAndSize(helv, 48);
        contentunder.showTextAligned(Element.ALIGN_CENTER, "World Wide Services", document.getPageSize().getWidth() / 2, document.getPageSize().getHeight() / 2, 45);
        contentunder.endText();
        contentunder.restoreState();
        
        
        try {
             total = writer.getDirectContent().createTemplate(30, 20);
            try {
                helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
            gstate = new PdfGState();
            gstate.setFillOpacity(0.3f);
            gstate.setStrokeOpacity(0.3f);
            contentunder.addImage(filigranePhoto, filigranePhoto.getWidth(), 0, 0, filigranePhoto.getHeight(), 0, 100);
            
        } catch (DocumentException ex) {
            Logger.getLogger(CustumPageEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        Font ft = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.ITALIC, BaseColor.RED);
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1), ft), 2, 8, 0);
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        super.onStartPage(writer, document); //To change body of generated methods, choose Tools | Templates.
        /*if (writer.getPageNumber() % 2 == 1) {
            color = Color.BLUE;
        } else {
            color = Color.red;
        }*/
    }
}
