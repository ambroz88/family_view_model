package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.enums.LabelShape;
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
    public int getHalfSpouseLabelSpace() {
        return (configService.getAdultImageWidth() + getMarriageLabelWidth()) / 2;
    }

    @Override
    public int getGapBetweenCouples() {
        return getMarriageLabelWidth();
    }

    @Override
    public int getAllAncestorsCoupleIncrease() {
        return 0;
    }

    @Override
    public int getSpouseDistance() {
        return configService.getAdultImageWidth() + getMarriageLabelWidth();
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
    public int getCoupleVerticalDifference() {
        return 0;
    }

    @Override
    public int getFatherVerticalDistance() {
        return configService.getHeraldryVerticalDistance();
    }

    @Override
    public int getMotherVerticalDistance() {
        return configService.getHeraldryVerticalDistance();
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

    @Override
    public int getParentGenerationWidth(AncestorPerson person) {
        int ancestorGeneration = 0;
        if (person.getFather() != null && person.getFather().getAncestorGenerations() > 0) {
            ancestorGeneration = person.getFather().getAncestorGenerations();
        } else if (person.getMother() != null) {
            ancestorGeneration = person.getMother().getAncestorGenerations();
        }
        return getHalfSpouseLabelSpace() * Math.min(ancestorGeneration + 1, configService.getGenerationCount());
    }

}
