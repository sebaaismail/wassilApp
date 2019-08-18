package main;


/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
    Authors: iText Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
/**
 * This example was written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/33726908/writing-arabic-in-pdf-using-itext
 */

        import com.itextpdf.io.font.PdfEncodings;
        import com.itextpdf.kernel.font.PdfFont;
        import com.itextpdf.kernel.font.PdfFontFactory;
        import com.itextpdf.kernel.pdf.PdfDocument;
        import com.itextpdf.kernel.pdf.PdfWriter;
        import com.itextpdf.layout.Document;
        import com.itextpdf.layout.element.Paragraph;
        import com.itextpdf.layout.element.Text;
        import com.itextpdf.layout.property.BaseDirection;
        import com.itextpdf.layout.property.TextAlignment;
        import com.itextpdf.licensekey.LicenseKey;


        import java.awt.*;
        import java.io.File;

public class ArabicExample {
    public static final String ARABIC
            = "\u0627\u0644\u0633\u0639\u0631 \u0627\u0644\u0627\u062c\u0645\u0627\u0644\u064a";
    public static final String DEST
            = "C:\\Users\\ismail\\Desktop\\result.pdf";
    public static final String FONT
            = "C:\\Windows\\Fonts/times.ttf";

    public static void main(String[] args) throws Exception {


        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ArabicExample().manipulatePdf(DEST);


            Desktop.getDesktop().open(file);

    }

    protected void manipulatePdf(String dest) throws Exception {


        //Load the license file to use advanced typography features
        LicenseKey.loadLicenseFile("C:\\Users\\ismail\\Desktop\\itextkey1565894938266_0.xml");

        /*
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        PdfFont f = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);

        Paragraph p = new Paragraph("This is auto detection: ");
        p.add(new Text(ARABIC).setFont(f));
        p.add(new Text(": 50.00 USD"));
        doc.add(p);


        p = new Paragraph("This is correct manual property: ").setBaseDirection(BaseDirection.LEFT_TO_RIGHT).setFontScript(Character.UnicodeScript.ARABIC);
        p.add(new Text(ARABIC).setFont(f));
        p.add(new Text(": 50.00"));
        doc.add(p);

        doc.close();

        //*/

        Document document = new Document(new PdfDocument(new PdfWriter(new File(DEST))));

        PdfFont bf = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        document.setFont(bf);

        document.add(new Paragraph(ARABIC).setTextAlignment(TextAlignment.RIGHT));

        document.close();
    }
}

