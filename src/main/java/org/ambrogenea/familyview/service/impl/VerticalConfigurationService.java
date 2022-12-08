package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
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
    public int getHalfSpouseLabelSpace() {
        return (configService.getAdultImageWidth() + getMarriageLabelWidth()) / 2;
    }

    @Override
    public int getGapBetweenCouples() {
        return configService.getAdultImageWidth() / 3;
    }

    @Override
    public int getAllAncestorsCoupleIncrease() {
        return 2 * getMarriageLabelWidth();
    }

    @Override
    public int getSpouseDistance() {
        return getMarriageLabelWidth();
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
    public int getCoupleVerticalDifference() {
        return configService.getAdultImageHeightAlternative() + getMarriageLabelHeight();
    }

    @Override
    public int getFatherVerticalDistance() {
        return configService.getHeraldryVerticalDistance() + getCoupleVerticalDifference();
    }

    @Override
    public int getMotherVerticalDistance() {
        return configService.getHeraldryVerticalDistance();
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

    @Override
    public int getParentGenerationWidth(AncestorPerson person) {
        return 0;
    }

}
