package org.ambrogenea.familyview.configuration;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class Configuration extends PersonConfiguration {

    private boolean showParentLineage;
    private boolean showCouplesVertical;
    private boolean showSiblings;
    private boolean showSpouses;
    private boolean showSiblingSpouses;
    private boolean showParents;
    private boolean showChildren;
    private boolean showHeraldry;
    private boolean resetMode;
    private int generationCount;

    public Configuration() {
        showParentLineage = false;
        showSiblings = true;
        showSpouses = true;
        showSiblingSpouses = false;
        showParents = true;
        showChildren = true;
        showCouplesVertical = false;
        showHeraldry = true;
        resetMode = false;
        generationCount = 10;
    }

    public boolean isShowParentLineage() {
        return showParentLineage;
    }

    public void setShowParentLineage(boolean showParentLineage) {
        this.showParentLineage = showParentLineage;
    }

    public boolean isShowCouplesVertical() {
        return showCouplesVertical;
    }

    public void setShowCouplesVertical(boolean showCouplesVertical) {
        this.showCouplesVertical = showCouplesVertical;
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

    public boolean isShowSiblingSpouses() {
        return showSiblingSpouses;
    }

    public void setShowSiblingSpouses(boolean showSiblingSpouses) {
        this.showSiblingSpouses = showSiblingSpouses;
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

    public boolean isShowHeraldry() {
        return showHeraldry;
    }

    public void setShowHeraldry(boolean showHeraldry) {
        this.showHeraldry = showHeraldry;
    }

    public boolean isResetMode() {
        return resetMode;
    }

    public void setResetMode(boolean resetMode) {
        this.resetMode = resetMode;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

}
