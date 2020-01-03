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
    private static final int TITLE_FONT_SIZE = 20;
    private static final int IMAGE_TOP_PADDING = 30;

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
        titleRun.setFontFamily("Monotype Corsiva");
        titleRun.setFontSize(TITLE_FONT_SIZE);
    }

    public static void addImageToPage(XWPFDocument document, InputStream imageStream, int imageWidth, int imageHeight) {
        try {
            int unitsWidth = Units.pointsToPixel(Units.EMU_PER_CENTIMETER * 17);
            double scale = Units.pixelToEMU(imageWidth) / (double) unitsWidth;
            int pixelHeight = (int) (imageHeight / scale);

            XWPFParagraph image = document.createParagraph();
            image.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun imageRun = image.createRun();
            imageRun.setTextPosition(IMAGE_TOP_PADDING);
            imageRun.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, "", unitsWidth, Units.pixelToEMU(pixelHeight));
            imageRun.addBreak(BreakType.PAGE);
        } catch (InvalidFormatException | IOException ex) {
            System.out.println("Image was not added to word document: " + ex.getMessage());
        }
    }

}
