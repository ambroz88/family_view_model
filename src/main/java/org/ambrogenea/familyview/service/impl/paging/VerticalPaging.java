package org.ambrogenea.familyview.service.impl.paging;

import static org.ambrogenea.familyview.constant.Spaces.*;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.Paging;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class VerticalPaging implements Paging {

    public final ConfigurationService config;

    public VerticalPaging(ConfigurationService configuration) {
        config = configuration;
    }

    @Override
    public int calculateAllAncestorsX(AncestorPerson person) {
        int minimalX = config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            int childrenWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0);
            int childrenX = childrenWidth / 2 - config.getHalfSpouseLabelSpace();
            minimalX = Math.max(minimalX, childrenX + SIBLINGS_GAP);
        }

        if (person.getFather() != null) {
            minimalX = Math.max(minimalX, config.getCoupleWidth() / 2 + SIBLINGS_GAP);
            int fathersX = (int) ((config.getCoupleWidth() + config.getGapBetweenCouples()) * person.getFather().getLastParentsCount()
                    + config.getAllAncestorsCoupleIncrease() / 2.0);
            return Math.max(fathersX, minimalX);
        } else {
            return minimalX;
        }
    }

    @Override
    public int calculateAllAncestorsWidth(AncestorPerson person) {
        return (int) ((config.getCoupleWidth() + config.getGapBetweenCouples())
                * person.getLastParentsCount() + config.getAllAncestorsCoupleIncrease());
    }

    @Override
    public int calculateParentLineageWidth(AncestorPerson person) {
        int pageWidth = config.getWideMarriageLabel() / 2 + 2 * (config.getCoupleWidth() + HORIZONTAL_GAP);

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
        int parentWidth = config.getParentGenerationWidth(person);
        int childrenShift = config.getChildrenShift(person);

        int siblingsShift = 0;
        if (config.isShowSiblings()) {
            if (person.getMaxOlderSiblings() > 0) {
                int olderSiblingsCount;
                if (config.isShowParentLineage()) {
                    olderSiblingsCount = Math.max(person.getMaxOlderSiblings(), person.getFather().getAllSiblingsCount());
                } else {
                    olderSiblingsCount = person.getMaxOlderSiblings();
                }

                siblingsShift = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * olderSiblingsCount;

                if (config.isShowSiblingSpouses()) {
                    siblingsShift = siblingsShift + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        return positionX + Math.max(parentWidth + siblingsShift, childrenShift);
    }

    @Override
    public int calculateFatherLineageWidth(AncestorPerson person) {
        int pageWidth = config.getCoupleWidth() + 2 * SIBLINGS_GAP;
        int parentWidth = config.getParentGenerationWidth(person);
        int childrenWidth = config.getChildrenShift(person) - config.getCoupleWidth() / 2;

        int siblingsWidth = 0;
        if (config.isShowSiblings()) {
            siblingsWidth = calculateFatherSiblingsWidth(person);
        }

        int spouseWidth = 0;
        if (config.isShowSpouses() && person.getSpouseCouple() != null) {

            int extraSpouseCount = person.getSpouseCouples().size() - 1;
            if (extraSpouseCount > 0) {
                spouseWidth = config.getSpouseLabelSpace() * extraSpouseCount;
            }
        }

        pageWidth = pageWidth + Math.max(siblingsWidth,
                Math.max(parentWidth, childrenWidth / 2) + Math.max(spouseWidth, childrenWidth / 2)
        );

        if (config.isShowResidence()) {
            pageWidth = pageWidth + RESIDENCE_SIZE;
        }

        return pageWidth;
    }

    @Override
    public int calculateMotherLineageX(AncestorPerson person) {
        int positionX = config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        int siblingsShift = 0;

        int childrenShift = config.getChildrenShift(person) / 2 - positionX;

        if (config.isShowSiblings()) {
            if (person.getMaxOlderSiblings() > 0) {
                siblingsShift = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() + HORIZONTAL_GAP;
                if (config.isShowSiblingSpouses()) {
                    siblingsShift = siblingsShift + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        return positionX + Math.max(siblingsShift, childrenShift);
    }

    @Override
    public int calculateMotherLineageWidth(AncestorPerson person) {
        int pageWidth = config.getCoupleWidth() + 2 * SIBLINGS_GAP;

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
        int positionY = pageHeight - (VERTICAL_GAP + config.getAdultImageHeight()) / 2;
        if (config.isShowSpouses() && person.getSpouseCouple() != null
                && config.isShowChildren() && !person.getSpouseCouple().getChildren().isEmpty()) {
            positionY = positionY - (VERTICAL_GAP + config.getSiblingImageHeight());
        }
        return positionY;
    }

    @Override
    public int calculateLineageHeight(AncestorPerson person) {
        int pageHeight;
        if (config.getAdultDiagram().equals(Diagrams.SCROLL)) {
            pageHeight = config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                    + (2 * config.getAdultImageHeight() + VERTICAL_GAP)
                    * Math.min(person.getAncestorGenerations(), config.getGenerationCount() - 1);
        } else {
            pageHeight = config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                    + (2 * config.getAdultImageHeight() + config.getMarriageLabelHeight() + VERTICAL_GAP)
                    * Math.min(person.getAncestorGenerations(), config.getGenerationCount() - 1);
        }

        if (config.isShowSpouses() && person.getSpouse() != null) {
            if (config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
                pageHeight = pageHeight + config.getSiblingImageHeight() + VERTICAL_GAP + SIBLINGS_GAP;
            }
        }

        return pageHeight + TITLE_HEIGHT;
    }

    @Override
    public int calculateAllDescendentHeight(AncestorPerson person) {
        int pageHeight = (config.getAdultImageHeight() + VERTICAL_GAP);

        if (person.getSpouseCouple() != null) {
            if (config.getAdultDiagram().equals(Diagrams.SCROLL)) {
                pageHeight += config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                        + (2 * config.getAdultImageHeight() + VERTICAL_GAP)
                        * person.getSpouseCouple().getDescendentTreeInfo().getMaxGenerationsCount();
            } else {
                pageHeight += config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                        + (2 * config.getAdultImageHeight() + config.getMarriageLabelHeight() + VERTICAL_GAP)
                        * person.getSpouseCouple().getDescendentTreeInfo().getMaxGenerationsCount();
            }
        }

        return pageHeight;
    }

}
