package cz.ambrogenea.familyvision.model.response;

import cz.ambrogenea.familyvision.enums.Background;
import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record VisualConfigurationResponse(
        int adultImageWidth,
        int adultImageHeight,
        int adultFontSize,
        int siblingImageWidth,
        int siblingImageHeight,
        int siblingFontSize,
        Diagram diagram,
        LabelShape marriageLabelShape,
        Background background,
        int verticalShift,
        boolean showAge,
        boolean showOccupation,
        boolean showTitle,
        boolean showPlaces,
        boolean shortenPlaces,
        boolean showOrdinances,
        boolean showChildrenCount,
        Locale locale,
        boolean resetMode
) {
}
