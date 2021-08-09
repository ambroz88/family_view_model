package org.ambrogenea.familyview.service;

import java.beans.PropertyChangeListener;
import java.util.Locale;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.PropertyName;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface ConfigurationService {

    FamilyData getFamilyData();

    void setFamilyData(FamilyData ancestorModel);

    Locale getLocale();

    void setLocale(Locale locale);

    int getWideMarriageLabel();

    int getMarriageLabelWidth();

    int getMarriageLabelHeight();

    int getAdultImageWidth();

    int getParentImageSpace();

    int getSpouseLabelSpace();

    int getHalfSpouseLabelSpace();

    int getCoupleWidth();

    int getGapBetweenCouples();

    int getAllAncestorsCoupleIncrease();

    int getSpouseDistance();

    int getCoupleVerticalDifference();

    void setAdultImageWidth(int adultImageWidth);

    int getAdultImageHeight();

    int getAdultImageHeightAlternative();

    void setAdultImageHeight(int adultImageHeight);

    int getSiblingImageWidth();

    void setSiblingImageWidth(int siblingImageWidth);

    int getSiblingImageHeight();

    void setSiblingImageHeight(int siblingImageHeight);

    int getChildrenShift(AncestorPerson person);

    int getParentGenerationWidth(AncestorPerson person);

    int getAdultFontSize();

    void setAdultFontSize(int adultFontSize);

    int getSiblingFontSize();

    void setSiblingFontSize(int siblingFontSize);

    Diagrams getAdultDiagram();

    void setAdultDiagram(Diagrams adultDiagram);

    Diagrams getSiblingDiagram();

    void setSiblingDiagram(Diagrams siblingDiagram);

    LabelShape getLabelShape();

    void setLabelShape(LabelShape labelShape);

    String getAdultManImagePath();

    String getAdultWomanImagePath();

    String getSiblingManImagePath();

    String getSiblingWomanImagePath();

    int getAdultVerticalShift();

    void setAdultVerticalShift(int adultVerticalShift);

    int getSiblingVerticalShift();

    void setSiblingVerticalShift(int siblingVerticalShift);

    boolean isShowSiblings();

    void setShowSiblings(boolean showSiblings);

    boolean isShowSpouses();

    void setShowSpouses(boolean showSpouses);

    boolean isShowSiblingSpouses();

    void setShowSiblingSpouses(boolean showSiblingSpouses);

    int getGenerationCount();

    void setGenerationCount(int generationCount);

    boolean isShowParents();

    void setShowParents(boolean showParents);

    boolean isShowChildren();

    void setShowChildren(boolean showChildren);

    boolean isShowParentLineage();

    void setShowParentLineage(boolean showParentLineage);

    boolean isShowAge();

    void setShowAge(boolean showAge);

    boolean isShowPlaces();

    void setShowPlaces(boolean showPlaces);

    boolean isShortenPlaces();

    void setShortenPlaces(boolean shortenPlaces);

    boolean isShowTemple();

    void setShowTemple(boolean showTemple);

    boolean isShowMarriage();

    void setShowMarriage(boolean showMarriage);

    boolean isShowHeraldry();

    void setShowHeraldry(boolean showHeraldry);

    boolean isShowOccupation();

    void setShowOccupation(boolean showOccupation);

    boolean isShowResidence();

    void setShowResidence(boolean showResidence);

    boolean isShowCouplesVertical();

    void setShowCouplesVertical(boolean showCouplesVertical);

    boolean isResetMode();

    void setResetMode(boolean resetMode);

    void firePropertyChange(PropertyName prop, Object oldValue, Object newValue);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

}
