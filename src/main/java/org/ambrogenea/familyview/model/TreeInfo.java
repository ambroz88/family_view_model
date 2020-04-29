package org.ambrogenea.familyview.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeInfo {

    private int directParentNumber;
    private int parentCouplesNumber;
    private int maxLeftSiblings;
    private int maxFathers;
    private int maxRighttSiblings;
    private int maxMothers;

    public TreeInfo() {
    }

    public void compareTree(TreeInfo tree) {

    }

    public int getDirectParentNumber() {
        return directParentNumber;
    }

    public void setDirectParentNumber(int directParentNumber) {
        this.directParentNumber = directParentNumber;
    }

    public int getParentCouplesNumber() {
        return parentCouplesNumber;
    }

    public void setParentCouplesNumber(int parentCouplesNumber) {
        this.parentCouplesNumber = parentCouplesNumber;
    }

    public int getMaxLeftSiblings() {
        return maxLeftSiblings;
    }

    public void setMaxLeftSiblings(int maxLeftSiblings) {
        this.maxLeftSiblings = maxLeftSiblings;
    }

    public int getMaxFathers() {
        return maxFathers;
    }

    public void setMaxFathers(int maxFathers) {
        this.maxFathers = maxFathers;
    }

    public int getMaxRighttSiblings() {
        return maxRighttSiblings;
    }

    public void setMaxRighttSiblings(int maxRighttSiblings) {
        this.maxRighttSiblings = maxRighttSiblings;
    }

    public int getMaxMothers() {
        return maxMothers;
    }

    public void setMaxMothers(int maxMothers) {
        this.maxMothers = maxMothers;
    }

}
