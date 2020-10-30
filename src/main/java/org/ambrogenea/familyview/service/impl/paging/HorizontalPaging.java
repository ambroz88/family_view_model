package org.ambrogenea.familyview.service.impl.paging;

import static org.ambrogenea.familyview.constant.Spaces.*;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.Paging;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class HorizontalPaging implements Paging {

    public final ConfigurationService config;

    public HorizontalPaging(ConfigurationService configuration) {
        config = configuration;
    }

    @Override
    public int calculateAllAncestorsX(AncestorPerson person) {
        return (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * person.getFather().getLastParentsCount());
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
        int ancestorGeneration;
        if (person.getFather().getAncestorGenerations() > 0) {
            ancestorGeneration = person.getFather().getAncestorGenerations() + 1;
        } else {
            ancestorGeneration = person.getMother().getAncestorGenerations() + 1;
        }

        int childrenShift = 0;
        int siblingsShift = 0;
        int positionX = config.getParentImageSpace() * Math.min(ancestorGeneration, config.getGenerationCount()) + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;

        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            int childrenWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0);
            childrenShift = childrenWidth / 2 - positionX;
        }

        if (config.isShowSiblings()) {
            if (person.getMaxOlderSiblings() > 0) {
                siblingsShift = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() - config.getParentImageSpace() + HORIZONTAL_GAP;

                if (config.isShowSiblingSpouses()) {
                    siblingsShift = siblingsShift + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        return positionX + Math.max(siblingsShift, childrenShift);
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

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + calculateFatherSiblingsWidth(person);
        }

        if (config.isShowSpouses() && person.getSpouseCouple() != null) {
            pageWidth = pageWidth + config.getParentImageSpace();

            int extraSpouseCount = person.getSpouseCouples().size() - 1;
            if (extraSpouseCount > 0) {
                pageWidth = pageWidth + config.getSpouseLabelSpace() * extraSpouseCount;
            }

            if (config.isShowChildren() && !person.getSpouseCouple().getChildren().isEmpty()) {
                int childrenWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0);
                if (childrenWidth > pageWidth) {
                    pageWidth = childrenWidth;
                }
            }
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
    public int calculateMotherSiblingsWidth(AncestorPerson mother) {
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
        if (config.isShowSpouses() && person.getSpouseCouple() != null &&
            config.isShowChildren() && !person.getSpouseCouple().getChildren().isEmpty()) {
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
