package org.ambrogenea.familyview.model.word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class WordGenerator {

    private static final String COLOR_BLACK = "000000";
    private static final String TITLE_FONT = "Monotype Corsiva";
    private static final int TITLE_FONT_SIZE = 20;
    private static final String TEXT_FONT = "Calibri";
    private static final int TEXT_FONT_SIZE = 11;

    private static final int MAX_WIDTH = 19;
    private static int MAX_HEIGHT = 12;
    private static int GENERATION_HEIGHT = 4;

    public static XWPFDocument createWordDocument() {
        XWPFDocument document = new XWPFDocument();
        CTBody body = document.getDocument().getBody();

        if (!body.isSetSectPr()) {
            body.addNewSectPr();
        }
        CTSectPr section = body.getSectPr();

        if (!section.isSetPgSz()) {
            section.addNewPgSz();
        }
        CTPageSz pageSize = section.getPgSz();

        pageSize.setOrient(STPageOrientation.LANDSCAPE);
        pageSize.setW(BigInteger.valueOf(11900));
        pageSize.setH(BigInteger.valueOf(8400));
        /* multiply by 20 to put it to above setters
        Letter       612x792
        LetterSmall  612x792
        Tabloid      792x1224
        Ledger       1224x792
        Legal        612x1008
        Statement    396x612
        Executive    540x720
        A0           2384x3371
        A1           1685x2384
        A2           1190x1684
        A3           842x1190
        A4           595x842
        A4Small      595x842
        A5           420x595
        B4           729x1032
        B5           516x729
        Folio        612x936
        Quarto       610x780
        10x14        720x1008 */
        CTSectPr sectPr = body.addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        // 1mm = 5,68
        pageMar.setLeft(BigInteger.valueOf(795L));
//        pageMar.setLeft(BigInteger.valueOf(682L));
        pageMar.setTop(BigInteger.valueOf(568L));
        pageMar.setRight(BigInteger.valueOf(568L));
        pageMar.setBottom(BigInteger.valueOf(568L));

        return document;
    }

    public static void writeDocument(String fileName, XWPFDocument document) throws IOException {
        try (FileOutputStream out = new FileOutputStream(new File(fileName))) {
            document.write(out);
            document.close();
        }
    }

    public static void createFamilyPage(XWPFDocument document, String titleText) {
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        title.setSpacingAfter(0);

        XWPFRun titleRun = title.createRun();
        titleRun.setText(titleText);
        titleRun.setColor(COLOR_BLACK);
        titleRun.setBold(true);
        titleRun.setFontFamily(TITLE_FONT);
        titleRun.setFontSize(TITLE_FONT_SIZE);
    }

    public static void addImageToPage(XWPFDocument document, InputStream imageStream, int imageWidth, int imageHeight) {
        try {
            int emuHeight = Units.EMU_PER_CENTIMETER * MAX_HEIGHT;
            int emuMaxWidth = Units.EMU_PER_CENTIMETER * MAX_WIDTH;

            double scale = Units.pixelToEMU(imageHeight) / (double) emuHeight;
            int pixelWidth = (int) (imageWidth / scale);
            int emuWidth = Units.pixelToEMU(pixelWidth);

            if (emuWidth > emuMaxWidth) {
                emuWidth = emuMaxWidth;
                scale = Units.pixelToEMU(imageWidth) / (double) emuWidth;
                int pixelHeight = (int) (imageHeight / scale);
                emuHeight = Units.pixelToEMU(pixelHeight);
            }

            XWPFParagraph image = document.createParagraph();
            image.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun imageRun = image.createRun();
            imageRun.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, "", emuWidth, emuHeight);

            addFamilyDescription(document);
        } catch (InvalidFormatException | IOException ex) {
            System.out.println("Image was not added to word document: " + ex.getMessage());
        }
    }

    private static void addFamilyDescription(XWPFDocument document) {
        XWPFParagraph blankInfo = document.createParagraph();
        blankInfo.setAlignment(ParagraphAlignment.CENTER);
        blankInfo.setSpacingBetween(2);
        XWPFRun description = blankInfo.createRun();
        description.setText("Informace o rodině:  __________________________________________________________________________\n"
                + "___________________________________________________________________________________________\n"
                + "___________________________________________________________________________________________\n"
                + "___________________________________________________________________________________________\n");
        description.setFontFamily(TEXT_FONT);
        description.setFontSize(TEXT_FONT_SIZE);
        description.addBreak(BreakType.PAGE);
    }

    public static void setMaxHeight(int generations) {
        if (generations != 0) {
            MAX_HEIGHT = generations * GENERATION_HEIGHT;
        }
    }
}
