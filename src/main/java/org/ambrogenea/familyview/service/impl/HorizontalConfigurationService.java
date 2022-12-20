package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.LabelType;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;

public class HorizontalConfigurationService implements ConfigurationExtensionService {

    private final ConfigurationService configService;

    public HorizontalConfigurationService(ConfigurationService configurationService) {
        this.configService = configurationService;
    }

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
        return 2 * configService.getAdultImageWidth() + getMarriageLabelWidth();
    }

    @Override
    public int getSpouseDistance() {
        return configService.getAdultImageWidth() + getMarriageLabelWidth();
    }

    @Override
    public int getSiblingSpouseDistance() {
        return configService.getSiblingImageWidth() + getMarriageLabelWidth();
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
                -configService.getHeraldryVerticalDistance()
        );
    }

    @Override
    public Position getMotherPositionFromHeraldry(Position heraldryPosition) {
        return heraldryPosition.addXAndY(
                getSpouseDistance() / 2,
                -configService.getHeraldryVerticalDistance()
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
        return configService.getHeraldryVerticalDistance();
    }

    @Override
    public LabelType getMarriageLabelType() {
        return LabelType.TALL;
    }

    @Override
    public int getGenerationsVerticalDistance() {
        return configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP;
    }

    private int getCoupleVerticalDifference() {
        return 0;
    }

}
