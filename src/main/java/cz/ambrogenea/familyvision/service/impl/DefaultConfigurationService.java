package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.configuration.Configuration;
import cz.ambrogenea.familyvision.constant.Dimensions;
import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;
import cz.ambrogenea.familyvision.enums.PropertyName;
import cz.ambrogenea.familyvision.service.ConfigurationService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DefaultConfigurationService implements ConfigurationService {

    private final Configuration configuration;
    private final PropertyChangeSupport prop;

    public DefaultConfigurationService(Configuration configuration) {
        this.configuration = configuration;
        prop = new PropertyChangeSupport(this);
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
            firePropertyChange(PropertyName.LINEAGE_SIZE_CHANGE, oldValue, adultImageWidth);
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
            firePropertyChange(PropertyName.LINEAGE_SIZE_CHANGE, oldValue, adultImageHeight);
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
            firePropertyChange(PropertyName.SIBLING_SIZE_CHANGE, oldValue, siblingImageWidth);
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
            firePropertyChange(PropertyName.SIBLING_SIZE_CHANGE, oldValue, siblingImageHeight);
        }
    }

    @Override
    public int getAdultFontSize() {
        return configuration.getAdultFontSize();
    }

    @Override
    public void setAdultFontSize(int adultFontSize) {
        int oldValue = getAdultFontSize();
        configuration.setAdultFontSize(adultFontSize);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultFontSize);
    }

    @Override
    public int getSiblingFontSize() {
        return configuration.getSiblingFontSize();
    }

    @Override
    public void setSiblingFontSize(int siblingFontSize) {
        int oldValue = getSiblingFontSize();
        configuration.setSiblingFontSize(siblingFontSize);
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingFontSize);
    }

    @Override
    public Diagram getDiagram() {
        return configuration.getDiagram();
    }

    @Override
    public void setDiagram(Diagram adultDiagram) {
        Diagram oldValue = getDiagram();
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

        setManImagePath("diagrams/" + adultDiagram + "_man.png");
        setWomanImagePath("diagrams/" + adultDiagram + "_woman.png");
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultDiagram);
    }

    @Override
    public LabelShape getLabelShape() {
        return configuration.getLabelShape();
    }

    @Override
    public void setLabelShape(LabelShape labelShape) {
        configuration.setLabelShape(labelShape);
    }

    @Override
    public String getManImagePath() {
        return configuration.getManImagePath();
    }

    private void setManImagePath(String adultManImagePath) {
        configuration.setManImagePath(adultManImagePath);
    }

    @Override
    public String getWomanImagePath() {
        return configuration.getWomanImagePath();
    }

    private void setWomanImagePath(String adultWomanImagePath) {
        configuration.setWomanImagePath(adultWomanImagePath);
    }

    @Override
    public int getVerticalShift() {
        return configuration.getVerticalShift();
    }

    @Override
    public void setVerticalShift(int adultVerticalShift) {
        int oldValue = getVerticalShift();
        configuration.setVerticalShift(adultVerticalShift);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultVerticalShift);
    }

    @Override
    public boolean isShowSiblings() {
        return configuration.isShowSiblings();
    }

    @Override
    public void setShowSiblings(boolean showSiblings) {
        configuration.setShowSiblings(showSiblings);
    }

    @Override
    public boolean isShowSpouses() {
        return configuration.isShowSpouses();
    }

    @Override
    public void setShowSpouses(boolean showSpouses) {
        configuration.setShowSpouses(showSpouses);
    }

    @Override
    public boolean isShowSiblingSpouses() {
        return configuration.isShowSiblingSpouses();
    }

    @Override
    public void setShowSiblingSpouses(boolean showSiblingSpouses) {
        configuration.setShowSiblingSpouses(showSiblingSpouses);
    }

    @Override
    public int getGenerationCount() {
        return configuration.getGenerationCount();
    }

    @Override
    public void setGenerationCount(int generationCount) {
        configuration.setGenerationCount(generationCount);
    }

    @Override
    public boolean isShowChildren() {
        return configuration.isShowChildren();
    }

    @Override
    public void setShowChildren(boolean showChildren) {
        configuration.setShowChildren(showChildren);
    }

    @Override
    public boolean isShowParentLineage() {
        return configuration.isShowParentLineage();
    }

    @Override
    public void setShowParentLineage(boolean showParentLineage) {
        configuration.setShowParentLineage(showParentLineage);
    }

    @Override
    public boolean isShowAge() {
        return configuration.isShowAge();
    }

    @Override
    public void setShowAge(boolean showAge) {
        boolean oldValue = isShowAge();
        configuration.setShowAge(showAge);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showAge);
    }

    @Override
    public boolean isShowPlaces() {
        return configuration.isShowPlaces();
    }

    @Override
    public void setShowPlaces(boolean showPlaces) {
        boolean oldValue = isShowPlaces();
        configuration.setShowPlaces(showPlaces);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showPlaces);
    }

    @Override
    public boolean isShortenPlaces() {
        return configuration.isShortenPlaces();
    }

    @Override
    public void setShortenPlaces(boolean shortenPlaces) {
        boolean oldValue = isShortenPlaces();
        configuration.setShortenPlaces(shortenPlaces);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, shortenPlaces);
    }

    @Override
    public boolean isShowTemple() {
        return configuration.isShowTemple();
    }

    @Override
    public void setShowTemple(boolean showTemple) {
        boolean oldValue = isShowTemple();
        configuration.setShowTemple(showTemple);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showTemple);
    }

    @Override
    public boolean isShowMarriage() {
        return configuration.isShowMarriage();
    }

    @Override
    public void setShowMarriage(boolean showMarriage) {
        configuration.setShowMarriage(showMarriage);
    }

    @Override
    public boolean isShowHeraldry() {
        return configuration.isShowHeraldry();
    }

    @Override
    public void setShowHeraldry(boolean showHeraldry) {
        configuration.setShowHeraldry(showHeraldry);
    }

    @Override
    public boolean isShowOccupation() {
        return configuration.isShowOccupation();
    }

    @Override
    public void setShowOccupation(boolean showOccupation) {
        boolean oldValue = isShowOccupation();
        configuration.setShowOccupation(showOccupation);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showOccupation);
    }

    @Override
    public boolean isShowResidence() {
        return configuration.isShowResidence();
    }

    @Override
    public void setShowResidence(boolean showResidence) {
        configuration.setShowResidence(showResidence);
    }

    @Override
    public boolean isShowCouplesVertical() {
        return configuration.isShowCouplesVertical();
    }

    @Override
    public void setShowCouplesVertical(boolean showCouplesVertical) {
        configuration.setShowCouplesVertical(showCouplesVertical);
    }

    @Override
    public boolean isResetMode() {
        return configuration.isResetMode();
    }

    @Override
    public void setResetMode(boolean resetMode) {
        configuration.setResetMode(resetMode);
    }

    @Override
    public void firePropertyChange(PropertyName propName, Object oldValue, Object newValue) {
        prop.firePropertyChange(propName.name(), oldValue, newValue);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        prop.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        prop.removePropertyChangeListener(listener);
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
