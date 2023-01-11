package cz.ambrogenea.familyvision.model.command;

import cz.ambrogenea.familyvision.enums.CoupleType;
import cz.ambrogenea.familyvision.enums.LineageType;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record TreeShapeConfigurationCommand(
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
