package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.constant.Dimensions;
import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.enums.Background;
import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class VisualConfigurationServiceImpl implements VisualConfigurationService {

    private final VisualConfiguration configuration;

    public VisualConfigurationServiceImpl(VisualConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Locale getLocale() {
        return configuration.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        configuration.setLocale(locale);
    }

    @Override
    public int getHeraldryVerticalDistance() {
        return (getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2;
    }

    @Override
    public int nextSiblingX() {
        return getSiblingImageWidth() + Spaces.HORIZONTAL_GAP;
    }

    @Override
    public int nextChildrenX() {
        return getAdultImageWidth() + Spaces.HORIZONTAL_GAP;
    }

    @Override
    public int getAdultImageWidth() {
        return configuration.getAdultImageWidth();
    }

    @Override
    public void setAdultImageWidth(int adultImageWidth) {
        int oldValue = getAdultImageWidth();
        if (Math.abs(oldValue - adultImageWidth) > 4) {
            configuration.setAdultImageWidth(adultImageWidth);
        }
    }

    @Override
    public int getAdultImageHeight() {
        return configuration.getAdultImageHeight();
    }

    @Override
    public int getAdultImageHeightAlternative() {
        int imageHeight = getAdultImageHeight();
        if (getDiagram().equals(Diagram.SCROLL)) {
            imageHeight = (int) (imageHeight * 0.8);
        }
        return imageHeight;
    }

    @Override
    public void setAdultImageHeight(int adultImageHeight) {
        int oldValue = getAdultImageHeight();
        if (Math.abs(oldValue - adultImageHeight) > 4) {
            configuration.setAdultImageHeight(adultImageHeight);
        }
    }

    @Override
    public int getSiblingImageWidth() {
        return configuration.getSiblingImageWidth();
    }

    @Override
    public void setSiblingImageWidth(int siblingImageWidth) {
        int oldValue = getSiblingImageWidth();
        if (Math.abs(oldValue - siblingImageWidth) > 4) {
            configuration.setSiblingImageWidth(siblingImageWidth);
        }
    }

    @Override
    public int getSiblingImageHeight() {
        return configuration.getSiblingImageHeight();
    }

    @Override
    public int getSiblingImageHeightAlternative() {
        int siblingImageHeight = configuration.getSiblingImageHeight();
        if (getDiagram().equals(Diagram.SCROLL)) {
            siblingImageHeight = (int) (siblingImageHeight * 0.8);
        }
        return siblingImageHeight;
    }

    @Override
    public void setSiblingImageHeight(int siblingImageHeight) {
        int oldValue = getSiblingImageHeight();
        if (Math.abs(oldValue - siblingImageHeight) > 4) {
            configuration.setSiblingImageHeight(siblingImageHeight);
        }
    }

    @Override
    public int getAdultFontSize() {
        return configuration.getAdultFontSize();
    }

    @Override
    public void setAdultFontSize(int adultFontSize) {
        configuration.setAdultFontSize(adultFontSize);
    }

    @Override
    public int getSiblingFontSize() {
        return configuration.getSiblingFontSize();
    }

    @Override
    public void setSiblingFontSize(int siblingFontSize) {
        configuration.setSiblingFontSize(siblingFontSize);
    }

    @Override
    public Diagram getDiagram() {
        return configuration.getDiagram();
    }

    @Override
    public void setDiagram(Diagram adultDiagram) {
        configuration.setDiagram(adultDiagram);
        int imageWidth;
        int imageHeight;
        if (adultDiagram == Diagram.HERALDRY) {
            imageWidth = Dimensions.PORTRAIT_IMAGE_WIDTH;
            imageHeight = Dimensions.PORTRAIT_IMAGE_HEIGHT;
        } else if (adultDiagram == Diagram.SCROLL) {
            imageWidth = Dimensions.SCROLL_IMAGE_WIDTH;
            imageHeight = Dimensions.SCROLL_IMAGE_HEIGHT;
        } else if (adultDiagram == Diagram.DOUBLE_WAVE) {
            imageWidth = Dimensions.DOUBLE_WAVE_IMAGE_WIDTH;
            imageHeight = Dimensions.DOUBLE_WAVE_IMAGE_HEIGHT;
        } else {
            imageWidth = Dimensions.DEFAULT_IMAGE_WIDTH;
            imageHeight = Dimensions.DEFAULT_IMAGE_HEIGHT;
        }
        setAdultImageWidth(imageWidth);
        setAdultImageHeight(imageHeight);
        setSiblingImageWidth(imageWidth);
        setSiblingImageHeight(imageHeight);
    }

    @Override
    public LabelShape getMarriageLabelShape() {
        return configuration.getMarriageLabelShape();
    }

    @Override
    public void setMarriageLabelShape(LabelShape labelShape) {
        configuration.setMarriageLabelShape(labelShape);
    }

    @Override
    public Background getBackground() {
        return configuration.getBackground();
    }

    @Override
    public void setBackground(Background background) {
        configuration.setBackground(background);
    }

    @Override
    public int getVerticalShift() {
        return configuration.getVerticalShift();
    }

    @Override
    public void setVerticalShift(int adultVerticalShift) {
        configuration.setVerticalShift(adultVerticalShift);
    }

    @Override
    public boolean isShowAge() {
        return configuration.isShowAge();
    }

    @Override
    public void setShowAge(boolean showAge) {
        configuration.setShowAge(showAge);
    }

    @Override
    public boolean isShowPlaces() {
        return configuration.isShowPlaces();
    }

    @Override
    public void setShowPlaces(boolean showPlaces) {
        configuration.setShowPlaces(showPlaces);
    }

    @Override
    public boolean isShortenPlaces() {
        return configuration.isShortenPlaces();
    }

    @Override
    public void setShortenPlaces(boolean shortenPlaces) {
        configuration.setShortenPlaces(shortenPlaces);
    }

    @Override
    public boolean isShowOrdinances() {
        return configuration.isShowOrdinances();
    }

    @Override
    public void setShowOrdinances(boolean showOrdinances) {
        configuration.setShowOrdinances(showOrdinances);
    }

    @Override
    public boolean isShowOccupation() {
        return configuration.isShowOccupation();
    }

    @Override
    public void setShowOccupation(boolean showOccupation) {
        configuration.setShowOccupation(showOccupation);
    }

    @Override
    public boolean isShowTitle() {
        return configuration.isShowTitle();
    }

    @Override
    public void setShowTitle(boolean showTitle) {
        configuration.setShowTitle(showTitle);
    }

    @Override
    public boolean isResetMode() {
        return configuration.isResetMode();
    }

    @Override
    public void setResetMode(boolean resetMode) {
        configuration.setResetMode(resetMode);
    }

}
