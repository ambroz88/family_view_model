package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.LabelType;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.CommonAncestorService;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.TreeModelService;

public class CommonAncestorServiceImpl implements CommonAncestorService {

    protected final ConfigurationService configService;
    protected final ConfigurationExtensionService extensionConfig;
    protected final TreeModelService treeModelService;

    public CommonAncestorServiceImpl(ConfigurationService configurationService) {
        this.configService = configurationService;
        if (configurationService.isShowCouplesVertical()) {
            extensionConfig = new VerticalConfigurationService(configurationService);
        } else {
            extensionConfig = new HorizontalConfigurationService(configurationService);
        }
        this.treeModelService = new TreeModelServiceImpl(configurationService);
    }

    @Override
    public void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson) {
        if (configService.isShowSpouses()) {
            Position lastSpouse = addRootSpouses(rootPersonPosition, rootPerson);
            if (configService.isShowSiblings()) {
                addOlderSiblings(rootPersonPosition, rootPerson);
                if (!rootPerson.getYoungerSiblings().isEmpty()) {
                    int spouseGap = lastSpouse.getX() - rootPersonPosition.getX();
                    addLineAboveSpouses(rootPersonPosition, spouseGap);
                    addYoungerSiblings(new Position(lastSpouse.getX(), rootPersonPosition.getY()), rootPerson);
                }
            }
        } else {
            treeModelService.addRootPerson(rootPersonPosition, rootPerson);
            addSiblings(rootPersonPosition, rootPerson);
        }
    }

    @Override
    public Position addRootSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            Position spousePosition = new Position(rootPersonPosition);
            HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                AncestorCouple spouseCouple = person.getSpouseCouple(index);
                if (spouseCouple.getChildren().isEmpty()) {
                    if (index == 0) {
                        treeModelService.addRootPerson(rootPersonPosition, person);
                    }
                    spousePosition = spousePosition.addXAndY(config.getSpouseDistance(), 0);
                } else {
                    if (index == 0) {
                        spousePosition = spousePosition.addXAndY(
                                -(getMaxChildrenWidth(spouseCouple)) / 2 - configService.getAdultImageWidth() / 2 + config.getSpouseDistance() / 2,
                                0);
                    }
                    int lastChildX = generateChildren(spousePosition, spouseCouple, person);
                    spousePosition = new Position(lastChildX, spousePosition.getY());
                }
            }
            return spousePosition;
        } else {
            treeModelService.addRootPerson(rootPersonPosition, person);
        }
        return rootPersonPosition;
    }

    private void addLineAboveSpouses(Position rootSibling, int spouseGap) {
        int verticalShift = (configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2;
        Position linePosition = rootSibling.addXAndY(0, -verticalShift);
        treeModelService.addLine(linePosition, linePosition.addXAndY(spouseGap, 0), Relation.SIDE);
    }

    @Override
    public int generateChildren(Position firstChildrenPosition, AncestorCouple spouseCouple, AncestorPerson rootSpouse) {
        int lastChildrenX;
        if (spouseCouple != null) { //useless condition
            HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
            Position spousePosition;
            if (!spouseCouple.getChildren().isEmpty()) {
                int maxChildrenWidth = getMaxChildrenWidth(spouseCouple);

                Position coupleCenterPosition = firstChildrenPosition.addXAndY(
                        (maxChildrenWidth - configService.getAdultImageWidth()) / 2 + Spaces.HORIZONTAL_GAP,
                        0);
                spousePosition = coupleCenterPosition.addXAndY(config.getSpouseDistance() / 2, 0);
                Position heraldryPosition = coupleCenterPosition.addXAndY(0, configService.getHeraldryVerticalDistance());
                treeModelService.addLine(heraldryPosition, coupleCenterPosition, Relation.DIRECT);
                if (configService.isShowHeraldry()) {
                    treeModelService.addHeraldry(heraldryPosition, spouseCouple.getChildren().get(0).getBirthDatePlace().getSimplePlace());
                }

                lastChildrenX = addChildren(heraldryPosition, spouseCouple);
            } else {
                spousePosition = firstChildrenPosition.addXAndY(config.getSpouseDistance(), 0);
                lastChildrenX = spousePosition.getX() + configService.getAdultImageWidth() + Spaces.HORIZONTAL_GAP;
            }
            addSpouseWithMarriage(spousePosition, spouseCouple, rootSpouse);

        } else {
            lastChildrenX = firstChildrenPosition.getX();
        }
        return lastChildrenX;
    }

    private int getMaxChildrenWidth(AncestorCouple spouseCouple) {
        HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
        int maxSinglesWidth = spouseCouple.getDescendentTreeInfo().getMaxSinglesCount() * (configService.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);
        int maxCouplesWidth = spouseCouple.getDescendentTreeInfo().getMaxCouplesCount() * (config.getCoupleWidth() + Spaces.HORIZONTAL_GAP);
        return Math.max(maxSinglesWidth + maxCouplesWidth - Spaces.HORIZONTAL_GAP, config.getCoupleWidth());
    }

    private void addSpouseWithMarriage(Position spousePosition, AncestorCouple spouseCouple, AncestorPerson rootSpouse) {
        HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
        addPerson(spousePosition.addXAndY(-config.getSpouseDistance(), 0), rootSpouse);
        addPerson(spousePosition, spouseCouple.getSpouse(rootSpouse.getSex()));
        treeModelService.addMarriage(spousePosition.addXAndY(-config.getSpouseDistance() / 2, 0),
                spouseCouple.getDatePlace().getLocalizedDate(configService.getLocale()),
                LabelType.TALL);
    }

    private int addChildren(Position heraldryPosition, AncestorCouple spouseCouple) {
        HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
        int singlesWidth = spouseCouple.getDescendentTreeInfo().getSinglesCount() * (configService.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);
        int couplesWidth = spouseCouple.getDescendentTreeInfo().getCouplesCount() * (config.getCoupleWidth() + Spaces.HORIZONTAL_GAP);
        int childrenWidth = singlesWidth + couplesWidth - Spaces.HORIZONTAL_GAP;

        Position childrenPosition = heraldryPosition.addXAndY(
                (configService.getAdultImageWidth() - childrenWidth) / 2,
                configService.getHeraldryVerticalDistance());

        int childrenCount = spouseCouple.getChildren().size();
        int lastChildX = heraldryPosition.getX();
        AncestorPerson child;
        for (int i = 0; i < childrenCount; i++) {
            child = spouseCouple.getChildren().get(i);
            if (child.getSpouse() != null) {
                lastChildX = generateChildren(childrenPosition, child.getSpouseCouple(), child);
                int husbandX = (lastChildX + childrenPosition.getX()) / 2 - config.getSpouseDistance() / 2 - configService.getAdultImageWidth() / 2;
                childrenPosition = new Position(husbandX, childrenPosition.getY());
            } else {
                treeModelService.addPerson(childrenPosition, child);
                lastChildX = childrenPosition.getX() + configService.getAdultImageWidth() + Spaces.HORIZONTAL_GAP;
            }

            if (i == 0 || i == childrenCount - 1) {
                treeModelService.addLine(childrenPosition, heraldryPosition, Relation.DIRECT);
            } else {
                treeModelService.addLine(childrenPosition, new Position(childrenPosition.getX(), heraldryPosition.getY()), Relation.DIRECT);
            }
            childrenPosition = new Position(lastChildX, childrenPosition.getY());
        }
        return lastChildX;
    }

    @Override
    public void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling) {
        if (configService.isShowSiblings()) {
            addOlderSiblings(rootSiblingPosition, rootSibling);
            int spouseGap = 0;
            if (rootSibling.getSpouse() != null) {
                spouseGap = extensionConfig.getSpouseDistance();
            }
            addYoungerSiblings(rootSiblingPosition.addXAndY(spouseGap, 0), rootSibling);
        }
    }

    private void addOlderSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position heraldryPosition = rootSiblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance());

        int olderSiblingCount = rootChild.getOlderSiblings().size();
        int widthDifference = configService.getAdultImageWidth() - configService.getSiblingImageWidth();
        Position siblingPosition = rootSiblingPosition.addXAndY(-widthDifference / 2, 0);

        for (int i = olderSiblingCount - 1; i >= 0; i--) {
            sibling = rootChild.getOlderSiblings().get(i);

            startX = siblingPosition.getX() - configService.getSiblingImageWidth() - Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());

            treeModelService.addPerson(siblingPosition, sibling);
            if (i == 0) {
                treeModelService.addLine(siblingPosition, heraldryPosition, Relation.SIDE);
            } else {
                treeModelService.addLine(siblingPosition, siblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance()), Relation.DIRECT);
            }
        }
    }

    private void addYoungerSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position heraldryPosition = rootSiblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance());

        int youngerSiblingsCount = rootChild.getYoungerSiblings().size();
        int widthDifference = configService.getAdultImageWidth() - configService.getSiblingImageWidth();

        Position siblingPosition = rootSiblingPosition.addXAndY(widthDifference / 2, 0);

        for (int i = 0; i < youngerSiblingsCount; i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = siblingPosition.getX() + configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());

            treeModelService.addPerson(siblingPosition, sibling);
            if (i == youngerSiblingsCount - 1) {
                treeModelService.addLine(siblingPosition, heraldryPosition, Relation.SIDE);
            } else {
                treeModelService.addLine(siblingPosition, siblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance()), Relation.DIRECT);
            }
        }
    }

    protected void addLineAboveSpouse(Position husbandPosition, int positionY) {
        treeModelService.addLine(
                new Position(husbandPosition.getX(), positionY),
                new Position(husbandPosition.getX() + extensionConfig.getSpouseDistance(), positionY),
                Relation.DIRECT
        );
    }

    @Override
    public ParentsDto generateHorizontalParents(Position heraldryPosition, AncestorPerson child) {
        return generateParents(heraldryPosition, child, new HorizontalConfigurationService(configService));
    }

    @Override
    public ParentsDto generateVerticalParents(Position heraldryPosition, AncestorPerson child) {
        return generateParents(heraldryPosition, child, new VerticalConfigurationService(configService));
    }

    @Override
    public ParentsDto generateParents(Position heraldryPosition, AncestorPerson child) {
        if (configService.isShowCouplesVertical()) {
            return generateParents(heraldryPosition, child, new VerticalConfigurationService(configService));
        } else {
            return generateParents(heraldryPosition, child, new HorizontalConfigurationService(configService));
        }
    }

    private ParentsDto generateParents(Position heraldryPosition, AncestorPerson child, ConfigurationExtensionService extensionConfig) {
        Position fatherPosition;
        if (child.getFather() != null) {
            fatherPosition = heraldryPosition.addXAndY(
                    -extensionConfig.getFatherHorizontalDistance(),
                    -extensionConfig.getFatherVerticalDistance()
            );
            treeModelService.addPerson(fatherPosition, child.getFather());
        } else {
            fatherPosition = null;
        }

        Position motherPosition;
        if (child.getMother() != null) {
            int motherXShift;
            int motherYShift;
            if (fatherPosition == null) {
                motherXShift = 0;
                motherYShift = extensionConfig.getFatherVerticalDistance();
            } else {
                motherXShift = extensionConfig.getMotherHorizontalDistance();
                motherYShift = extensionConfig.getMotherVerticalDistance();
            }
            motherPosition = heraldryPosition.addXAndY(motherXShift, -motherYShift);
            treeModelService.addPerson(motherPosition, child.getMother());
        } else {
            motherPosition = null;
        }

        return getParentsDto(heraldryPosition, fatherPosition, motherPosition, child, extensionConfig);
    }

    protected ParentsDto getParentsDto(Position heraldryPosition, Position fatherPosition, Position motherPosition, AncestorPerson child, ConfigurationExtensionService extensionConfig) {
        if (fatherPosition == null && motherPosition == null) {
            return null;
        } else {
            treeModelService.addLine(heraldryPosition, heraldryPosition.addXAndY(0, -extensionConfig.getGenerationsVerticalDistance() / 2), Relation.DIRECT);
            if (configService.isShowHeraldry()) {
                treeModelService.addHeraldry(heraldryPosition, child.getBirthDatePlace().getSimplePlace());
            }
            if (fatherPosition != null && motherPosition != null) {
                treeModelService.addMarriage(
                        heraldryPosition.addXAndY(
                                0,
                                -extensionConfig.getMarriageLabelVerticalDistance()
                        ),
                        child.getParents().getDatePlace().getLocalizedDate(configService.getLocale()), extensionConfig.getMarriageLabelType()
                );
            }
            return new ParentsDto(fatherPosition, motherPosition, heraldryPosition.getY() - extensionConfig.getGenerationsVerticalDistance());
        }
    }

    @Override
    public void addPerson(Position personCenter, AncestorPerson person) {
        treeModelService.addPerson(personCenter, person);
    }

    @Override
    public TreeModel getTreeModel() {
        return treeModelService.getTreeModel();
    }

}
