package org.ambrogenea.familyview.utils;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.ambrogenea.familyview.domain.Personalize;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.PersonRecord;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.service.ConfigurationService;

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
            return date1.compareTo(date2) < 0;
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

    public static PersonRecord generateSamplePerson() {
        PersonRecord samplePerson = new PersonRecord(Sex.MALE, true);
        samplePerson.setFirstName("Vítězslav");
        samplePerson.setSurname("Konipásek");
        samplePerson.setBirthDate("24 AUG 1869");
        samplePerson.setBirthPlace("České Budějovice");
        samplePerson.setDeathDate("30 DEC 1924");
        samplePerson.setDeathPlace("České Budějovice");
        samplePerson.setOccupation("pekařský mistr");
        samplePerson.setLiving(false);

        return samplePerson;
    }

    public static PersonRecord generateSampleChild() {
        PersonRecord samplePerson = new PersonRecord(Sex.FEMALE, false);
        samplePerson.setFirstName("Julie");
        samplePerson.setSurname("Konipásková");
        samplePerson.setBirthDate("18 JAN 1901");
        samplePerson.setBirthPlace("České Budějovice");
        samplePerson.setLiving(true);

        return samplePerson;
    }

    public static int calculateGenerations(AncestorPerson person, ConfigurationService config) {
        int generationCount = 1;

        if (config.isShowParents() && !person.getParents().isEmpty()) {
            generationCount++;
        }
        if (config.isShowChildren() && person.getAllChildrenCount() > 0) {
            generationCount++;
        }
        return generationCount;
    }

    public static String getNameIn2ndFall(Personalize person) {
        String firstName = person.getFirstName();
        String surName = person.getSurname();

        if (person.getSex().equals(Sex.MALE)) {

            if (firstName.endsWith("í")) {
                int index = firstName.lastIndexOf("í");
                firstName = firstName.substring(0, index) + "ího";
            } else if (firstName.endsWith("a")) {
                int index = firstName.lastIndexOf("a");
                firstName = firstName.substring(0, index) + "i";
            } else if (firstName.endsWith("o")) {
                int index = firstName.lastIndexOf("o");
                firstName = firstName.substring(0, index) + "a";
            } else if (firstName.endsWith("š") || firstName.endsWith("j")
                    || firstName.endsWith("ř")) {
                firstName += "e";
            } else if (firstName.endsWith("ek")) {
                int index = firstName.lastIndexOf("ek");
                firstName = firstName.substring(0, index) + "ka";
            } else {
                firstName += "a";
            }

            if (surName.endsWith("ek")) {
                int index = surName.lastIndexOf("ek");
                surName = surName.substring(0, index) + "ka";
            } else if (surName.endsWith("ša")) {
                int index = surName.lastIndexOf("ša");
                surName = surName.substring(0, index) + "ši";
            } else if (surName.endsWith("ja")) {
                int index = surName.lastIndexOf("ja");
                surName = surName.substring(0, index) + "ji";
            } else if (surName.endsWith("a")) {
                int index = surName.lastIndexOf("a");
                surName = surName.substring(0, index) + "y";
            } else if (surName.endsWith("ž") || surName.endsWith("š") || surName.endsWith("č")
                    || surName.endsWith("ř") || surName.endsWith("c") || surName.endsWith("j")) {
                surName += "e";
            } else if (surName.endsWith("ý")) {
                int index = surName.lastIndexOf("ý");
                surName = surName.substring(0, index) + "ého";
            } else if (surName.endsWith("í")) {
                int index = surName.lastIndexOf("í");
                surName = surName.substring(0, index) + "ího";
            } else if (surName.endsWith("o")) {
                int index = surName.lastIndexOf("o");
                surName = surName.substring(0, index) + "a";
            } else if (!surName.endsWith("ě") || !surName.endsWith("i") || !surName.endsWith("e")) {
                surName += "a";
            }

        } else {

            if (firstName.endsWith("ša")) {
                int index = firstName.lastIndexOf("ša");
                firstName = firstName.substring(0, index) + "ši";
            } else if (firstName.endsWith("ja")) {
                int index = firstName.lastIndexOf("ja");
                firstName = firstName.substring(0, index) + "ji";
            } else if (firstName.endsWith("a")) {
                int index = firstName.lastIndexOf("a");
                firstName = firstName.substring(0, index) + "y";
            }

            if (surName.endsWith("á")) {
                int index = surName.lastIndexOf("á");
                surName = surName.substring(0, index) + "é";
            }

        }

        return firstName + " " + surName;
    }
}
