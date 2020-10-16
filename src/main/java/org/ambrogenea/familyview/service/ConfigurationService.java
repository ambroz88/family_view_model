package org.ambrogenea.familyview.service;

import java.beans.PropertyChangeListener;

import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.PropertyName;
import org.ambrogenea.familyview.model.AncestorModel;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface ConfigurationService {

    public static final int MIN_MARRIAGE_LABEL_HEIGHT = 30;
    public static final int MIN_MARRIAGE_LABEL_WIDTH = 100;

    public AncestorModel getAncestorModel();

    public void setAncestorModel(AncestorModel ancestorModel);

    public int getWideMarriageLabel();

    public int getMarriageLabelWidth();

    public int getMarriageLabelHeight();

    public int getAdultImageWidth();

    public int getParentImageSpace();

    public int getSpouseLabelSpace();

    public int getHalfSpouseLabelSpace();

    public int getCoupleWidth();

    public int getSpouseDistance();

    public int getCoupleWidthVertical();

    public int getCoupleVerticalDifference();

    public void setAdultImageWidth(int adultImageWidth);

    public int getAdultImageHeight();

    public int getAdultImageHeightAlternative();

    public void setAdultImageHeight(int adultImageHeight);

    public int getSiblingImageWidth();

    public void setSiblingImageWidth(int siblingImageWidth);

    public int getSiblingImageHeight();

    public void setSiblingImageHeight(int siblingImageHeight);

    public int getAdultFontSize();

    public void setAdultFontSize(int adultFontSize);

    public int getSiblingFontSize();

    public void setSiblingFontSize(int siblingFontSize);

    public Diagrams getAdultDiagram();

    public void setAdultDiagram(Diagrams adultDiagram);

    public Diagrams getSiblingDiagram();

    public void setSiblingDiagram(Diagrams siblingDiagram);

    public LabelShape getLabelShape();

    public void setLabelShape(LabelShape labelShape);

    public String getAdultManImagePath();

    public String getAdultWomanImagePath();

    public String getSiblingManImagePath();

    public String getSiblingWomanImagePath();

    public int getAdultBottomOffset();

    public int getAdultTopOffset();

    public void setAdultBottomOffset(int adultVerticalOffset);

    public void setAdultTopOffset(int adultVerticalOffset);

    public int getSiblingBottomOffset();

    public int getSiblingTopOffset();

    public void setSiblingBottomOffset(int siblingVerticalOffset);

    public void setSiblingTopOffset(int siblingVerticalOffset);

    public boolean isShowSiblings();

    public void setShowSiblings(boolean showSiblings);

    public boolean isShowSpouses();

    public void setShowSpouses(boolean showSpouses);

    public boolean isShowSiblingSpouses();

    public void setShowSiblingSpouses(boolean showSiblingSpouses);

    public int getGenerationCount();

    public void setGenerationCount(int generationCount);

    public boolean isShowParents();

    public void setShowParents(boolean showParents);

    public boolean isShowChildren();

    public void setShowChildren(boolean showChildren);

    public boolean isShowParentLineage();

    public void setShowParentLineage(boolean showParentLineage);

    public boolean isShowAge();

    public void setShowAge(boolean showAge);

    public boolean isShowPlaces();

    public void setShowPlaces(boolean showPlaces);

    public boolean isShortenPlaces();

    public void setShortenPlaces(boolean shortenPlaces);

    public boolean isShowTemple();

    public void setShowTemple(boolean showTemple);

    public boolean isShowMarriage();

    public void setShowMarriage(boolean showMarriage);

    public boolean isShowHeraldry();

    public void setShowHeraldry(boolean showHeraldry);

    public boolean isShowOccupation();

    public void setShowOccupation(boolean showOccupation);

    public boolean isShowResidence();

    public void setShowResidence(boolean showResidence);

    public boolean isShowCouplesVertical();

    public void setShowCouplesVertical(boolean showCouplesVertical);

    public boolean isResetMode();

    public void setResetMode(boolean resetMode);

    public void firePropertyChange(PropertyName prop, Object oldValue, Object newValue);

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

}
