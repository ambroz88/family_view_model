package cz.ambrogenea.familyvision.service;

import java.beans.PropertyChangeListener;
import java.util.Locale;

import cz.ambrogenea.familyvision.configuration.Configuration;
import cz.ambrogenea.familyvision.domain.FamilyData;
import cz.ambrogenea.familyvision.enums.Diagrams;
import cz.ambrogenea.familyvision.enums.LabelShape;
import cz.ambrogenea.familyvision.enums.PropertyName;

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
    Diagrams getDiagram();
    void setDiagram(Diagrams adultDiagram);
    LabelShape getLabelShape();
    void setLabelShape(LabelShape labelShape);
    String getManImagePath();
    String getWomanImagePath();
    int getVerticalShift();
    void setVerticalShift(int adultVerticalShift);

    boolean isShowSiblings();
    void setShowSiblings(boolean showSiblings);
    boolean isShowSpouses();
    void setShowSpouses(boolean showSpouses);
    boolean isShowSiblingSpouses();
    void setShowSiblingSpouses(boolean showSiblingSpouses);
    int getGenerationCount();
    void setGenerationCount(int generationCount);
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

    Configuration getConfiguration();
}
