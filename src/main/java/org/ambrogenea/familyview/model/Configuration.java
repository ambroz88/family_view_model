package org.ambrogenea.familyview.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Configuration {

    private int adultImageWidth;
    private int adultImageHeight;
    private int adultVerticalOffset;
    private int siblingImageWidth;
    private int siblingImageHeight;
    private int siblingVerticalOffset;
    private int fontSize;

    private String adultManImagePath;
    private String adultWomanImagePath;
    private String siblingManImagePath;
    private String siblingWomanImagePath;
    private String boyImagePath;
    private String girlImagePath;

    public Configuration() {
        adultImageWidth = 150;
        adultImageHeight = 140;
        adultVerticalOffset = 30;
        siblingImageWidth = 150;
        siblingImageHeight = 120;
        siblingVerticalOffset = 10;
        fontSize = 13;
        adultManImagePath = this.getClass().getResource("/diagrams/man_diagram.png").getPath();
        adultWomanImagePath = this.getClass().getResource("/diagrams/woman_diagram.png").getPath();
        siblingManImagePath = "";
        siblingWomanImagePath = "";
        boyImagePath = "";
        girlImagePath = "";
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

    public int getAdultVerticalOffset() {
        return adultVerticalOffset;
    }

    public void setAdultVerticalOffset(int adultVerticalOffset) {
        this.adultVerticalOffset = adultVerticalOffset;
    }

    public int getSiblingVerticalOffset() {
        return siblingVerticalOffset;
    }

    public void setSiblingVerticalOffset(int siblingVerticalOffset) {
        this.siblingVerticalOffset = siblingVerticalOffset;
    }

}
