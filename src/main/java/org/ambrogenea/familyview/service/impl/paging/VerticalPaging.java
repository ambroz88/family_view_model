package org.ambrogenea.familyview.service.impl.paging;

import static org.ambrogenea.familyview.constant.Spaces.*;

import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.Paging;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class VerticalPaging implements Paging {

    public final Configuration config;

    public VerticalPaging(Configuration configuration) {
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
        calculateFatherLineageWidth(person);

        int pageWidth = config.getAdultImageWidth() + config.getCoupleWidthVertical() + HORIZONTAL_GAP;

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + calculateFatherSiblingsWidth(person);

            if (person.getMother().getMaxOlderSiblings() > 0) {
                //mother parent lineage width
                pageWidth = pageWidth + config.getParentImageSpace() * (Math.min(person.getMother().getAncestorGenerations(), config.getGenerationCount()) - 1) + config.getCoupleWidth() - config.getParentImageSpace() + SIBLINGS_GAP;
            }
            pageWidth = pageWidth + calculateMotherSiblingsWidth(person.getMother());
        }

        return pageWidth;
    }

    @Override
    public int calculateFatherLineageX(AncestorPerson person) {
        int positionX = config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            int childrenWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0);
            if (positionX < childrenWidth / 2) {
                positionX = childrenWidth / 2;
            }
        }

        if (config.isShowSiblings()) {
            if (person.getMaxOlderSiblings() > 0) {
                int olderSiblingsCount;
                if (config.isShowParentLineage()) {
                    olderSiblingsCount = Math.max(person.getMaxOlderSiblings(), person.getFather().getAllSiblingsCount());
                } else {
                    olderSiblingsCount = person.getMaxOlderSiblings();
                }

                positionX = positionX + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * olderSiblingsCount + HORIZONTAL_GAP;
                if (config.isShowSiblingSpouses()) {
                    positionX = positionX + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        return positionX;
    }

    @Override
    public int calculateFatherLineageWidth(AncestorPerson person) {
        int pageWidth = config.getCoupleWidthVertical() + 2 * SIBLINGS_GAP;

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + calculateFatherSiblingsWidth(person);
        }

        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            int childrenWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0);
            if (childrenWidth > pageWidth) {
                pageWidth = childrenWidth;
            }
        }

        if (config.isShowResidence()) {
            pageWidth = pageWidth + RESIDENCE_SIZE;
        }

        return pageWidth;
    }

    @Override
    public int calculateMotherLineageX(AncestorPerson person) {
        int positionX = config.getAdultImageWidth() / 2 + SIBLINGS_GAP;

        if (person.getMother().getMaxOlderSiblings() > 0) {
            positionX = positionX + SIBLINGS_GAP
                    + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * Math.max(person.getMother().getMaxOlderSiblings(), person.getMaxOlderSiblings());
            if (config.isShowSiblingSpouses()) {
                positionX = positionX + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }

        return positionX;
    }

    @Override
    public int calculateMotherLineageWidth(AncestorPerson person) {
        int pageWidth = config.getCoupleWidthVertical() + 2 * SIBLINGS_GAP;

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + calculateMotherSiblingsWidth(person.getMother());
        }

        if (config.isShowResidence()) {
            pageWidth = pageWidth + RESIDENCE_SIZE;
        }

        return pageWidth;
    }

    @Override
    public int calculateFatherSiblingsWidth(AncestorPerson person) {
        int siblingWidth = 0;
        if (person.getMaxOlderSiblings() > 0) {
            siblingWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() + SIBLINGS_GAP;
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
    public int calculateMotherSiblingsWidth(AncestorPerson mother) {
        int siblingWidth = 0;
        if (mother.getMaxOlderSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxOlderSiblings() + SIBLINGS_GAP;

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
        int positionY = pageHeight - VERTICAL_GAP / 2 - (int) (1.5 * config.getAdultImageHeightAlternative()) - config.getMarriageLabelHeight();
        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            positionY = positionY - (VERTICAL_GAP + config.getSiblingImageHeight());
        }
        return positionY;
    }

    @Override
    public int calculateLineageHeight(AncestorPerson person) {
        int pageHeight;
        if (config.getAdultDiagram().equals(Diagrams.PERGAMEN)) {
            pageHeight = config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                    + (2 * config.getAdultImageHeight() - (int) (config.getAdultImageHeight() * 0.2) + config.getMarriageLabelHeight() + VERTICAL_GAP)
                    * Math.min(person.getAncestorGenerations(), config.getGenerationCount());
        } else {
            pageHeight = config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                    + (2 * config.getAdultImageHeight() + config.getMarriageLabelHeight() + VERTICAL_GAP)
                    * Math.min(person.getAncestorGenerations(), config.getGenerationCount());
        }

        if (config.isShowSpouses() && person.getSpouse() != null) {
            pageHeight = pageHeight + config.getAdultImageHeightAlternative() + config.getMarriageLabelHeight();
            if (config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
                pageHeight = pageHeight + config.getSiblingImageHeight() + VERTICAL_GAP + SIBLINGS_GAP;
            }
        }

        return pageHeight;
    }

}
