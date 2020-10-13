package org.ambrogenea.familyview.service.impl.paging;

import static org.ambrogenea.familyview.constant.Spaces.*;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.Paging;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class HorizontalPaging implements Paging {

    public final Configuration config;

    public HorizontalPaging(Configuration configuration) {
        config = configuration;
    }

    @Override
    public int calculateAllAncestorsX(AncestorPerson person) {
        return (int) ((config.getCoupleWidth() + SIBLINGS_GAP)
                * (person.getFather().getLastParentsCount() - person.getFather().getInnerParentsCount()
                + (person.getFather().getInnerParentsCount() + person.getMother().getInnerParentsCount()) / 2));
    }

    @Override
    public int calculateAllAncestorsWidth(AncestorPerson person) {
        return (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (person.getLastParentsCount()));
    }

    @Override
    public int calculateParentLineageWidth(AncestorPerson person) {
        int pageWidth = calculateFatherLineageWidth(person);
        pageWidth = pageWidth + config.getWideMarriageLabel() + config.getAdultImageWidth();

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + addFathersSiblingDimension(person);

            if (person.getMother().getMaxOlderSiblings() > 0) {
                //mother parent lineage width
                pageWidth = pageWidth + config.getParentImageSpace() * (Math.min(person.getMother().getAncestorGenerations(), config.getGenerationCount()) - 1) + config.getCoupleWidth() - config.getParentImageSpace() + SIBLINGS_GAP;
            }
            pageWidth = pageWidth + addMotherSiblingsWidth(person.getMother());
        }

        return pageWidth;
    }

    @Override
    public int calculateFatherLineageX(AncestorPerson person) {
        int ancestorGeneration;
        if (person.getFather().getAncestorGenerations() > 0) {
            ancestorGeneration = person.getFather().getAncestorGenerations() + 1;
        } else {
            ancestorGeneration = person.getMother().getAncestorGenerations() + 1;
        }
        return config.getParentImageSpace() * Math.min(ancestorGeneration, config.getGenerationCount()) + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
    }

    @Override
    public int calculateFatherLineageWidth(AncestorPerson person) {
        int ancestorGeneration;
        if (person.getFather().getAncestorGenerations() > 0) {
            ancestorGeneration = person.getFather().getAncestorGenerations() + 1;
        } else {
            ancestorGeneration = person.getMother().getAncestorGenerations() + 1;
        }

        int pageWidth = config.getParentImageSpace() * Math.min(ancestorGeneration, config.getGenerationCount()) + config.getCoupleWidth() - config.getParentImageSpace() + 2 * SIBLINGS_GAP;

        if (config.isShowSpouses()) {
            pageWidth = pageWidth + config.getParentImageSpace();
        }

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + addFathersSiblingDimension(person);
        }

        if (config.isShowResidence()) {
            pageWidth = pageWidth + RESIDENCE_SIZE;
        }

        return pageWidth;
    }

    @Override
    public int calculateMotherLineageX(AncestorPerson person) {
        int positionX = calculateFatherLineageX(person);
        positionX = positionX - config.getAdultImageWidth() - config.getMarriageLabelWidth();

        if (config.isShowSiblings()) {

            if (person.getMother().getOlderSiblings().size() > 0) {
                positionX = positionX + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * Math.max(person.getMother().getMaxOlderSiblings(), person.getMaxOlderSiblings()) - config.getParentImageSpace();
                if (config.isShowSiblingSpouses()) {
                    positionX = positionX + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        return positionX;
    }

    @Override
    public int calculateMotherLineageWidth(AncestorPerson person) {
        int pageWidth = calculateFatherLineageWidth(person);
        pageWidth = pageWidth - config.getAdultImageWidth() - config.getMarriageLabelWidth() + config.getParentImageSpace();

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + addMotherSiblingsWidth(person.getMother());
        }

        if (config.isShowResidence()) {
            pageWidth = pageWidth + RESIDENCE_SIZE;
        }

        return pageWidth;
    }

    @Override
    public int addFathersSiblingDimension(AncestorPerson person) {
        int siblingWidth = 0;
        if (person.getMaxOlderSiblings() > 0) {
            siblingWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() - config.getParentImageSpace() + SIBLINGS_GAP;

            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }

        if (person.getMaxYoungerSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxYoungerSiblings() + SIBLINGS_GAP;
            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + person.getMaxYoungerSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }
        return siblingWidth;
    }

    @Override
    public int addMotherSiblingsWidth(AncestorPerson mother) {
        int siblingWidth = 0;
        if (mother.getMaxOlderSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxOlderSiblings() - config.getParentImageSpace() + SIBLINGS_GAP;

            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + mother.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }

        if (mother.getMaxYoungerSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxYoungerSiblings() + SIBLINGS_GAP;
            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + mother.getMaxYoungerSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }
        return siblingWidth;
    }

    @Override
    public int calculateLineageY(AncestorPerson person, int pageHeight) {
        int positionY = pageHeight - VERTICAL_GAP / 2 - config.getAdultImageHeight() / 2;
        if (config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            positionY = positionY - (VERTICAL_GAP + config.getSiblingImageHeight());
        }
        return positionY;
    }

    @Override
    public int calculateLineageHeight(AncestorPerson person) {
        int pageHeight = (config.getAdultImageHeight() + VERTICAL_GAP) * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) + 1);

        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            pageHeight = pageHeight + config.getAdultImageHeight() + VERTICAL_GAP;
        }

        return pageHeight;
    }

}
