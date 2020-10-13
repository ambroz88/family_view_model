package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface Paging {

    int calculateAllAncestorsX(AncestorPerson person);

    int calculateAllAncestorsWidth(AncestorPerson person);

    int calculateParentLineageWidth(AncestorPerson person);

    int calculateFatherLineageX(AncestorPerson person);

    int calculateFatherLineageWidth(AncestorPerson person);

    int calculateMotherLineageX(AncestorPerson person);

    int calculateMotherLineageWidth(AncestorPerson person);

    int calculateLineageY(AncestorPerson person, int pageHeight);

    int calculateLineageHeight(AncestorPerson person);

    int addFathersSiblingDimension(AncestorPerson person);

    int addMotherSiblingsWidth(AncestorPerson mother);

}