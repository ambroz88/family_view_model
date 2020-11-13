package org.ambrogenea.familyview.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.ambrogenea.familyview.enums.Date;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DatePlace {

    private LocalDate date;
    private Date determination;
    private String place;
    private String datePattern;

    public DatePlace() {
        place = "";
        datePattern = "d MMM yyyy";
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Date getDetermination() {
        return determination;
    }

    public void setDetermination(Date determination) {
        this.determination = determination;
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

    public void parseDateText(String date) {
        String posibleDetermination = date.split(" ", 2)[0];
        try {
            determination = Date.valueOf(posibleDetermination);

            String parsedDate = date.split(" ", 2)[1];
            parseDate(parsedDate);
        } catch (IllegalArgumentException e) {
            parseDate(date);
        }
    }

    private void parseDate(String date) {
        try {
            this.date = LocalDate.parse(date, new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("d MMM yyyy")
                    .toFormatter(Locale.ENGLISH)
            );
        } catch (DateTimeParseException | NumberFormatException e) {

            try {
                this.date = LocalDate.ofYearDay(Integer.valueOf(date), 1);
                this.datePattern = "yyyy";
            } catch (DateTimeParseException | NumberFormatException exp) {
                try {
                    this.date = LocalDate.parse(date, new DateTimeFormatterBuilder()
                            .parseCaseInsensitive()
                            .appendPattern("MMM yyyy")
                            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                            .toFormatter(Locale.ENGLISH)
                    );
                    this.datePattern = "MMM yyyy";
                } catch (DateTimeParseException | NumberFormatException excep) {
                    this.datePattern = null;
                }
            }
        }
    }

    public String getLocalizedDate(Locale locale) {
        if (datePattern == null) {
            return "Unknown";
        }
        if (date != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern, locale);
            if (determination != null) {
                return determination.getString(locale) + " " + date.format(dtf);
            }
            return date.format(dtf);
        } else {
            return "";
        }
    }
}
