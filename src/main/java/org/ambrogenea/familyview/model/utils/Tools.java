package org.ambrogenea.familyview.model.utils;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.ambrogenea.familyview.model.Information;
import org.ambrogenea.familyview.model.Person;

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
        plainText = plainText.replace(" ", "");
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

        SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat format3 = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

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

    public static String cityShortVersion(String placeName) {
        String filePath;
        URL fileURL = Tools.class.getResource("/text/CityShortcuts.properties");

        if (fileURL != null) {
            filePath = fileURL.getPath();
            Properties prop = FileIO.loadProperties(filePath);

            String normalizeName = placeName.toLowerCase().replace(" ", "");
            if (prop.containsKey(normalizeName)) {
                return prop.getProperty(normalizeName);
            }
        }

        return placeName;
    }

    private static String normalizeDate(String stringDate) {
        return stringDate.replace("ABT ", "").replace("BEF ", "").replace("TO ", "");
    }

    public static Person generateSamplePerson() {
        Person samplePerson = new Person("0000");
        samplePerson.setFirstName("Vítězslav");
        samplePerson.setSurname("Konipásek");
        samplePerson.setSex(Information.VALUE_MALE);
        samplePerson.setBirthDate("24 AUG 1869");
        samplePerson.setBirthPlace("České Budějovice");
        samplePerson.setDeathDate("30 DEC 1924");
        samplePerson.setDeathPlace("České Budějovice");
        samplePerson.setOccupation("pekařský mistr");
        samplePerson.setLiving(false);

        return samplePerson;
    }

}
