package org.ambrogenea.familyview.model.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class Tools {

    public static String getYear(String date) {
        String[] dateParts = date.split(" ");
        return dateParts[dateParts.length - 1];
    }

    public static String putYearToNewLine(String date) {
        int spaceIndex = date.lastIndexOf(" ");

        return date.substring(0, spaceIndex) + "\n" + date.substring(spaceIndex + 1);
    }

    public static boolean isEarlier(String dateBefore, String dateAfter) {
        String date1norm = dateBefore.replace("ABT ", "").replace("BEF ", "");
        String date2norm = dateAfter.replace("ABT ", "").replace("BEF ", "");

        Date date1 = convertDateString(date1norm);
        Date date2 = convertDateString(date2norm);

        if (date1 != null && date2 != null) {
            return date1.compareTo(date2) == -1;
        }
        return false;
    }

    public static Date convertDateString(String stringDate) {
        String dateNorm = stringDate.replace("ABT ", "").replace("BEF ", "");

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
}