package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.dto.FamilyDto;
import cz.ambrogenea.familyvision.service.CommonAncestorService;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.dto.AncestorCouple;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.ParentsDto;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.enums.LabelType;
import cz.ambrogenea.familyvision.enums.Relation;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.TreeModelService;

public class CommonAncestorServiceImpl implements CommonAncestorService {

    protected final ConfigurationService configService;
    protected final ConfigurationExtensionService extensionConfig;
    protected final TreeModelService treeModelService;

    public CommonAncestorServiceImpl(AncestorPerson rootPerson, String treeName, ConfigurationService configurationService) {
        this.configService = configurationService;
        if (configurationService.isShowCouplesVertical()) {
            extensionConfig = new VerticalConfigurationService(configurationService);
        } else {
            extensionConfig = new HorizontalConfigurationService(configurationService);
        }
        this.treeModelService = new TreeModelServiceImpl(rootPerson, treeName, configurationService);
    }

    @Override
    public Position addSiblingsAndDescendents(AncestorPerson rootPerson) {
        Position rootPersonPosition = new Position();
        treeModelService.addPerson(rootPersonPosition, rootPerson);
        Position spousePosition = generateSpousesWithDescendents(rootPerson, rootPersonPosition);

        addSiblings(rootPersonPosition, rootPerson, spousePosition.x());

        Position heraldryPosition = new Position(0, -(configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2);
        if (rootPerson.hasMinOneParent()) {
            treeModelService.addLine(new Position(), heraldryPosition, Relation.DIRECT);
        }
        return heraldryPosition;
    }

    private Position generateSpousesWithDescendents(AncestorPerson rootPerson, Position rootPersonPosition) {
        if (rootPerson.getSpouse() != null) {
            HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
            Position spousePosition = rootPersonPosition;
            Position lastChildPosition = rootPersonPosition.addXAndY(0, config.getGenerationsVerticalDistance());

            for (int index = 0; index < rootPerson.getSpouseCouples().size(); index++) {
                AncestorCouple spouseCouple = rootPerson.getSpouseCouple(index);
                if (spouseCouple.getChildren().isEmpty()) {
                    spousePosition = spousePosition.addXAndY(config.getSpouseDistance(), 0);
                    addSpouseWithMarriage(spousePosition, spouseCouple, rootPerson);
                } else {
                    if (index == 0) {
                        int childrenHalfWidth = getMaxChildrenWidth(spouseCouple) / 2;
                        lastChildPosition = rootPersonPosition.addXAndY(
                                -childrenHalfWidth - configService.nextChildrenX() + (configService.getAdultImageWidth() + config.getSpouseDistance()) / 2,
                                config.getGenerationsVerticalDistance());
                        spousePosition = new Position(lastChildPosition.x(), rootPersonPosition.y());
                    }
                    FamilyDto familyDto = generateChildren(
                            new FamilyDto(spousePosition, lastChildPosition), spouseCouple, rootPerson
                    );
                    spousePosition = familyDto.lastParentPosition();
                    lastChildPosition = familyDto.lastChildrenPosition();
                }
            }
            return spousePosition;
        }
        return rootPersonPosition;
    }

    private FamilyDto generateChildren(FamilyDto prevFamilyDto, AncestorCouple spouseCouple, AncestorPerson rootSpouse) {
        if (spouseCouple != null) { //useless condition
            HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
            Position spousePosition;
            FamilyDto familyDto;
            if (!spouseCouple.getChildren().isEmpty()) {
                familyDto = addChildren(prevFamilyDto, spouseCouple);
                spousePosition = familyDto.lastParentPosition();
            } else {
                spousePosition = prevFamilyDto.lastParentPosition().addXAndY(configService.nextChildrenX() + config.getSpouseDistance(), 0);
                familyDto = new FamilyDto(spousePosition, prevFamilyDto.lastChildrenPosition());
            }
            addBothSpouseWithMarriage(spousePosition, spouseCouple, rootSpouse);
            return familyDto;
        } else {
            return prevFamilyDto;
        }
    }

    private int getMaxChildrenWidth(AncestorCouple spouseCouple) {
        HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
        int maxSinglesWidth = spouseCouple.getDescendentTreeInfo().getMaxSinglesCount() * (configService.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);
        int maxCouplesWidth = spouseCouple.getDescendentTreeInfo().getMaxCouplesCount() * (config.getCoupleWidth() + Spaces.HORIZONTAL_GAP);
        return Math.max(maxSinglesWidth + maxCouplesWidth - Spaces.HORIZONTAL_GAP, config.getCoupleWidth());
    }

    private void addBothSpouseWithMarriage(Position spousePosition, AncestorCouple spouseCouple, AncestorPerson rootSpouse) {
        HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
        addPerson(spousePosition.addXAndY(-config.getSpouseDistance(), 0), rootSpouse);
        addPerson(spousePosition, spouseCouple.getSpouse(rootSpouse.getSex()));
        treeModelService.addMarriage(spousePosition.addXAndY(-config.getSpouseDistance() / 2, 0),
                spouseCouple.getDatePlace().getLocalizedDate(configService.getLocale()),
                LabelType.TALL);
    }

    private void addSpouseWithMarriage(Position spousePosition, AncestorCouple spouseCouple, AncestorPerson rootSpouse) {
        HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
        addPerson(spousePosition, spouseCouple.getSpouse(rootSpouse.getSex()));
        treeModelService.addMarriage(spousePosition.addXAndY(-config.getSpouseDistance() / 2, 0),
                spouseCouple.getDatePlace().getLocalizedDate(configService.getLocale()),
                LabelType.TALL);
    }

    private FamilyDto addChildren(FamilyDto prevFamilyDto, AncestorCouple spouseCouple) {
        HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
        int maxChildrenWidth = getMaxChildrenWidth(spouseCouple);

        Position coupleCenterPosition = new Position(
                prevFamilyDto.maxX() + (maxChildrenWidth + configService.getAdultImageWidth()) / 2 + Spaces.HORIZONTAL_GAP,
                prevFamilyDto.lastParentPosition().y()
        );
        Position heraldryPosition = coupleCenterPosition.addXAndY(0, configService.getHeraldryVerticalDistance());
        treeModelService.addLine(heraldryPosition, coupleCenterPosition, Relation.DIRECT);
        if (configService.isShowHeraldry()) {
            treeModelService.addHeraldry(heraldryPosition, spouseCouple.getChildren().get(0).getBirthDatePlace().getSimplePlace());
        }

        Position spousePosition = coupleCenterPosition.addXAndY(config.getSpouseDistance() / 2, 0);
        Position childrenPosition = prevFamilyDto.lastChildrenPosition();

        FamilyDto currentFamily;
        int childrenCount = spouseCouple.getChildren().size();
        int maxX = prevFamilyDto.maxX();
        AncestorPerson child;
        for (int i = 0; i < childrenCount; i++) {
            child = spouseCouple.getChildren().get(i);
            if (child.getSpouse() != null) {
                currentFamily = generateChildren(new FamilyDto(
                        childrenPosition,
                        childrenPosition.addXAndY(0, config.getGenerationsVerticalDistance())
                ), child.getSpouseCouple(), child);

                maxX = Math.max(maxX, currentFamily.maxX());
                childrenPosition = currentFamily.lastParentPosition();
                Position childrenSpouse = childrenPosition.addXAndY(-config.getSpouseDistance(), 0);
//                TODO: what about single parent? ___________________________|
                if (i == 0 || i == childrenCount - 1) {
                    treeModelService.addLine(childrenSpouse, heraldryPosition, Relation.DIRECT);
                } else {
                    treeModelService.addLine(childrenSpouse, new Position(childrenSpouse.x(), heraldryPosition.y()), Relation.DIRECT);
                }
            } else {
                childrenPosition = childrenPosition.addXAndY(configService.nextChildrenX(), 0);
                maxX = Math.max(maxX, childrenPosition.x());

                treeModelService.addPerson(childrenPosition, child);
                if (i == 0 || i == childrenCount - 1) {
                    treeModelService.addLine(childrenPosition, heraldryPosition, Relation.DIRECT);
                } else {
                    treeModelService.addLine(childrenPosition, new Position(childrenPosition.x(), heraldryPosition.y()), Relation.DIRECT);
                }
            }
        }
        return new FamilyDto(spousePosition, new Position(maxX, childrenPosition.y()));
    }

    @Override
    public void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling) {
        int spouseGap = 0;
        if (rootSibling.getSpouse() != null) {
            spouseGap = extensionConfig.getSpouseDistance();
        }
        addSiblings(rootSiblingPosition, rootSibling, spouseGap);
    }

    private void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling, int lastSpouseGapX) {
        addOlderSiblings(rootSiblingPosition, rootSibling);
        if (!rootSibling.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouses(rootSiblingPosition, lastSpouseGapX);
            addYoungerSiblings(rootSiblingPosition.addXAndY(lastSpouseGapX, 0), rootSibling);
        }
    }

    private void addLineAboveSpouses(Position rootSibling, int spouseGap) {
        Position linePosition = rootSibling.addXAndY(0, -configService.getHeraldryVerticalDistance());
        treeModelService.addLine(linePosition, linePosition.addXAndY(spouseGap, 0), Relation.SIDE);
    }

    private void addOlderSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position heraldryPosition = rootSiblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance());

        int olderSiblingCount = rootChild.getOlderSiblings().size();
        int widthDifference = configService.getAdultImageWidth() - configService.getSiblingImageWidth();
        Position siblingPosition = rootSiblingPosition.addXAndY(-widthDifference / 2 - Spaces.HORIZONTAL_GAP, 0);

        for (int i = olderSiblingCount - 1; i >= 0; i--) {
            sibling = rootChild.getOlderSiblings().get(i);
            startX = siblingPosition.x() - configService.nextSiblingX();
            siblingPosition = new Position(startX, rootSiblingPosition.y());


            if (sibling.getSpouseCouple() == null) {
                treeModelService.addPerson(siblingPosition, sibling);
            } else {
                siblingPosition = siblingPosition.addXAndY(-extensionConfig.getSiblingSpouseDistance(), 0);
                addSiblingsCouple(siblingPosition, sibling.getSpouseCouple(), sibling);
            }
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

        Position siblingPosition = rootSiblingPosition.addXAndY(widthDifference / 2 + Spaces.HORIZONTAL_GAP, 0);

        for (int i = 0; i < youngerSiblingsCount; i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = siblingPosition.x() + configService.nextSiblingX();
            siblingPosition = new Position(startX, rootSiblingPosition.y());

            if (i == youngerSiblingsCount - 1) {
                treeModelService.addLine(siblingPosition, heraldryPosition, Relation.SIDE);
            } else {
                treeModelService.addLine(siblingPosition, siblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance()), Relation.DIRECT);
            }

            if (sibling.getSpouseCouple() == null) {
                treeModelService.addPerson(siblingPosition, sibling);
            } else {
                addSiblingsCouple(siblingPosition, sibling.getSpouseCouple(), sibling);
                siblingPosition = siblingPosition.addXAndY(extensionConfig.getSiblingSpouseDistance(), 0);
            }
        }
    }

    private void addSiblingsCouple(Position spousePosition, AncestorCouple spouseCouple, AncestorPerson rootSpouse) {
        addPerson(spousePosition, rootSpouse);
        addPerson(extensionConfig.getSiblingsWifePosition(spousePosition), spouseCouple.getSpouse(rootSpouse.getSex()));
        treeModelService.addMarriage(
                extensionConfig.getSiblingsMarriagePosition(spousePosition),
                spouseCouple.getDatePlace().getLocalizedDate(configService.getLocale()),
                extensionConfig.getMarriageLabelType()
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
    public void addLine(Position start, Position end, Relation lineType) {
        treeModelService.addLine(start, end, lineType);
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
            fatherPosition = extensionConfig.getFatherPositionFromHeraldry(heraldryPosition);
            treeModelService.addPerson(fatherPosition, child.getFather());
        } else {
            fatherPosition = null;
        }

        Position motherPosition;
        if (child.getMother() != null) {
            if (fatherPosition == null) {
                motherPosition = extensionConfig.getFatherPositionFromHeraldry(heraldryPosition);
            } else {
                motherPosition = extensionConfig.getMotherPositionFromHeraldry(heraldryPosition);
            }
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
            return new ParentsDto(fatherPosition, motherPosition, heraldryPosition.y() - extensionConfig.getGenerationsVerticalDistance());
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
