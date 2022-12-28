package cz.ambrogenea.familyvision.configuration;

import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonConfiguration {

    private Locale locale;

    private int adultImageWidth;
    private int adultImageHeight;
    private int adultFontSize;

    private int siblingImageWidth;
    private int siblingImageHeight;
    private int siblingFontSize;

    private int verticalShift;
    private Diagram diagram;
    private LabelShape labelShape;
    private String manImagePath;
    private String womanImagePath;

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
        siblingImageWidth = 170;
        siblingImageHeight = 120;
        adultFontSize = 12;
        siblingFontSize = 11;
        verticalShift = 0;

        diagram = Diagram.SCROLL;
        labelShape = LabelShape.OVAL;
        manImagePath = "diagrams/" + diagram + "_man.png";
        womanImagePath = "diagrams/" + diagram + "_woman.png";

        showAge = true;
        showPlaces = true;
        shortenPlaces = false;
        showOccupation = true;
        showMarriage = true;
        showResidence = false;
        showTemple = false;

        locale = new Locale("cs");
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

    public int getVerticalShift() {
        return verticalShift;
    }

    public void setVerticalShift(int verticalShift) {
        this.verticalShift = verticalShift;
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

    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public LabelShape getLabelShape() {
        return labelShape;
    }

    public void setLabelShape(LabelShape labelShape) {
        this.labelShape = labelShape;
    }

    public String getManImagePath() {
        return manImagePath;
    }

    public void setManImagePath(String manImagePath) {
        this.manImagePath = manImagePath;
    }

    public String getWomanImagePath() {
        return womanImagePath;
    }

    public void setWomanImagePath(String womanImagePath) {
        this.womanImagePath = womanImagePath;
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
