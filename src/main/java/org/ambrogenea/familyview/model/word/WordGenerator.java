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

    private static final int IMAGE_TOP_PADDING = 30;
    private static final int MAX_WIDTH = 24;
    private static int MAX_HEIGHT = 12;

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
        pageSize.setW(BigInteger.valueOf(16840));
        pageSize.setH(BigInteger.valueOf(11900));
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
            imageRun.setTextPosition(IMAGE_TOP_PADDING);
            imageRun.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, "", emuWidth, emuHeight);
            imageRun.addBreak(BreakType.PAGE);
        } catch (InvalidFormatException | IOException ex) {
            System.out.println("Image was not added to word document: " + ex.getMessage());
        }
    }

    public static void setMaxHeight(int generations) {
        if (generations == 1) {
            MAX_HEIGHT = 4;
        } else if (generations == 2) {
            MAX_HEIGHT = 8;
        } else if (generations == 3) {
            MAX_HEIGHT = 12;
        }
    }
}
