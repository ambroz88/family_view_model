package org.ambrogenea.familyview.model;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.LabelShape;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonConfiguration {

    private AncestorModel ancestorModel;

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
    private LabelShape labelShape;
    private String adultManImagePath;
    private String adultWomanImagePath;
    private String siblingManImagePath;
    private String siblingWomanImagePath;

    private boolean showMarriage;

    private boolean showAge;
    private boolean showPlaces;
    private boolean shortenPlaces;
    private boolean showOccupation;
    private boolean showResidence;
    private boolean showTemple;

    public PersonConfiguration() {
        adultImageWidth = 180;
        adultImageHeight = 130;
        marriageLabelWidth = Math.max(Spaces.MIN_MARRIAGE_LABEL_WIDTH, adultImageWidth / 4 * 3);
        wideMarriageLabel = 3 * (adultImageWidth + marriageLabelWidth) / 2;
        adultTopOffset = 10;
        adultBottomOffset = 10;
        siblingImageWidth = 170;
        siblingImageHeight = 120;
        siblingBottomOffset = 10;
        siblingTopOffset = 10;
        adultFontSize = 14;
        siblingFontSize = 13;

        adultDiagram = Diagrams.STICKY;
        siblingDiagram = Diagrams.STICKY;
        labelShape = LabelShape.OVAL;
        adultManImagePath = "diagrams/" + adultDiagram + "_man.png";
        adultWomanImagePath = "diagrams/" + adultDiagram + "_woman.png";
        siblingManImagePath = "diagrams/" + siblingDiagram + "_man.png";
        siblingWomanImagePath = "diagrams/" + siblingDiagram + "_woman.png";

        showAge = true;
        showPlaces = false;
        shortenPlaces = false;
        showOccupation = true;
        showMarriage = true;
        showResidence = false;
        showTemple = false;
    }

    public AncestorModel getAncestorModel() {
        return ancestorModel;
    }

    public void setAncestorModel(AncestorModel ancestorModel) {
        this.ancestorModel = ancestorModel;
    }

    public int getAdultImageWidth() {
        return adultImageWidth;
    }

    public void setAdultImageWidth(int adultImageWidth) {
        this.adultImageWidth = adultImageWidth;
    }

    public int getAdultImageHeight() {
        return adultImageHeight;
    }

    public void setAdultImageHeight(int adultImageHeight) {
        this.adultImageHeight = adultImageHeight;
    }

    public int getWideMarriageLabel() {
        return wideMarriageLabel;
    }

    public void setWideMarriageLabel(int wideMarriageLabel) {
        this.wideMarriageLabel = wideMarriageLabel;
    }

    public int getMarriageLabelWidth() {
        return marriageLabelWidth;
    }

    public void setMarriageLabelWidth(int marriageLabelWidth) {
        this.marriageLabelWidth = marriageLabelWidth;
    }

    public int getAdultTopOffset() {
        return adultTopOffset;
    }

    public void setAdultTopOffset(int adultTopOffset) {
        this.adultTopOffset = adultTopOffset;
    }

    public int getAdultBottomOffset() {
        return adultBottomOffset;
    }

    public void setAdultBottomOffset(int adultBottomOffset) {
        this.adultBottomOffset = adultBottomOffset;
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

    public int getSiblingTopOffset() {
        return siblingTopOffset;
    }

    public void setSiblingTopOffset(int siblingTopOffset) {
        this.siblingTopOffset = siblingTopOffset;
    }

    public int getSiblingBottomOffset() {
        return siblingBottomOffset;
    }

    public void setSiblingBottomOffset(int siblingBottomOffset) {
        this.siblingBottomOffset = siblingBottomOffset;
    }

    public int getAdultFontSize() {
        return adultFontSize;
    }

    public void setAdultFontSize(int adultFontSize) {
        this.adultFontSize = adultFontSize;
    }

    public int getSiblingFontSize() {
        return siblingFontSize;
    }

    public void setSiblingFontSize(int siblingFontSize) {
        this.siblingFontSize = siblingFontSize;
    }

    public Diagrams getAdultDiagram() {
        return adultDiagram;
    }

    public void setAdultDiagram(Diagrams adultDiagram) {
        this.adultDiagram = adultDiagram;
    }

    public Diagrams getSiblingDiagram() {
        return siblingDiagram;
    }

    public void setSiblingDiagram(Diagrams siblingDiagram) {
        this.siblingDiagram = siblingDiagram;
    }

    public LabelShape getLabelShape() {
        return labelShape;
    }

    public void setLabelShape(LabelShape labelShape) {
        this.labelShape = labelShape;
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

    public boolean isShowMarriage() {
        return showMarriage;
    }

    public void setShowMarriage(boolean showMarriage) {
        this.showMarriage = showMarriage;
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

    public boolean isShortenPlaces() {
        return shortenPlaces;
    }

    public void setShortenPlaces(boolean shortenPlaces) {
        this.shortenPlaces = shortenPlaces;
    }

    public boolean isShowOccupation() {
        return showOccupation;
    }

    public void setShowOccupation(boolean showOccupation) {
        this.showOccupation = showOccupation;
    }

    public boolean isShowResidence() {
        return showResidence;
    }

    public void setShowResidence(boolean showResidence) {
        this.showResidence = showResidence;
    }

    public boolean isShowTemple() {
        return showTemple;
    }

    public void setShowTemple(boolean showTemple) {
        this.showTemple = showTemple;
    }

}
