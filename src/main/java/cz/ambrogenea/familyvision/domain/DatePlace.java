package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.enums.DateSpecification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DatePlace {

    private Date date;
    private DateSpecification dateSpecification;
    private String place;
    private String datePattern;

    public DatePlace() {
        place = "";
        datePattern = "d MMM yyyy";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DateSpecification getDateSpecification() {
        return dateSpecification;
    }

    public void setDateSpecification(DateSpecification dateSpecification) {
        this.dateSpecification = dateSpecification;
    }

    public String getPlace() {
        return place;
    }

    public String getSimplePlace() {
        return place.split(",")[0];
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public void parseDateText(String date) {
        String possibleSpecification = date.split(" ", 2)[0];
        try {
            dateSpecification = DateSpecification.valueOf(possibleSpecification);

            String parsedDate = date.split(" ", 2)[1];
            parseDate(parsedDate);
        } catch (IllegalArgumentException e) {
            parseDate(date);
        }
    }

    private void parseDate(String date) {
        String normalizeDate = date.toLowerCase();
        if (!normalizeDate.contains("sept")) {
            normalizeDate = normalizeDate.replace("sep", "sept");
        }
        try {
            parseFullDate(normalizeDate, Locale.UK);
        } catch (ParseException e) {
            try {
                Locale czech = new Locale("cs", "CZ");
                parseFullDate(normalizeDate, czech);
            } catch (ParseException exp) {
                try {
                    this.date = new SimpleDateFormat("yyyy").parse(normalizeDate);
                    this.datePattern = "yyyy";
                } catch (ParseException ex) {
                    this.datePattern = null;
                }
            }
        }
    }

    private void parseFullDate(String date, Locale locale) throws ParseException {
        try {
            this.date = DateFormat.getDateInstance(DateFormat.LONG, locale).parse(date);
            this.datePattern = "d MMM yyyy";
        } catch (ParseException ex1) {
            try {
                this.date = DateFormat.getDateInstance(DateFormat.MEDIUM, locale).parse(date);
                this.datePattern = "d MMM yyyy";
            } catch (ParseException ex2) {
                try {
                    this.date = DateFormat.getDateInstance(DateFormat.SHORT, locale).parse(date);
                    this.datePattern = "d MMM yyyy";
                } catch (ParseException ex3) {
                    this.date = new SimpleDateFormat("MMM yyyy", locale).parse(date);
                    this.datePattern = "MMM yyyy";
                }
            }
        }
    }

    public String getLocalizedDate(Locale locale) {
        if (datePattern == null) {
            return "?";
        }
        if (date != null) {
            SimpleDateFormat dtf = new SimpleDateFormat(datePattern, locale);
            if (dateSpecification != null) {
                return dateSpecification.getString(locale) + " " + dtf.format(date);
            }
            return dtf.format(date);
        } else {
            return "";
        }
    }
}
