package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.enums.LabelType;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface ConfigurationExtensionService {
    int getMarriageLabelWidth();
    int getMarriageLabelHeight();
    int getCoupleWidth();
    int getSpouseDistance();
    int getSiblingSpouseDistance();
    Position getSiblingsWifePosition(Position husbandPosition);
    Position getFatherPositionFromHeraldry(Position heraldryPosition);
    Position getMotherPositionFromHeraldry(Position heraldryPosition);
    Position getSiblingsMarriagePosition(Position siblingPosition);
    int getFatherHorizontalDistance();
    int getMotherHorizontalDistance();
    int getMarriageLabelVerticalDistance();
    LabelType getMarriageLabelType();
    int getGenerationsVerticalDistance();
}
