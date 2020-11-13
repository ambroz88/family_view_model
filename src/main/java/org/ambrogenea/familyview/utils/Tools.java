package org.ambrogenea.familyview.utils;

import java.net.URL;
import java.time.LocalDate;
import java.util.Properties;

import org.ambrogenea.familyview.domain.Person;
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

    public static PersonRecord generateSamplePerson() {
        PersonRecord samplePerson = new PersonRecord(Sex.MALE, true);
        samplePerson.setFirstName("Vítězslav");
        samplePerson.setSurname("Konipásek");
        samplePerson.getBirthDatePlace().setDate(LocalDate.of(1869, 8, 24));
        samplePerson.getBirthDatePlace().setPlace("České Budějovice");
        samplePerson.getDeathDatePlace().setDate(LocalDate.of(1924, 12, 30));
        samplePerson.getDeathDatePlace().setPlace("České Budějovice");
        samplePerson.setOccupation("pekařský mistr");
        samplePerson.setLiving(false);

        return samplePerson;
    }

    public static PersonRecord generateSampleChild() {
        PersonRecord samplePerson = new PersonRecord(Sex.FEMALE, false);
        samplePerson.setFirstName("Julie");
        samplePerson.setSurname("Konipásková");
        samplePerson.getBirthDatePlace().setDate(LocalDate.of(1901, 1, 18));
        samplePerson.getBirthDatePlace().setPlace("České Budějovice");
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

    public static AncestorPerson createEmtpyWoman() {
        Person woman = new Person("0000");
        woman.setSex(Sex.FEMALE);
        AncestorPerson mother = new AncestorPerson(woman);
        mother.setDirectLineage(true);
        return mother;
    }

}
