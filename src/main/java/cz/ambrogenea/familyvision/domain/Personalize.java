package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.enums.Sex;

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
