package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.PropertyName;
import org.ambrogenea.familyview.configuration.Configuration;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.service.ConfigurationService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class DefaultConfigurationService implements ConfigurationService {

    private final Configuration configuration;
    private final PropertyChangeSupport prop;

    public DefaultConfigurationService() {
        configuration = new Configuration();
        prop = new PropertyChangeSupport(this);
    }

    @Override
    public FamilyData getFamilyData() {
        return configuration.getFamilyData();
    }

    @Override
    public void setFamilyData(FamilyData familyData) {
        configuration.setFamilyData(familyData);
    }

    @Override
    public int getWideMarriageLabel() {
        return configuration.getWideMarriageLabel();
    }

    @Override
    public int getMarriageLabelWidth() {
        return configuration.getMarriageLabelWidth();
    }

    @Override
    public int getMarriageLabelHeight() {
        return Math.max((int) (getAdultImageHeight() * 0.2), Spaces.MIN_MARRIAGE_LABEL_HEIGHT);
    }

    @Override
    public int getAdultImageWidth() {
        return configuration.getAdultImageWidth();
    }

    @Override
    public int getParentImageSpace() {
        return (getAdultImageWidth() + getMarriageLabelWidth()) / 2;
    }

    @Override
    public int getSpouseLabelSpace() {
        return getAdultImageWidth() + getMarriageLabelWidth();
    }

    @Override
    public int getHalfSpouseLabelSpace() {
        return (getAdultImageWidth() + getMarriageLabelWidth()) / 2;
    }

    @Override
    public int getCoupleWidth() {
        if (isShowCouplesVertical()) {
            return 2 * getAdultImageWidth() - (int) (0.25 * getAdultImageWidth());
        } else {
            return 2 * getAdultImageWidth() + getMarriageLabelWidth();
        }
    }

    @Override
    public int getSpouseDistance() {
        if (isShowCouplesVertical()) {
            return getMarriageLabelWidth();
        } else {
            return getAdultImageWidth() + getMarriageLabelWidth();
        }
    }

    @Override
    public int getCoupleVerticalDifference() {
        return getAdultImageHeightAlternative() + getMarriageLabelHeight();
    }

    @Override
    public void setAdultImageWidth(int adultImageWidth) {
        int oldValue = getAdultImageWidth();
        if (Math.abs(oldValue - adultImageWidth) > 4) {
            configuration.setAdultImageWidth(adultImageWidth);
            configuration.setMarriageLabelWidth(Math.max(Spaces.MIN_MARRIAGE_LABEL_WIDTH, adultImageWidth / 3 * 2));
            configuration.setWideMarriageLabel(3 * getParentImageSpace());
            setSiblingImageWidth(adultImageWidth);
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
        if (getAdultDiagram().equals(Diagrams.PERGAMEN)) {
            imageHeight = (int) (imageHeight * 0.8);
        }
        return imageHeight;
    }

    @Override
    public void setAdultImageHeight(int adultImageHeight) {
        int oldValue = getAdultImageHeight();
        if (Math.abs(oldValue - adultImageHeight) > 4) {
            configuration.setAdultImageHeight(adultImageHeight);
            setSiblingImageHeight(adultImageHeight);
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
    public Diagrams getAdultDiagram() {
        return configuration.getAdultDiagram();
    }

    @Override
    public void setAdultDiagram(Diagrams adultDiagram) {
        Diagrams oldValue = getAdultDiagram();
        configuration.setAdultDiagram(adultDiagram);
        setAdultManImagePath("diagrams/" + adultDiagram + "_man.png");
        setAdultWomanImagePath("diagrams/" + adultDiagram + "_woman.png");
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultDiagram);
    }

    @Override
    public Diagrams getSiblingDiagram() {
        return configuration.getSiblingDiagram();
    }

    @Override
    public void setSiblingDiagram(Diagrams siblingDiagram) {
        Diagrams oldValue = getSiblingDiagram();
        configuration.setSiblingDiagram(siblingDiagram);
        setSiblingManImagePath("diagrams/" + siblingDiagram + "_man.png");
        setSiblingWomanImagePath("diagrams/" + siblingDiagram + "_woman.png");
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingDiagram);
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
    public String getAdultManImagePath() {
        return configuration.getAdultManImagePath();
    }

    private void setAdultManImagePath(String adultManImagePath) {
        configuration.setAdultManImagePath(adultManImagePath);
    }

    @Override
    public String getAdultWomanImagePath() {
        return configuration.getAdultWomanImagePath();
    }

    private void setAdultWomanImagePath(String adultWomanImagePath) {
        configuration.setAdultWomanImagePath(adultWomanImagePath);
    }

    @Override
    public String getSiblingManImagePath() {
        return configuration.getSiblingManImagePath();
    }

    private void setSiblingManImagePath(String siblingManImagePath) {
        configuration.setSiblingManImagePath(siblingManImagePath);
    }

    @Override
    public String getSiblingWomanImagePath() {
        return configuration.getSiblingWomanImagePath();
    }

    private void setSiblingWomanImagePath(String siblingWomanImagePath) {
        configuration.setSiblingWomanImagePath(siblingWomanImagePath);
    }

    @Override
    public int getAdultBottomOffset() {
        return configuration.getAdultBottomOffset();
    }

    @Override
    public int getAdultTopOffset() {
        return configuration.getAdultTopOffset();
    }

    @Override
    public void setAdultBottomOffset(int adultVerticalOffset) {
        int oldValue = getAdultBottomOffset();
        configuration.setAdultBottomOffset(adultVerticalOffset);
        setSiblingBottomOffset(adultVerticalOffset);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultVerticalOffset);
    }

    @Override
    public void setAdultTopOffset(int adultVerticalOffset) {
        int oldValue = getAdultTopOffset();
        configuration.setAdultTopOffset(adultVerticalOffset);
        setSiblingBottomOffset(adultVerticalOffset);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultVerticalOffset);
    }

    @Override
    public int getSiblingBottomOffset() {
        return configuration.getSiblingBottomOffset();
    }

    @Override
    public int getSiblingTopOffset() {
        return configuration.getSiblingTopOffset();
    }

    @Override
    public void setSiblingBottomOffset(int siblingVerticalOffset) {
        int oldValue = getSiblingBottomOffset();
        configuration.setSiblingBottomOffset(siblingVerticalOffset);
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingVerticalOffset);
    }

    @Override
    public void setSiblingTopOffset(int siblingVerticalOffset) {
        int oldValue = getSiblingTopOffset();
        configuration.setSiblingTopOffset(siblingVerticalOffset);
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingVerticalOffset);
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
    public boolean isShowParents() {
        return configuration.isShowParents();
    }

    @Override
    public void setShowParents(boolean showParents) {
        configuration.setShowParents(showParents);
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

}
