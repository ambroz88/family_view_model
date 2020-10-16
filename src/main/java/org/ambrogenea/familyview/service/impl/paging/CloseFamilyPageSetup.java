package org.ambrogenea.familyview.service.impl.paging;

import static org.ambrogenea.familyview.constant.Spaces.HORIZONTAL_GAP;
import static org.ambrogenea.familyview.constant.Spaces.RESIDENCE_SIZE;
import static org.ambrogenea.familyview.constant.Spaces.SIBLINGS_GAP;
import static org.ambrogenea.familyview.constant.Spaces.VERTICAL_GAP;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;

public class CloseFamilyPageSetup implements PageSetup {

    private final ConfigurationService config;

    private final Position startPosition;
    private final int pictureWidth;
    private final int pictureHeight;

    public CloseFamilyPageSetup(ConfigurationService config, AncestorPerson person) {
        this.config = config;
        pictureWidth = calculatePageWidth(person);
        pictureHeight = calculatePageHeight(person);

        int x = calculateXPosition(person);
        int y = calculateYPosition(person);
        startPosition = new Position(x, y);
    }

    @Override
    public Position getRootPosition() {
        return startPosition;
    }

    @Override
    public int getWidth() {
        return pictureWidth;
    }

    @Override
    public int getHeight() {
        return pictureHeight;
    }

    private int calculateXPosition(AncestorPerson personModel) {
        int siblingsLeftSpace = 0;
        int childrenLeftSpace = 0;
        int minimalLeftSpace = config.getAdultImageWidth() / 2 + HORIZONTAL_GAP;

        if (config.isShowSiblings()) {
            siblingsLeftSpace = personModel.getOlderSiblings().size() * (config.getSiblingImageWidth() + HORIZONTAL_GAP) + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        }
        if (config.isShowChildren()) {
            childrenLeftSpace = (int) (personModel.getChildrenCount(0) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP) - config.getHalfSpouseLabelSpace());
        }
        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            minimalLeftSpace = config.getAdultImageWidth() + HORIZONTAL_GAP + config.getMarriageLabelWidth() / 2;
        }

        int x = Math.max(minimalLeftSpace, Math.max(siblingsLeftSpace, childrenLeftSpace + HORIZONTAL_GAP));
        if (config.isShowResidence()) {
            x = x + RESIDENCE_SIZE;
        }

        return x;
    }

    private int calculateYPosition(AncestorPerson personModel) {
        int y = getHeight() - VERTICAL_GAP / 2 - config.getAdultImageHeight() / 2;
        if (config.isShowChildren() && personModel.getSpouseCouple() != null && !personModel.getSpouseCouple().getChildren().isEmpty()) {
            y = getHeight() - (int) (1.5 * (VERTICAL_GAP + config.getAdultImageHeight()));
        }
        return y;
    }

    private int calculatePageHeight(AncestorPerson personModel) {
        int generationHeight = config.getAdultImageHeight() + VERTICAL_GAP;
        return generationHeight * calculateGenerations(personModel);
    }

    private int calculatePageWidth(AncestorPerson personModel) {
        int childrenLeftWidth = 0;
        int childrenRightWidth = 0;
        int childrenSpouseWidth = 0;
        int parentsHalfWidth = 0;

        int siblingsLeftWidth = config.getSiblingImageWidth() / 2 + HORIZONTAL_GAP;
        int siblingsRightWidth = config.getSiblingImageWidth() / 2 + HORIZONTAL_GAP;

        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            parentsHalfWidth = config.getMarriageLabelWidth() / 2 + config.getAdultImageWidth() + HORIZONTAL_GAP;
        }

        if (config.isShowSiblings()) {
            siblingsLeftWidth = siblingsLeftWidth + personModel.getOlderSiblings().size() * (config.getSiblingImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
            siblingsRightWidth = siblingsRightWidth + personModel.getYoungerSiblings().size() * (config.getSiblingImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
        }
        if (config.isShowSpouses()) {
            int spouseRightIncrease;
            for (int i = 0; i < personModel.getSpouseCouples().size(); i++) {
                spouseRightIncrease = config.getSpouseLabelSpace();

                if (config.isShowChildren()) {
                    if (i == 0) {
                        childrenLeftWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP)) - config.getHalfSpouseLabelSpace();
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP)) + config.getHalfSpouseLabelSpace();
                    } else if (i == personModel.getSpouseCouples().size() - 1) {
                        childrenSpouseWidth = childrenSpouseWidth + (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP));
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP));
                    } else {
                        childrenRightWidth = personModel.getChildrenCount(i) * (config.getSiblingImageWidth() + HORIZONTAL_GAP);
                    }
                    childrenRightWidth = childrenRightWidth + SIBLINGS_GAP;
                }
                if (i == personModel.getSpouseCouples().size() - 1) {
                    siblingsRightWidth = childrenSpouseWidth + Math.max(siblingsRightWidth + spouseRightIncrease, childrenRightWidth);
                } else {
                    childrenSpouseWidth = childrenSpouseWidth + Math.max(spouseRightIncrease, childrenRightWidth + HORIZONTAL_GAP);
                }
            }
        }

        int leftWidth = Math.max(parentsHalfWidth, Math.max(siblingsLeftWidth, childrenLeftWidth));
        int rightWidth = Math.max(parentsHalfWidth, siblingsRightWidth);
        int width = leftWidth + rightWidth;
        if (config.isShowResidence()) {
            width = width + 2 * RESIDENCE_SIZE;
        }

        return width;
    }

    public int calculateGenerations(AncestorPerson personModel) {
        int generationCount = 1;

        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            generationCount++;
        }
        if (config.isShowChildren() && personModel.getAllChildrenCount() > 0) {
            generationCount++;
        }
        return generationCount;
    }

}
