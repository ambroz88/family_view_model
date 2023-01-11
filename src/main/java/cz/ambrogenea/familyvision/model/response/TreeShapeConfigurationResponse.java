package cz.ambrogenea.familyvision.model.response;

import cz.ambrogenea.familyvision.enums.CoupleType;
import cz.ambrogenea.familyvision.enums.LineageType;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record TreeShapeConfigurationResponse(
        LineageType lineageType,
        CoupleType coupleType,
        int ancestorGenerations,
        int descendentGenerations,
        boolean showSiblings,
        boolean showSiblingSpouses,
        boolean showSpouses,
        boolean showHeraldry,
        boolean showResidence
) {
}
