package org.ambrogenea.familyview.service.impl.paging;

import static org.ambrogenea.familyview.constant.Spaces.*;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.Paging;
import org.ambrogenea.familyview.service.impl.HorizontalConfigurationService;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class HorizontalPaging implements Paging {

    public final ConfigurationService config;
    public final ConfigurationExtensionService extensionConfig;

    public HorizontalPaging(ConfigurationService configuration) {
        config = configuration;
        extensionConfig = new HorizontalConfigurationService(config);
    }

    @Override
    public int calculateAllAncestorsX(AncestorPerson person) {
        int minimalX = config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            int childrenWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0);
            int childrenX = childrenWidth / 2 - extensionConfig.getHalfSpouseLabelSpace();
            minimalX = Math.max(minimalX, childrenX + SIBLINGS_GAP);
        }

        if (person.getFather() != null) {
            minimalX = Math.max(minimalX, extensionConfig.getCoupleWidth() / 2 + SIBLINGS_GAP);
            int fathersX = (int) ((extensionConfig.getCoupleWidth() + extensionConfig.getGapBetweenCouples()) * person.getFather().getLastParentsCount()
                    + extensionConfig.getAllAncestorsCoupleIncrease() / 2.0);
            return Math.max(fathersX, minimalX);
        } else {
            return minimalX;
        }
    }

    @Override
    public int calculateAllAncestorsWidth(AncestorPerson person) {
        return (int) ((extensionConfig.getCoupleWidth() + extensionConfig.getGapBetweenCouples())
                * person.getLastParentsCount() + extensionConfig.getAllAncestorsCoupleIncrease());
    }

    @Override
    public int calculateParentLineageWidth(AncestorPerson person) {
        int pageWidth = calculateFatherLineageWidth(person);
        pageWidth = pageWidth + extensionConfig.getCoupleWidth() + (extensionConfig.getMarriageLabelWidth() - config.getAdultImageWidth());

        if (config.isShowSiblings()) {
            pageWidth = pageWidth + calculateMotherSiblingsWidth(person.getMother());
        }

        return Math.max(pageWidth, extensionConfig.getMarriageLabelWidth() + 2 * (config.getAdultImageWidth() + Spaces.SIBLINGS_GAP));
    }

    @Override
    public int calculateFatherLineageX(AncestorPerson person) {
        int positionX = config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        int parentWidth = extensionConfig.getParentGenerationWidth(person);
        int childrenShift = getChildrenShift(person);

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
                    siblingsShift = siblingsShift + person.getMaxOlderSiblingsSpouse() * (extensionConfig.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        return positionX + Math.max(Math.max(parentWidth, siblingsShift), childrenShift);
    }

    @Override
    public int calculateFatherLineageWidth(AncestorPerson person) {
        int pageWidth = extensionConfig.getCoupleWidth() + 2 * SIBLINGS_GAP;
        int parentWidth = extensionConfig.getParentGenerationWidth(person);
        int childrenWidth = getChildrenShift(person) - extensionConfig.getCoupleWidth() / 2;

        int siblingsWidth = 0;
        if (config.isShowSiblings()) {
            siblingsWidth = calculateFatherSiblingsWidth(person);
        }

        int spouseWidth = 0;
        if (config.isShowSpouses() && person.getSpouseCouple() != null) {

            int extraSpouseCount = person.getSpouseCouples().size() - 1;
            if (extraSpouseCount > 0) {
                spouseWidth = extensionConfig.getSpouseDistance() * extraSpouseCount;
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
        int positionX = calculateFatherLineageX(person);

        if (config.isShowSiblings()) {

            if (person.getMother() != null && person.getMother().getOlderSiblings().size() > 0) {
                positionX = positionX - config.getAdultImageWidth() - extensionConfig.getMarriageLabelWidth();
                positionX = positionX + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * Math.max(person.getMother().getMaxOlderSiblings(), person.getMaxOlderSiblings()) - extensionConfig.getHalfSpouseLabelSpace();
                if (config.isShowSiblingSpouses()) {
                    positionX = positionX + person.getMaxOlderSiblingsSpouse() * (extensionConfig.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        return positionX;
    }

    @Override
    public int calculateMotherLineageWidth(AncestorPerson person) {
        int pageWidth = calculateFatherLineageWidth(person);
        pageWidth = pageWidth - config.getAdultImageWidth() - extensionConfig.getMarriageLabelWidth() + extensionConfig.getHalfSpouseLabelSpace();

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
            siblingWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() - extensionConfig.getHalfSpouseLabelSpace() + SIBLINGS_GAP;
            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + person.getMaxOlderSiblingsSpouse() * (extensionConfig.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }

        if (person.getMaxYoungerSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxYoungerSiblings() + SIBLINGS_GAP;
            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + person.getMaxYoungerSiblingsSpouse() * (extensionConfig.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }
        return siblingWidth;
    }

    @Override
    public int calculateMotherSiblingsWidth(AncestorPerson mother) {
        int siblingWidth = 0;
        if (mother != null) {
            if (mother.getMaxOlderSiblings() > 0) {
                siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxOlderSiblings() - extensionConfig.getHalfSpouseLabelSpace() + SIBLINGS_GAP;

                if (config.isShowSiblingSpouses()) {
                    siblingWidth = siblingWidth + mother.getMaxOlderSiblingsSpouse() * (extensionConfig.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }

            if (mother.getMaxYoungerSiblings() > 0) {
                siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxYoungerSiblings() + SIBLINGS_GAP;
                if (config.isShowSiblingSpouses()) {
                    siblingWidth = siblingWidth + mother.getMaxYoungerSiblingsSpouse() * (extensionConfig.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }
        return siblingWidth;
    }

    @Override
    public int calculateLineageY(AncestorPerson person, int pageHeight) {
        int positionY = pageHeight - VERTICAL_GAP / 2 - config.getAdultImageHeight() / 2;
        if (config.isShowSpouses() && person.getSpouseCouple() != null
                && config.isShowChildren() && !person.getSpouseCouple().getChildren().isEmpty()) {
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

        return pageHeight + TITLE_HEIGHT;
    }

    @Override
    public int calculateAllDescendentHeight(AncestorPerson person) {
        int pageHeight = (config.getAdultImageHeight() + VERTICAL_GAP);

        if (person.getSpouseCouple() != null) {
            pageHeight += person.getSpouseCouple().getDescendentTreeInfo().getMaxGenerationsCount()
                    * (config.getAdultImageHeight() + VERTICAL_GAP);
        }

        return pageHeight + TITLE_HEIGHT;
    }

    public int getChildrenShift(AncestorPerson person) {
        int childrenShift = 0;
        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            childrenShift = ((config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0)
                    - extensionConfig.getCoupleWidth()) / 2;
        }
        return childrenShift;
    }
}
