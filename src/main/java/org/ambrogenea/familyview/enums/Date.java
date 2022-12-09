package org.ambrogenea.familyview.enums;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Date {

    AFT, BEF, ABT, TO;

    public String getString(Locale locale) {
        ResourceBundle description = ResourceBundle.getBundle("language/date", locale);
        return description.getString(this.name());
    }
}
