package org.ambrogenea.familyview.model;

import org.ambrogenea.familyview.model.utils.FileIO;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Configuration {

    private AncestorModel ancestorModel;

    public static final int MIN_MARRIAGE_LABEL_WIDTH = 100;

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

    private String adultManImagePath;
    private String adultWomanImagePath;
    private String siblingManImagePath;
    private String siblingWomanImagePath;
    private String boyImagePath;
    private String girlImagePath;

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
    private boolean showResidence;
    private boolean showHeraldry;
    private boolean showTemple;

    private int generationCount;

    public Configuration() {
        adultImageWidth = 150;
        adultImageHeight = 140;
        marriageLabelWidth = MIN_MARRIAGE_LABEL_WIDTH;
        wideMarriageLabel = 4 * (adultImageWidth - (adultImageWidth / 2 - marriageLabelWidth / 2));
        adultTopOffset = 30;
        adultBottomOffset = 30;
        siblingImageWidth = 150;
        siblingImageHeight = 120;
        siblingBottomOffset = 10;
        siblingTopOffset = 10;
        fontSize = 14;

        adultManImagePath = FileIO.loadFileFromResources("/diagrams/man_diagram.png").getPath();
        adultWomanImagePath = FileIO.loadFileFromResources("/diagrams/woman_diagram.png").getPath();
        siblingManImagePath = "";
        siblingWomanImagePath = "";
        boyImagePath = "";
        girlImagePath = "";

        showFathersLineage = true;
        showMothersLineage = false;
        showSiblings = true;
        showSpouses = false;

        showSiblingsFamily = true;
        showSpousesFamily = true;
        showParents = true;
        showChildren = true;

        showAge = true;
        showPlaces = true;
        showResidence = false;
        showHeraldry = true;
        showTemple = false;
        generationCount = 10;
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

    public void setAdultImageWidth(int adultImageWidth) {
        this.adultImageWidth = adultImageWidth;
        marriageLabelWidth = Math.max(MIN_MARRIAGE_LABEL_WIDTH, adultImageWidth / 3 * 2);
        wideMarriageLabel = 4 * (adultImageWidth - (adultImageWidth / 2 - marriageLabelWidth / 2));
    }

    public int getAdultImageHeight() {
        return adultImageHeight;
    }

    public void setAdultImageHeight(int adultImageHeight) {
        this.adultImageHeight = adultImageHeight;
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
        this.fontSize = fontSize;
    }

    public String getAdultManImagePath() {
        return adultManImagePath;
    }

    public void setAdultManImagePath(String adultManImagePath) {
        this.adultManImagePath = adultManImagePath;
    }

    public String getAdultWomanImagePath() {
        return adultWomanImagePath;
    }

    public void setAdultWomanImagePath(String adultWomanImagePath) {
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

    public String getBoyImagePath() {
        return boyImagePath;
    }

    public void setBoyImagePath(String boyImagePath) {
        this.boyImagePath = boyImagePath;
    }

    public String getGirlImagePath() {
        return girlImagePath;
    }

    public void setGirlImagePath(String girlImagePath) {
        this.girlImagePath = girlImagePath;
    }

    public int getAdultBottomOffset() {
        return adultBottomOffset;
    }

    public int getAdultTopOffset() {
        return adultTopOffset;
    }

    public void setAdultBottomOffset(int adultVerticalOffset) {
        this.adultBottomOffset = adultVerticalOffset;
    }

    public void setAdultTopOffset(int adultVerticalOffset) {
        this.adultTopOffset = adultVerticalOffset;
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
        this.showAge = showAge;
    }

    public boolean isShowPlaces() {
        return showPlaces;
    }

    public void setShowPlaces(boolean showPlaces) {
        this.showPlaces = showPlaces;
    }

    public boolean isShowTemple() {
        return showTemple;
    }

    public void setShowTemple(boolean showTemple) {
        this.showTemple = showTemple;
    }

    public boolean isShowHeraldry() {
        return showHeraldry;
    }

    public void setShowHeraldry(boolean showHeraldry) {
        this.showHeraldry = showHeraldry;
    }

    public boolean isShowResidence() {
        return showResidence;
    }

    public void setShowResidence(boolean showResidence) {
        this.showResidence = showResidence;
    }

}
