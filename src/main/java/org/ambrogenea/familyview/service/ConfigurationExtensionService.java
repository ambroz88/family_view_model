package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.enums.LabelType;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface ConfigurationExtensionService {
    int getMarriageLabelWidth();
    int getMarriageLabelHeight();
    int getCoupleWidth();
    int getHalfSpouseLabelSpace();
    int getGapBetweenCouples();
    int getAllAncestorsCoupleIncrease();
    int getSpouseDistance();
    int getFatherHorizontalDistance();
    int getMotherHorizontalDistance();
    int getCoupleVerticalDifference();
    int getFatherVerticalDistance();
    int getMotherVerticalDistance();
    int getMarriageLabelVerticalDistance();
    LabelType getMarriageLabelType();
    int getGenerationsVerticalDistance();
    int getParentGenerationWidth(AncestorPerson person);
}
