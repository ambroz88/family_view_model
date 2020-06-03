package org.ambrogenea.familyview.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.ambrogenea.familyview.model.enums.Diagrams;
import org.ambrogenea.familyview.model.enums.PropertyName;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Configuration {

    private AncestorModel ancestorModel;

    public static final int MIN_MARRIAGE_LABEL_WIDTH = 100;
    private final PropertyChangeSupport prop;

    private int adultImageWidth;
    private int adultImageHeight;
    private int wideMarriageLabel;
    private int marriageLabelWidth;
    private int adultTopOffset;
    private int adultBottomOffset;
    private int siblingImageWidth;
    private int siblingImageHeight;
    private int siblingTopOffset;
    private int siblingBottomOffset;
    private int adultFontSize;
    private int siblingFontSize;

    private Diagrams adultDiagram;
    private Diagrams siblingDiagram;
    private String adultManImagePath;
    private String adultWomanImagePath;
    private String siblingManImagePath;
    private String siblingWomanImagePath;

    private boolean showFathersLineage;
    private boolean showMothersLineage;
    private boolean showSiblings;
    private boolean showSpouses;

    private boolean showParents;
    private boolean showChildren;
    private boolean showSiblingsFamily;
    private boolean showSpousesFamily;

    private boolean showAge;
    private boolean showPlaces;
    private boolean shortenPlaces;
    private boolean showOccupation;
    private boolean showMarriage;
    private boolean showResidence;
    private boolean showHeraldry;
    private boolean showTemple;

    private boolean resetMode;
    private int generationCount;

    public Configuration() {
        adultImageWidth = 170;
        adultImageHeight = 170;
        marriageLabelWidth = MIN_MARRIAGE_LABEL_WIDTH;
        wideMarriageLabel = 4 * (adultImageWidth - (adultImageWidth / 2 - marriageLabelWidth / 2));
        adultTopOffset = 30;
        adultBottomOffset = 30;
        siblingImageWidth = 170;
        siblingImageHeight = 170;
        siblingBottomOffset = 30;
        siblingTopOffset = 30;
        adultFontSize = 14;
        siblingFontSize = 14;

        adultDiagram = Diagrams.HERALDRY;
        siblingDiagram = Diagrams.PERGAMEN;
        adultManImagePath = "diagrams/" + adultDiagram + "_man.png";
        adultWomanImagePath = "diagrams/" + adultDiagram + "_woman.png";
        siblingManImagePath = "diagrams/" + siblingDiagram + "_man.png";
        siblingWomanImagePath = "diagrams/" + siblingDiagram + "_woman.png";

        showFathersLineage = true;
        showMothersLineage = false;
        showSiblings = true;
        showSpouses = false;

        showSiblingsFamily = true;
        showSpousesFamily = true;
        showParents = true;
        showChildren = true;

        showAge = true;
        showPlaces = false;
        shortenPlaces = false;
        showOccupation = true;
        showMarriage = true;
        showResidence = false;
        showHeraldry = true;
        showTemple = false;

        resetMode = false;
        generationCount = 10;

        prop = new PropertyChangeSupport(this);
    }

    public AncestorModel getAncestorModel() {
        return ancestorModel;
    }

    public void setAncestorModel(AncestorModel ancestorModel) {
        this.ancestorModel = ancestorModel;
    }

    public int getWideMarriageLabel() {
        return wideMarriageLabel;
    }

    public int getMarriageLabelWidth() {
        return marriageLabelWidth;
    }

    public int getAdultImageWidth() {
        return adultImageWidth;
    }

    public int getParentImageSpace() {
        return getAdultImageWidth() - (getAdultImageWidth() / 2 - getMarriageLabelWidth() / 2);
    }

    public int getSpouseLabelSpace() {
        return getAdultImageWidth() + getMarriageLabelWidth();
    }

    public int getHalfSpouseLabelSpace() {
        return (getAdultImageWidth() + getMarriageLabelWidth()) / 2;
    }

    public int getCoupleWidth() {
        return 2 * getAdultImageWidth() + getMarriageLabelWidth();
    }

    public void setAdultImageWidth(int adultImageWidth) {
        int oldValue = getAdultImageWidth();
        if (Math.abs(oldValue - adultImageWidth) > 4) {
            this.adultImageWidth = adultImageWidth;
            marriageLabelWidth = Math.max(MIN_MARRIAGE_LABEL_WIDTH, adultImageWidth / 3 * 2);
            wideMarriageLabel = 3 * adultImageWidth;
            setSiblingImageWidth(adultImageWidth);
            firePropertyChange(PropertyName.LINEAGE_SIZE_CHANGE, oldValue, adultImageWidth);
        }
    }

    public int getAdultImageHeight() {
        return adultImageHeight;
    }

    public void setAdultImageHeight(int adultImageHeight) {
        int oldValue = getAdultImageHeight();
        if (Math.abs(oldValue - adultImageHeight) > 4) {
            this.adultImageHeight = adultImageHeight;
            setSiblingImageHeight(adultImageHeight);
            firePropertyChange(PropertyName.LINEAGE_SIZE_CHANGE, oldValue, adultImageHeight);
        }
    }

    public int getSiblingImageWidth() {
        return siblingImageWidth;
    }

    public void setSiblingImageWidth(int siblingImageWidth) {
        int oldValue = getSiblingImageWidth();
        if (Math.abs(oldValue - siblingImageWidth) > 4) {
            this.siblingImageWidth = siblingImageWidth;
            firePropertyChange(PropertyName.SIBLING_SIZE_CHANGE, oldValue, siblingImageWidth);
        }
    }

    public int getSiblingImageHeight() {
        return siblingImageHeight;
    }

    public void setSiblingImageHeight(int siblingImageHeight) {
        int oldValue = getSiblingImageHeight();
        if (Math.abs(oldValue - siblingImageHeight) > 4) {
            this.siblingImageHeight = siblingImageHeight;
            firePropertyChange(PropertyName.SIBLING_SIZE_CHANGE, oldValue, siblingImageHeight);
        }
    }

    public int getAdultFontSize() {
        return adultFontSize;
    }

    public void setAdultFontSize(int adultFontSize) {
        int oldValue = getAdultFontSize();
        this.adultFontSize = adultFontSize;
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultFontSize);
    }

    public int getSiblingFontSize() {
        return siblingFontSize;
    }

    public void setSiblingFontSize(int siblingFontSize) {
        int oldValue = getSiblingFontSize();
        this.siblingFontSize = siblingFontSize;
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingFontSize);
    }

    public Diagrams getAdultDiagram() {
        return adultDiagram;
    }

    public void setAdultDiagram(Diagrams adultDiagram) {
        Diagrams oldValue = getAdultDiagram();
        this.adultDiagram = adultDiagram;
        setAdultManImagePath("diagrams/" + adultDiagram + "_man.png");
        setAdultWomanImagePath("diagrams/" + adultDiagram + "_woman.png");
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultDiagram);
    }

    public Diagrams getSiblingDiagram() {
        return siblingDiagram;
    }

    public void setSiblingDiagram(Diagrams siblingDiagram) {
        Diagrams oldValue = getSiblingDiagram();
        this.siblingDiagram = siblingDiagram;
        setSiblingManImagePath("diagrams/" + siblingDiagram + "_man.png");
        setSiblingWomanImagePath("diagrams/" + siblingDiagram + "_woman.png");
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingDiagram);
    }

    public String getAdultManImagePath() {
        return adultManImagePath;
    }

    private void setAdultManImagePath(String adultManImagePath) {
        this.adultManImagePath = adultManImagePath;
    }

    public String getAdultWomanImagePath() {
        return adultWomanImagePath;
    }

    private void setAdultWomanImagePath(String adultWomanImagePath) {
        this.adultWomanImagePath = adultWomanImagePath;
    }

    public String getSiblingManImagePath() {
        return siblingManImagePath;
    }

    private void setSiblingManImagePath(String siblingManImagePath) {
        this.siblingManImagePath = siblingManImagePath;
    }

    public String getSiblingWomanImagePath() {
        return siblingWomanImagePath;
    }

    private void setSiblingWomanImagePath(String siblingWomanImagePath) {
        this.siblingWomanImagePath = siblingWomanImagePath;
    }

    public int getAdultBottomOffset() {
        return adultBottomOffset;
    }

    public int getAdultTopOffset() {
        return adultTopOffset;
    }

    public void setAdultBottomOffset(int adultVerticalOffset) {
        int oldValue = getAdultBottomOffset();
        this.adultBottomOffset = adultVerticalOffset;
        setSiblingBottomOffset(adultVerticalOffset);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultVerticalOffset);
    }

    public void setAdultTopOffset(int adultVerticalOffset) {
        int oldValue = getAdultTopOffset();
        this.adultTopOffset = adultVerticalOffset;
        setSiblingBottomOffset(adultVerticalOffset);
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, adultVerticalOffset);
    }

    public int getSiblingBottomOffset() {
        return siblingBottomOffset;
    }

    public int getSiblingTopOffset() {
        return siblingTopOffset;
    }

    public void setSiblingBottomOffset(int siblingVerticalOffset) {
        int oldValue = getSiblingBottomOffset();
        this.siblingBottomOffset = siblingVerticalOffset;
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingVerticalOffset);
    }

    public void setSiblingTopOffset(int siblingVerticalOffset) {
        int oldValue = getSiblingTopOffset();
        this.siblingTopOffset = siblingVerticalOffset;
        firePropertyChange(PropertyName.SIBLING_CONFIG_CHANGE, oldValue, siblingVerticalOffset);
    }

    public boolean isShowSiblings() {
        return showSiblings;
    }

    public void setShowSiblings(boolean showSiblings) {
        this.showSiblings = showSiblings;
    }

    public boolean isShowSpouses() {
        return showSpouses;
    }

    public void setShowSpouses(boolean showSpouses) {
        this.showSpouses = showSpouses;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

    public boolean isShowSiblingsFamily() {
        return showSiblingsFamily;
    }

    public void setShowSiblingsFamily(boolean showSiblingsFamily) {
        this.showSiblingsFamily = showSiblingsFamily;
    }

    public boolean isShowSpousesFamily() {
        return showSpousesFamily;
    }

    public void setShowSpousesFamily(boolean showSpousesFamily) {
        this.showSpousesFamily = showSpousesFamily;
    }

    public boolean isShowParents() {
        return showParents;
    }

    public void setShowParents(boolean showParents) {
        this.showParents = showParents;
    }

    public boolean isShowChildren() {
        return showChildren;
    }

    public void setShowChildren(boolean showChildren) {
        this.showChildren = showChildren;
    }

    public boolean isShowFathersLineage() {
        return showFathersLineage;
    }

    public void setShowFathersLineage(boolean showFathersLineage) {
        this.showFathersLineage = showFathersLineage;
    }

    public boolean isShowMothersLineage() {
        return showMothersLineage;
    }

    public void setShowMothersLineage(boolean showMothersLineage) {
        this.showMothersLineage = showMothersLineage;
    }

    public boolean isShowAge() {
        return showAge;
    }

    public void setShowAge(boolean showAge) {
        boolean oldValue = isShowAge();
        this.showAge = showAge;
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showAge);
    }

    public boolean isShowPlaces() {
        return showPlaces;
    }

    public void setShowPlaces(boolean showPlaces) {
        boolean oldValue = isShowPlaces();
        this.showPlaces = showPlaces;
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showPlaces);
    }

    public boolean isShortenPlaces() {
        return shortenPlaces;
    }

    public void setShortenPlaces(boolean shortenPlaces) {
        boolean oldValue = isShortenPlaces();
        this.shortenPlaces = shortenPlaces;
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, shortenPlaces);
    }

    public boolean isShowTemple() {
        return showTemple;
    }

    public void setShowTemple(boolean showTemple) {
        boolean oldValue = isShowTemple();
        this.showTemple = showTemple;
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showTemple);
    }

    public boolean isShowMarriage() {
        return showMarriage;
    }

    public void setShowMarriage(boolean showMarriage) {
        this.showMarriage = showMarriage;
    }

    public boolean isShowHeraldry() {
        return showHeraldry;
    }

    public void setShowHeraldry(boolean showHeraldry) {
        this.showHeraldry = showHeraldry;
    }

    public boolean isShowOccupation() {
        return showOccupation;
    }

    public void setShowOccupation(boolean showOccupation) {
        boolean oldValue = isShowOccupation();
        this.showOccupation = showOccupation;
        firePropertyChange(PropertyName.LINEAGE_CONFIG_CHANGE, oldValue, showOccupation);
    }

    public boolean isShowResidence() {
        return showResidence;
    }

    public void setShowResidence(boolean showResidence) {
        this.showResidence = showResidence;
    }

    public boolean isResetMode() {
        return resetMode;
    }

    public void setResetMode(boolean resetMode) {
        this.resetMode = resetMode;
    }

    public void firePropertyChange(PropertyName prop, Object oldValue, Object newValue) {
        this.prop.firePropertyChange(prop.toString(), oldValue, newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.prop.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.prop.removePropertyChangeListener(listener);
    }

}
