package org.ambrogenea.familyview.domain;

import org.ambrogenea.familyview.enums.Sex;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface Personalize {

    String getFirstName();

    String getSurname();

    String getName();

    Sex getSex();

}
