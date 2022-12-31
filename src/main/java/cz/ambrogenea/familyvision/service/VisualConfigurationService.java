package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.enums.Background;
import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;

import java.util.Locale;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface VisualConfigurationService {
    //------------- COMMON config methods ----------------
    int getHeraldryVerticalDistance();
    int nextSiblingX();
    int nextChildrenX();

    int getAdultImageWidth();
    void setAdultImageWidth(int adultImageWidth);
    int getAdultImageHeight();
    int getAdultImageHeightAlternative();
    void setAdultImageHeight(int adultImageHeight);
    int getAdultFontSize();
    void setAdultFontSize(int adultFontSize);
    int getSiblingImageWidth();
    void setSiblingImageWidth(int siblingImageWidth);
    int getSiblingImageHeight();
    int getSiblingImageHeightAlternative();
    void setSiblingImageHeight(int siblingImageHeight);
    int getSiblingFontSize();
    void setSiblingFontSize(int siblingFontSize);

    Diagram getDiagram();
    void setDiagram(Diagram diagram);
    LabelShape getMarriageLabelShape();
    void setMarriageLabelShape(LabelShape marriageLabelShape);
    Background getBackground();
    void setBackground(Background background);
    int getVerticalShift();
    void setVerticalShift(int verticalShift);
    boolean isShowAge();
    void setShowAge(boolean showAge);
    boolean isShowOccupation();
    void setShowOccupation(boolean showOccupation);
    boolean isShowTitle();
    void setShowTitle(boolean showTitle);
    boolean isShowPlaces();
    void setShowPlaces(boolean showPlaces);
    boolean isShortenPlaces();
    void setShortenPlaces(boolean shortenPlaces);
    boolean isShowOrdinances();
    void setShowOrdinances(boolean showOrdinances);
    Locale getLocale();
    void setLocale(Locale locale);
    boolean isResetMode();
    void setResetMode(boolean resetMode);
}
