package cz.ambrogenea.familyvision.model.response;

import cz.ambrogenea.familyvision.enums.Diagram;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record PanelConfigurationResponse(
        long id,
        String name,
        boolean active,
        int adultImageWidth,
        int adultImageHeight,
        int siblingImageWidth,
        int siblingImageHeight,
        int adultTopOffset,
        int adultBottomOffset,
        int siblingTopOffset,
        int siblingBottomOffset,
        int adultFontSize,
        int siblingFontSize,
        Diagram diagram,
        boolean showAge,
        boolean showPlaces,
        boolean shortenPlaces,
        boolean showOccupation,
        boolean showTemple
) {
}
