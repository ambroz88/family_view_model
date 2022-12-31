package cz.ambrogenea.familyvision.model.request;

import cz.ambrogenea.familyvision.enums.Diagram;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PanelConfigurationRequest {
    private String name;
    private boolean active;
    private int adultImageWidth;
    private int adultImageHeight;
    private int siblingImageWidth;
    private int siblingImageHeight;
    private int adultTopOffset;
    private int adultBottomOffset;
    private int siblingTopOffset;
    private int siblingBottomOffset;
    private int adultFontSize;
    private int siblingFontSize;
    private Diagram diagram;
    private boolean showAge;
    private boolean showPlaces;
    private boolean shortenPlaces;
    private boolean showOccupation;
    private boolean showTemple;
}
