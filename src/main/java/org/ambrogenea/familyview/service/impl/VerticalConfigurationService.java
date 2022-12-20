package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.LabelType;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;

public class VerticalConfigurationService implements ConfigurationExtensionService {

    private final ConfigurationService configService;

    public VerticalConfigurationService(ConfigurationService configurationService) {
        this.configService = configurationService;
    }

    @Override
    public int getMarriageLabelWidth() {
        return Math.max(Spaces.MIN_VERT_MARRIAGE_LABEL_WIDTH, (int) (configService.getAdultImageWidth() / 3.0 * 2));
    }

    @Override
    public int getMarriageLabelHeight() {
        return Spaces.VERT_MARRIAGE_LABEL_HEIGHT;
    }

    @Override
    public int getCoupleWidth() {
        return (int) (configService.getAdultImageWidth() / 3.0 * 5);
    }

    @Override
    public int getSpouseDistance() {
        return getMarriageLabelWidth();
    }

    @Override
    public int getSiblingSpouseDistance() {
        return Math.max(Spaces.MIN_VERT_MARRIAGE_LABEL_WIDTH, (int) (configService.getSiblingImageWidth() / 3.0 * 2));
    }

    @Override
    public Position getSiblingsWifePosition(Position husbandPosition) {
        return husbandPosition.addXAndY(
                getSiblingSpouseDistance(),
                getSiblingsCoupleVerticalDifference()
        );
    }

    @Override
    public Position getFatherPositionFromHeraldry(Position heraldryPosition) {
        return heraldryPosition.addXAndY(
                0,
                -configService.getHeraldryVerticalDistance() - getCoupleVerticalDifference()
        );
    }

    @Override
    public Position getMotherPositionFromHeraldry(Position heraldryPosition) {
        return heraldryPosition.addXAndY(
                getMarriageLabelWidth(),
                -configService.getHeraldryVerticalDistance()
        );
    }

    @Override
    public Position getSiblingsMarriagePosition(Position siblingPosition) {
        return siblingPosition.addXAndY(
                0,
                getSiblingsCoupleVerticalDifference() / 2
        );
    }

    @Override
    public int getFatherHorizontalDistance() {
        return 0;
    }

    @Override
    public int getMotherHorizontalDistance() {
        return getMarriageLabelWidth();
    }

    @Override
    public int getMarriageLabelVerticalDistance() {
        return configService.getHeraldryVerticalDistance() + getCoupleVerticalDifference() / 2;
    }

    @Override
    public LabelType getMarriageLabelType() {
        return LabelType.LONG;
    }

    @Override
    public int getGenerationsVerticalDistance() {
        return 2 * configService.getAdultImageHeightAlternative() + getMarriageLabelHeight() + Spaces.VERTICAL_GAP;
    }

    private int getCoupleVerticalDifference() {
        return configService.getAdultImageHeightAlternative() + getMarriageLabelHeight();
    }

    private int getSiblingsCoupleVerticalDifference() {
        return configService.getSiblingImageHeightAlternative() + getMarriageLabelHeight();
    }

}
