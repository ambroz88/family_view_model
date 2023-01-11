package cz.ambrogenea.familyvision.model.request;

import cz.ambrogenea.familyvision.enums.Background;
import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record VisualConfigurationRequest(
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
        Locale locale,
        boolean resetMode
) {

    @Override
    public String toString() {
        return "VisualConfigurationRequest{" +
                "adultImageWidth=" + adultImageWidth +
                ", adultImageHeight=" + adultImageHeight +
                ", adultFontSize=" + adultFontSize +
                ", siblingImageWidth=" + siblingImageWidth +
                ", siblingImageHeight=" + siblingImageHeight +
                ", siblingFontSize=" + siblingFontSize +
                ", diagram=" + diagram +
                ", marriageLabelShape=" + marriageLabelShape +
                ", background=" + background +
                ", verticalShift=" + verticalShift +
                ", showAge=" + showAge +
                ", showOccupation=" + showOccupation +
                ", showTitle=" + showTitle +
                ", showPlaces=" + showPlaces +
                ", shortenPlaces=" + shortenPlaces +
                ", showOrdinances=" + showOrdinances +
                ", locale=" + locale +
                ", resetMode=" + resetMode +
                '}';
    }
}
