package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.enums.LabelType;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.util.Config;

public class VerticalConfigurationService implements ConfigurationExtensionService {

    private final VisualConfiguration configService;

    public VerticalConfigurationService() {
        this.configService = Config.visual();
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
