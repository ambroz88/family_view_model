package cz.ambrogenea.familyvision.model.command;

import cz.ambrogenea.familyvision.enums.LabelShape;
import cz.ambrogenea.familyvision.enums.CoupleType;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record TreeConfigurationCommand  (
    String name,
    boolean active,
    int generationCount,
    LabelShape labelShape,
    CoupleType coupleType,
    boolean showSiblings,
    boolean showSpouses,
    boolean showSiblingSpouses,
    boolean showChildren,
    boolean showSiblingsFamily,
    boolean showSpousesFamily,
    boolean showMarriage,
    boolean showResidence,
    boolean showHeraldry,
    boolean resetMode
){
}
