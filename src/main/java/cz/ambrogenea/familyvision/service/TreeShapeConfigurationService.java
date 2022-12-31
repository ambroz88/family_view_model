package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.enums.CoupleType;
import cz.ambrogenea.familyvision.enums.LineageType;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface TreeShapeConfigurationService {
    LineageType getLineageType();
    void setLineageType(LineageType lineageType);
    CoupleType getCoupleType();
    void setCoupleType(CoupleType coupleType);
    int getAncestorGenerations();
    void setAncestorGenerations(int ancestorGenerations);
    int getDescendentGenerations();
    void setDescendentGenerations(int descendentGenerations);
    boolean isShowSiblings();
    void setShowSiblings(boolean showSiblings);
    boolean isShowSiblingSpouses();
    void setShowSiblingSpouses(boolean showSiblingSpouses);
    boolean isShowSpouses();
    void setShowSpouses(boolean showSpouses);
    boolean isShowHeraldry();
    void setShowHeraldry(boolean showHeraldry);
    boolean isShowResidence();
    void setShowResidence(boolean showResidence);
}
