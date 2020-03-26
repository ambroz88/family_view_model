package org.ambrogenea.familyview.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.ambrogenea.familyview.model.utils.FileIO;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Configuration {

    private AncestorModel ancestorModel;

    public static final String CONFIG_CHANGE = "configChange";

    public static final String DIAGRAM_PERGAMEN = "pergamen";
    public static final String DIAGRAM_HERALDRY = "heraldry";
    public static final String DIAGRAM_WAVE = "wave";
    public static final String DIAGRAM_DOUBLEWAVE = "doublewave";

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
    private int fontSize;

    private String adultDiagram;
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
        adultImageHeight = 150;
        marriageLabelWidth = MIN_MARRIAGE_LABEL_WIDTH;
        wideMarriageLabel = 4 * (adultImageWidth - (adultImageWidth / 2 - marriageLabelWidth / 2));
        adultTopOffset = 30;
        adultBottomOffset = 30;
        siblingImageWidth = 150;
        siblingImageHeight = 120;
        siblingBottomOffset = 10;
        siblingTopOffset = 10;
        fontSize = 14;

        adultDiagram = DIAGRAM_PERGAMEN;
        adultManImagePath = FileIO.loadFileFromResources("/diagrams/" + adultDiagram + "_man.png").getPath();
        adultWomanImagePath = FileIO.loadFileFromResources("/diagrams/" + adultDiagram + "_woman.png").getPath();
        siblingManImagePath = "";
        siblingWomanImagePath = "";

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
        this.adultImageWidth = adultImageWidth;
        marriageLabelWidth = Math.max(MIN_MARRIAGE_LABEL_WIDTH, adultImageWidth / 3 * 2);
        wideMarriageLabel = 3 * adultImageWidth;
        firePropertyChange(CONFIG_CHANGE, oldValue, adultImageWidth);
    }

    public int getAdultImageHeight() {
        return adultImageHeight;
    }

    public void setAdultImageHeight(int adultImageHeight) {
        int oldValue = getAdultImageHeight();
        this.adultImageHeight = adultImageHeight;
        firePropertyChange(CONFIG_CHANGE, oldValue, adultImageHeight);
    }

    public int getSiblingImageWidth() {
        return siblingImageWidth;
    }

    public void setSiblingImageWidth(int siblingImageWidth) {
        this.siblingImageWidth = siblingImageWidth;
    }

    public int getSiblingImageHeight() {
        return siblingImageHeight;
    }

    public void setSiblingImageHeight(int siblingImageHeight) {
        this.siblingImageHeight = siblingImageHeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        int oldValue = getFontSize();
        this.fontSize = fontSize;
        firePropertyChange(CONFIG_CHANGE, oldValue, fontSize);
    }

    public String getAdultDiagram() {
        return adultDiagram;
    }

    public void setAdultDiagram(String adultDiagram) {
        String oldValue = getAdultDiagram();
        this.adultDiagram = adultDiagram;
        setAdultManImagePath(FileIO.loadFileFromResources("/diagrams/" + adultDiagram + "_man.png").getPath());
        setAdultWomanImagePath(FileIO.loadFileFromResources("/diagrams/" + adultDiagram + "_woman.png").getPath());
        firePropertyChange(CONFIG_CHANGE, oldValue, adultDiagram);
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

    public void setSiblingManImagePath(String siblingManImagePath) {
        this.siblingManImagePath = siblingManImagePath;
    }

    public String getSiblingWomanImagePath() {
        return siblingWomanImagePath;
    }

    public void setSiblingWomanImagePath(String siblingWomanImagePath) {
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
        firePropertyChange(CONFIG_CHANGE, oldValue, adultVerticalOffset);
    }

    public void setAdultTopOffset(int adultVerticalOffset) {
        int oldValue = getAdultTopOffset();
        this.adultTopOffset = adultVerticalOffset;
        firePropertyChange(CONFIG_CHANGE, oldValue, adultVerticalOffset);
    }

    public int getSiblingBottomOffset() {
        return siblingBottomOffset;
    }

    public int getSiblingTopOffset() {
        return siblingTopOffset;
    }

    public void setSiblingBottomOffset(int siblingVerticalOffset) {
        this.siblingBottomOffset = siblingVerticalOffset;
    }

    public void setSiblingTopOffset(int siblingVerticalOffset) {
        this.siblingTopOffset = siblingVerticalOffset;
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
        firePropertyChange(CONFIG_CHANGE, oldValue, showAge);
    }

    public boolean isShowPlaces() {
        return showPlaces;
    }

    public void setShowPlaces(boolean showPlaces) {
        boolean oldValue = isShowPlaces();
        this.showPlaces = showPlaces;
        firePropertyChange(CONFIG_CHANGE, oldValue, showPlaces);
    }

    public boolean isShortenPlaces() {
        return shortenPlaces;
    }

    public void setShortenPlaces(boolean shortenPlaces) {
        boolean oldValue = isShortenPlaces();
        this.shortenPlaces = shortenPlaces;
        firePropertyChange(CONFIG_CHANGE, oldValue, shortenPlaces);
    }

    public boolean isShowTemple() {
        return showTemple;
    }

    public void setShowTemple(boolean showTemple) {
        boolean oldValue = isShowTemple();
        this.showTemple = showTemple;
        firePropertyChange(CONFIG_CHANGE, oldValue, showTemple);
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
        firePropertyChange(CONFIG_CHANGE, oldValue, showOccupation);
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

    public void firePropertyChange(String prop, Object oldValue, Object newValue) {
        this.prop.firePropertyChange(prop, oldValue, newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        prop.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        prop.removePropertyChangeListener(listener);
    }

}
