package org.ambrogenea.familyview.configuration;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.LabelShape;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonConfiguration {

    private FamilyData familyData;
    private Locale locale;

    private int adultImageWidth;
    private int adultImageHeight;
    private int adultVerticalShift;
    private int adultFontSize;

    private int siblingImageWidth;
    private int siblingImageHeight;
    private int siblingFontSize;

    private Diagrams adultDiagram;
    private LabelShape labelShape;
    private String adultManImagePath;
    private String adultWomanImagePath;

    private boolean showMarriage;

    private boolean showAge;
    private boolean showPlaces;
    private boolean shortenPlaces;
    private boolean showOccupation;
    private boolean showResidence;
    private boolean showTemple;

    public PersonConfiguration() {
        adultImageWidth = 190;
        adultImageHeight = 130;
        adultVerticalShift = 0;
        siblingImageWidth = 170;
        siblingImageHeight = 120;
        adultFontSize = 12;
        siblingFontSize = 11;

        adultDiagram = Diagrams.SCROLL;
        labelShape = LabelShape.OVAL;
        adultManImagePath = "diagrams/" + adultDiagram + "_man.png";
        adultWomanImagePath = "diagrams/" + adultDiagram + "_woman.png";

        showAge = true;
        showPlaces = true;
        shortenPlaces = false;
        showOccupation = true;
        showMarriage = true;
        showResidence = false;
        showTemple = false;

        locale = new Locale("cs");
    }

    public FamilyData getFamilyData() {
        return familyData;
    }

    public void setFamilyData(FamilyData familyData) {
        this.familyData = familyData;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
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

    public int getAdultVerticalShift() {
        return adultVerticalShift;
    }

    public void setAdultVerticalShift(int adultVerticalShift) {
        this.adultVerticalShift = adultVerticalShift;
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
