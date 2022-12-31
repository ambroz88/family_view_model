package cz.ambrogenea.familyvision.utils;

import cz.ambrogenea.familyvision.domain.Personalize;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.enums.Sex;
import cz.ambrogenea.familyvision.service.util.Config;

import java.net.URL;
import java.util.Properties;

/**
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

    public static int calculateGenerations(AncestorPerson person) {
        int generationCount = 1;

        if (person.hasMinOneParent()) {
            generationCount++;
        }
        if (Config.treeShape().getDescendentGenerations() > 0 && person.getAllChildrenCount() > 0) {
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
