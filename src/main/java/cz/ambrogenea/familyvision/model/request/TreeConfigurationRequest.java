package cz.ambrogenea.familyvision.model.request;

import cz.ambrogenea.familyvision.enums.LabelShape;
import cz.ambrogenea.familyvision.enums.CoupleType;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeConfigurationRequest {
    private String name;
    private boolean active;
    private int generationCount;
    private LabelShape labelShape;
    private CoupleType coupleType;
    private boolean showSiblings;
    private boolean showSpouses;
    private boolean showSiblingSpouses;
    private boolean showChildren;
    private boolean showSiblingsFamily;
    private boolean showSpousesFamily;
    private boolean showMarriage;
    private boolean showResidence;
    private boolean showHeraldry;
    private boolean resetMode;
}
