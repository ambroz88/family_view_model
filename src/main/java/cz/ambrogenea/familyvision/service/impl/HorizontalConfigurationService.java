package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.enums.LabelType;
import cz.ambrogenea.familyvision.model.dto.tree.Position;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.util.Services;

public class HorizontalConfigurationService implements ConfigurationExtensionService {

    private final VisualConfiguration configuration = Services.visual().get();

    @Override
    public int getMarriageLabelWidth() {
        return Spaces.HORIZ_MARRIAGE_LABEL_WIDTH;
    }

    @Override
    public int getMarriageLabelHeight() {
        return Spaces.HORIZ_MARRIAGE_LABEL_HEIGHT;
    }

    @Override
    public int getCoupleWidth() {
        return 2 * configuration.getAdultImageWidth() + getMarriageLabelWidth();
    }

    @Override
    public int getSpouseDistance() {
        return configuration.getAdultImageWidth() + getMarriageLabelWidth();
    }

    @Override
    public int getSiblingSpouseDistance() {
        return configuration.getSiblingImageWidth() + getMarriageLabelWidth();
    }

    @Override
    public Position getSiblingsWifePosition(Position husbandPosition) {
        return husbandPosition.addXAndY(
                getSiblingSpouseDistance(),
                getCoupleVerticalDifference()
        );
    }

    @Override
    public Position getFatherPositionFromHeraldry(Position heraldryPosition) {
        return heraldryPosition.addXAndY(
                -getSpouseDistance() / 2,
                -configuration.getHeraldryVerticalDistance()
        );
    }

    @Override
    public Position getMotherPositionFromHeraldry(Position heraldryPosition) {
        return heraldryPosition.addXAndY(
                getSpouseDistance() / 2,
                -configuration.getHeraldryVerticalDistance()
        );
    }

    @Override
    public Position getSiblingsMarriagePosition(Position siblingPosition) {
        return siblingPosition.addXAndY(getSiblingSpouseDistance() / 2, 0);
    }

    @Override
    public int getFatherHorizontalDistance() {
        return getSpouseDistance() / 2;
    }

    @Override
    public int getMotherHorizontalDistance() {
        return getSpouseDistance() / 2;
    }

    @Override
    public int getMarriageLabelVerticalDistance() {
        return configuration.getHeraldryVerticalDistance();
    }

    @Override
    public LabelType getMarriageLabelType() {
        return LabelType.TALL;
    }

    @Override
    public int getGenerationsVerticalDistance() {
        return configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP;
    }

    private int getCoupleVerticalDifference() {
        return 0;
    }

}
