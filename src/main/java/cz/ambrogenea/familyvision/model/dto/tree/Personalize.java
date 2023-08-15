package cz.ambrogenea.familyvision.model.dto.tree;

import cz.ambrogenea.familyvision.enums.Sex;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface Personalize {
    String firstName();
    String surname();
    Sex sex();
}
