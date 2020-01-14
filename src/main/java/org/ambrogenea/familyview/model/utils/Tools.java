package org.ambrogenea.familyview.model.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class Tools {

    public static String replaceDiacritics(String originalText) {
        String plainText = originalText.toLowerCase();
        plainText = plainText.replace("č", "c").replace("ř", "r").replace("ž", "z").replace("š", "s").replace("ě", "e");
        plainText = plainText.replace("ň", "n").replace("ď", "d").replace("ť", "t");
        plainText = plainText.replace("ö", "o");
        return plainText;
    }

    public static String getYear(String date) {
        String[] dateParts = date.split(" ");
        return dateParts[dateParts.length - 1];
    }

    public static String translateDateToCzech(String date) {
        String dateCzech = date.replace("ABT", "asi").replace("BEF", "před").replace("TO", "před").replace("AFT", "po");
        dateCzech = dateCzech.replace("JAN", "led").replace("FEB", "úno").replace("MAR", "bře");
        dateCzech = dateCzech.replace("APR", "dub").replace("MAY", "kvě").replace("JUN", "črn");
        dateCzech = dateCzech.replace("JUL", "črc").replace("AUG", "srp").replace("SEP", "zář");
        dateCzech = dateCzech.replace("OCT", "říj").replace("NOV", "lis").replace("DEC", "pro");
        return dateCzech;
    }

    public static String putYearToNewLine(String date) {
        int spaceIndex = date.lastIndexOf(" ");

        return date.substring(0, spaceIndex) + "\n" + date.substring(spaceIndex + 1);
    }

    public static boolean isEarlier(String dateBefore, String dateAfter) {
        String date1norm = normalizeDate(dateBefore);
        String date2norm = normalizeDate(dateAfter);

        Date date1 = convertDateString(date1norm);
        Date date2 = convertDateString(date2norm);

        if (date1 != null && date2 != null) {
            return date1.compareTo(date2) == -1;
        }
        return false;
    }

    public static Date convertDateString(String stringDate) {
        String dateNorm = normalizeDate(stringDate);

        SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format3 = new SimpleDateFormat("MMM yyyy");

        Date date;
        try {
            date = format1.parse(dateNorm);
        } catch (ParseException ex) {
            try {
                date = format2.parse(dateNorm);
            } catch (ParseException ex1) {
                try {
                    date = format3.parse(dateNorm);
                } catch (ParseException ex2) {
                    date = null;
                }
            }
        }
        return date;
    }

    private static String normalizeDate(String stringDate) {
        return stringDate.replace("ABT ", "").replace("BEF ", "").replace("TO ", "");
    }

}
