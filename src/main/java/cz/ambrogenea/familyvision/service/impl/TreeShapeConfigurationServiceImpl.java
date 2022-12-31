package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.enums.CoupleType;
import cz.ambrogenea.familyvision.enums.LineageType;
import cz.ambrogenea.familyvision.service.TreeShapeConfigurationService;

public class TreeShapeConfigurationServiceImpl implements TreeShapeConfigurationService {

    private final TreeShapeConfiguration configuration;

    public TreeShapeConfigurationServiceImpl(TreeShapeConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public LineageType getLineageType() {
        return configuration.getLineageType();
    }

    @Override
    public void setLineageType(LineageType lineageType) {
        configuration.setLineageType(lineageType);
    }

    @Override
    public CoupleType getCoupleType() {
        return configuration.getCoupleType();
    }

    @Override
    public void setCoupleType(CoupleType coupleType) {
        configuration.setCoupleType(coupleType);
    }

    @Override
    public int getAncestorGenerations() {
        return configuration.getAncestorGenerations();
    }

    @Override
    public void setAncestorGenerations(int ancestorGenerations) {
        configuration.setAncestorGenerations(ancestorGenerations);
    }

    @Override
    public int getDescendentGenerations() {
        return configuration.getDescendentGenerations();
    }

    @Override
    public void setDescendentGenerations(int descendentGenerations) {
        configuration.setDescendentGenerations(descendentGenerations);
    }

    @Override
    public boolean isShowSiblings() {
        return configuration.isShowSiblings();
    }

    @Override
    public void setShowSiblings(boolean showSiblings) {
        configuration.setShowSiblings(showSiblings);
    }

    @Override
    public boolean isShowSiblingSpouses() {
        return configuration.isShowSiblingSpouses();
    }

    @Override
    public void setShowSiblingSpouses(boolean showSiblingSpouses) {
        configuration.setShowSiblingSpouses(showSiblingSpouses);
    }

    @Override
    public boolean isShowSpouses() {
        return configuration.isShowSpouses();
    }

    @Override
    public void setShowSpouses(boolean showSpouses) {
        configuration.setShowSpouses(showSpouses);
    }

    @Override
    public boolean isShowHeraldry() {
        return configuration.isShowHeraldry();
    }

    @Override
    public void setShowHeraldry(boolean showHeraldry) {
        configuration.setShowHeraldry(showHeraldry);
    }

    @Override
    public boolean isShowResidence() {
        return configuration.isShowResidence();
    }

    @Override
    public void setShowResidence(boolean showResidence) {
        configuration.setShowResidence(showResidence);
    }
}
