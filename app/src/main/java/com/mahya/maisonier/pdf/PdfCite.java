/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.pdf;

/**
 * @author fabio
 */

import android.content.Context;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mahya.maisonier.CustumPageEvent;
import com.mahya.maisonier.activities.BaseActivity;
import com.mahya.maisonier.entites.Cite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PdfCite extends BaseActivity {

    private static final Font fontTirets = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
    private static Rectangle format = null;
    private static Rectangle art = null;
    private static PdfWriter writer = null;
    private static String outPutFilePath;
    private static Document document;
    private static String nomDuFichier;
    private final Context context;

    public PdfCite(Context context) {
        this.context = context;
    }

    private static void ecrireEntete(Document document) throws DocumentException {
        float[] ent = {4f, 1f, 4f};
        PdfPTable tete = new PdfPTable(ent);
        tete.setWidthPercentage(100);
        tete.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        tete.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tete.getDefaultCell().setMinimumHeight(10);
        tete.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        PdfPCell cell1;

        tete.addCell(new Phrase("REPUBLIQUE DU CAMEROUN", fontTirets));
        tete.addCell(" ");
        tete.addCell(new Phrase("REPUBLIC OF CAMEROON", fontTirets));

        tete.addCell(new Phrase("Paix - Travail - Patrie", fontTirets));
        tete.addCell("");
        tete.addCell(new Phrase("Peace - Work - Fatherland", fontTirets));

        cell1 = new PdfPCell(new Phrase("-----------------"));
        cell1.setFixedHeight(11);
        cell1.setBorder(PdfPCell.NO_BORDER);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_TOP);
        tete.addCell(cell1);
        tete.addCell("");
        tete.addCell(cell1);

        tete.addCell(new Phrase("MINISTERE DES FORETS ET DE LA FAUNE", fontTirets));
        tete.addCell("");
        tete.addCell(new Phrase("MINISTRY OF FORESTRY AND WILDLIFE", fontTirets));

        tete.addCell(cell1);
        tete.addCell("");
        tete.addCell(cell1);

        tete.addCell(new Phrase("SECRETARIAT GENERAL", fontTirets));
        tete.addCell("");
        tete.addCell(new Phrase("SECRETARIAT GENERAL", fontTirets));

        tete.addCell(cell1);
        tete.addCell("");
        tete.addCell(cell1);

        tete.addCell(new Phrase("DIRECTION DES FORETS", fontTirets));
        tete.addCell("");
        tete.addCell(new Phrase("FORESTRY DEPARTMENT", fontTirets));

        tete.addCell(cell1);
        tete.addCell("");
        tete.addCell(cell1);

        tete.addCell(new Phrase("Pool Technique-Système Informatique de Gestion des Informations Forestière", fontTirets));
        tete.addCell("");
        tete.addCell(new Phrase("Technical Pool-Computer System for Forestry Data Management", fontTirets));

        tete.addCell(cell1);
        tete.addCell("");
        tete.addCell(cell1);

        tete.addCell(new Phrase("", fontTirets));
        tete.addCell("");
        tete.addCell(new Phrase(" ", fontTirets));

        tete.addCell(cell1);
        tete.addCell("");
        tete.addCell(cell1);
        document.add(tete);

    }

    public File etatsCites(List<Cite> cites) {
        outPutFilePath = "";
        File outPutFilePath = null;
        try {
            getFile();
            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(date);
            outPutFilePath = new File(file.getAbsolutePath() + File.separator + timeStamp + ".pdf");


            format = PageSize.A4;
            art = new Rectangle(format.getLeft() + 25, format.getBottom() + 25, format.getRight() - 25, format.getTop());
            document = new Document(format, Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f));
            writer = PdfWriter.getInstance(document, new FileOutputStream(outPutFilePath));
            writer.setBoxSize("art", art);
            writer.setPageEvent(new CustumPageEvent(context));
            document.open();
            document.addTitle("DURVIL");
            document.addSubject("Etat liste des cites");
            document.addKeywords("Etat, gestion des cites");
            document.addCreator("durvilchat");
            document.addAuthor("rostapig@gmail.com");
            document.addHeader("Expires", "0");
            float[] widths1 = {0.5f, 2f, 2f, 4f, 2f, 2f, 4f};
            PdfPTable table = new PdfPTable(widths1);
            table.setWidthPercentage(100);
            table.setTotalWidth(art.getWidth());
            // ecrireEntete(document);

            PdfPCell cellNum = new PdfPCell(new Paragraph("N°", fontTirets));
            cellNum.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellNum);

            PdfPCell cell1Code = new PdfPCell(new Paragraph("NOM DU CITE", fontTirets));
            cell1Code.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell1Code);

            PdfPCell cell1bat = new PdfPCell(new Paragraph("SIEGE", fontTirets));
            cell1bat.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell1bat);

            PdfPCell cellDes = new PdfPCell(new Paragraph("BAILLEUR", fontTirets));
            cellDes.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellDes);

            PdfPCell cellEmail = new PdfPCell(new Paragraph("EMAIL", fontTirets));
            cellEmail.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellEmail);

            PdfPCell cellTels = new PdfPCell(new Paragraph("Tels", fontTirets));
            cellTels.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellTels);

            PdfPCell cellNom = new PdfPCell(new Paragraph("DESCRIPTION", fontTirets));
            cellNom.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellNom);

            int nbRegion = 0;
            for (Cite cite : cites) {

                //String numS = String.valueOf(batiment.getId());
                PdfPCell cellNumero = new PdfPCell(new Phrase((nbRegion + 1) + ""));
                cellNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellNumero);

                PdfPCell cellCode = new PdfPCell(new Phrase(cite.getNomCite()));
                cellCode.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellCode);

                PdfPCell cellBatiment = new PdfPCell(new Phrase(cite.getSiege()));
                cellBatiment.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellBatiment);

                PdfPCell cellDes1 = new PdfPCell(new Phrase(cite.getBailleur().load().getNom() + " " + cite.getBailleur().load().getPrenom()));
                cellDes1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellDes1);


                PdfPCell cell = new PdfPCell(new Phrase(cite.getEmail()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                PdfPCell cellTel = new PdfPCell(new Phrase(cite.getTels()));
                cellTel.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellTel);

                PdfPCell cellNomRegion = new PdfPCell(new Phrase(cite.getDescription()));
                cellNomRegion.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellNomRegion);
                nbRegion++;
            }

            Paragraph P5 = new Paragraph("Liste des cites\n\n");
            document.add(P5);
            document.add(table);

            Paragraph nombreBatiments = new Paragraph(new Phrase("Nombre de cite: " + String.valueOf(nbRegion)));
            nombreBatiments.setAlignment(Element.ALIGN_RIGHT);
            document.add(nombreBatiments);

            document.close();
            System.out.println("fermeture du fichier " + outPutFilePath);

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outPutFilePath;
    }


}
