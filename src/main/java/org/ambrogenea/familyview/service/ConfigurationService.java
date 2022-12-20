package org.ambrogenea.familyview.service;

import java.beans.PropertyChangeListener;
import java.util.Locale;

import org.ambrogenea.familyview.configuration.Configuration;
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
    //------------- COMMON config methods ----------------
    FamilyData getFamilyData();
    void setFamilyData(FamilyData ancestorModel);
    Locale getLocale();
    void setLocale(Locale locale);
    int getHeraldryVerticalDistance();
    int nextSiblingX();
    int nextChildrenX();

    //------------- CONFIGURATION SETTERS AND GETTERS ----------------
    int getAdultImageWidth();
    void setAdultImageWidth(int adultImageWidth);
    int getAdultImageHeight();
    int getAdultImageHeightAlternative();
    void setAdultImageHeight(int adultImageHeight);
    int getSiblingImageWidth();
    void setSiblingImageWidth(int siblingImageWidth);
    int getSiblingImageHeight();
    int getSiblingImageHeightAlternative();
    void setSiblingImageHeight(int siblingImageHeight);
    int getAdultFontSize();
    void setAdultFontSize(int adultFontSize);
    int getSiblingFontSize();
    void setSiblingFontSize(int siblingFontSize);
    Diagrams getAdultDiagram();
    void setAdultDiagram(Diagrams adultDiagram);
    LabelShape getLabelShape();
    void setLabelShape(LabelShape labelShape);
    String getAdultManImagePath();
    String getAdultWomanImagePath();
    int getAdultVerticalShift();
    void setAdultVerticalShift(int adultVerticalShift);

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

    public Configuration getConfiguration();
}
