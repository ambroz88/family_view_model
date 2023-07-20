package cz.ambrogenea.familyvision.mapper.dto;

import cz.ambrogenea.familyvision.dto.DatePlaceDto;
import cz.ambrogenea.familyvision.enums.DateSpecification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DatePlaceMapper {

    public static DatePlaceDto map(String date, String place) {
        DatePlaceDto datePlace = new DatePlaceDto();
        if (date != null) {
            String[] dateParts = date.split(" ", 2);
            if (dateParts.length == 2) {
                try {
                    datePlace.setDateSpecification(DateSpecification.valueOf(dateParts[0]));
                    parseDate(dateParts[1], datePlace);
                } catch (IllegalArgumentException e) {
                    parseDate(date, datePlace);
                }
            } else {
                parseDate(date, datePlace);
            }
        }

        if (place != null) {
            datePlace.setPlace(place);
        }
        return datePlace;
    }

    private static void parseDate(String date, DatePlaceDto datePlace) {
        String normalizeDate = date.toLowerCase();
        if (!normalizeDate.contains("sept")) {
            normalizeDate = normalizeDate.replace("sep", "sept");
        }
        try {
            parseFullDate(normalizeDate, Locale.UK, datePlace);
        } catch (ParseException e) {
            try {
                Locale czech = new Locale("cs", "CZ");
                parseFullDate(normalizeDate, czech, datePlace);
            } catch (ParseException exp) {
                try {
                    datePlace.setDate(new SimpleDateFormat("yyyy").parse(normalizeDate));
                    datePlace.setDatePattern("yyyy");
                } catch (ParseException ex) {
                }
            }
        }
    }

    private static void parseFullDate(String date, Locale locale, DatePlaceDto datePlace) throws ParseException {
        Date finalDate;
        String datePattern;
        try {
            finalDate = DateFormat.getDateInstance(DateFormat.LONG, locale).parse(date);
            datePattern = "d MMM yyyy";
        } catch (ParseException ex1) {
            try {
                finalDate = DateFormat.getDateInstance(DateFormat.MEDIUM, locale).parse(date);
                datePattern = "d MMM yyyy";
            } catch (ParseException ex2) {
                try {
                    finalDate = DateFormat.getDateInstance(DateFormat.SHORT, locale).parse(date);
                    datePattern = "d MMM yyyy";
                } catch (ParseException ex3) {
                    finalDate = new SimpleDateFormat("MMM yyyy", locale).parse(date);
                    datePattern = "MMM yyyy";
                }
            }
        }
        datePlace.setDate(finalDate);
        datePlace.setDatePattern(datePattern);
    }

}
