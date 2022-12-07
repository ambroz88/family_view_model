package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.CommonAncestorService;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.TreeModelService;

public class CommonAncestorServiceImpl implements CommonAncestorService {

    protected ConfigurationService configService;
    protected ConfigurationExtensionService extensionConfig;
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
            if (configService.isShowSiblings() && !rootPerson.getYoungerSiblings().isEmpty()) {
                int spouseGap = lastSpouse.getX() - rootPersonPosition.getX();
                addLineAboveSpouses(rootPersonPosition, spouseGap);

                addOlderSiblings(rootPersonPosition, rootPerson);
                addYoungerSiblings(new Position(lastSpouse.getX(), rootPersonPosition.getY()), rootPerson);
            }
        } else {
            addSiblings(rootPersonPosition, rootPerson);
        }
    }

    @Override
    public Position addRootSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
            Position spousePosition = new Position(rootPersonPosition);

            Position label;
            int labelShift = configService.getAdultImageWidth() / 2 + config.getMarriageLabelWidth();

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                spousePosition = spousePosition.addXAndY(config.getSpouseDistance(), 0);
                label = spousePosition.addXAndY(-labelShift, 0);
                addPerson(spousePosition, person.getSpouse(index));
                addMarriage(label, config.getMarriageLabelWidth(),
                        person.getSpouseCouple(index).getDatePlace().getLocalizedDate(configService.getLocale()));
                //TODO: add children for each spouse
            }

            return spousePosition;
        }
        return rootPersonPosition;
    }

    private void addLineAboveSpouses(Position rootSibling, int spouseGap) {
        int verticalShift = (configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2;
        Position linePosition = rootSibling.addXAndY(0, -verticalShift);
        addLine(linePosition, linePosition.addXAndY(spouseGap, 0), Relation.SIDE);
    }

    @Override
    public int generateChildren(Position fatherPosition, AncestorCouple spouseCouple) {
        int childrenWidth = 0;
        if (spouseCouple != null) {

            if (configService.isShowChildren() && !spouseCouple.getChildren().isEmpty()) {
                HorizontalConfigurationService config = new HorizontalConfigurationService(configService);
                Position coupleCenterPosition = fatherPosition.addXAndY(config.getSpouseDistance() / 2, 0);

                Position heraldryPosition = coupleCenterPosition.addXAndY(0, configService.getHeraldryVerticalDistance());
                addLine(heraldryPosition, coupleCenterPosition, Relation.DIRECT);
                if (configService.isShowHeraldry()) {
                    addHeraldry(heraldryPosition, spouseCouple.getChildren().get(0).getBirthDatePlace().getSimplePlace());
                }

                childrenWidth = addChildren(heraldryPosition, spouseCouple);
            }

        }
        return childrenWidth;
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

            addPerson(siblingPosition, sibling);
            if (i == 0) {
                addLine(siblingPosition, heraldryPosition, Relation.DIRECT);
            } else {
                addLine(siblingPosition, siblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance()), Relation.DIRECT);
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

            addPerson(siblingPosition, sibling);
            if (i == youngerSiblingsCount - 1) {
                addLine(siblingPosition, heraldryPosition, Relation.DIRECT);
            } else {
                addLine(siblingPosition, siblingPosition.addXAndY(0, -configService.getHeraldryVerticalDistance()), Relation.DIRECT);
            }
        }
    }

    private int addChildren(Position heraldryPosition, AncestorCouple spouseCouple) {
        int childrenCount = spouseCouple.getChildren().size();
        int childrenWidth = childrenCount * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) - Spaces.HORIZONTAL_GAP;

        Position childrenPosition = heraldryPosition.addXAndY(
                configService.getSiblingImageWidth() / 2 - childrenWidth / 2,
                (configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2);

        for (int i = 0; i < childrenCount; i++) {
            if (i == 0 || i == childrenCount - 1) {
                addLine(childrenPosition, heraldryPosition, Relation.DIRECT);
            } else {
                addLine(childrenPosition, new Position(childrenPosition.getX(), heraldryPosition.getY()), Relation.DIRECT);
            }
            //TODO: draw spouse of the children
            addPerson(childrenPosition, spouseCouple.getChildren().get(i));
            childrenPosition = childrenPosition.addXAndY(configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP, 0);
        }

        return childrenWidth / 2;
    }

    protected void addLineAboveSpouse(Position husbandPosition, int positionY) {
        addLine(
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
        this.extensionConfig = extensionConfig;

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

        return getParentsDto(heraldryPosition, fatherPosition, motherPosition, child, extensionConfig.getGenerationsVerticalDistance());
    }

    protected ParentsDto getParentsDto(Position heraldryPosition, Position fatherPosition, Position motherPosition, AncestorPerson child, int yGenerationShift) {
        if (fatherPosition == null && motherPosition == null) {
            return null;
        } else {
            addLine(heraldryPosition, heraldryPosition.addXAndY(0, -yGenerationShift / 2), Relation.DIRECT);
            if (configService.isShowHeraldry()) {
                treeModelService.addHeraldry(heraldryPosition, child.getBirthDatePlace().getSimplePlace());
            }
            if (fatherPosition != null && motherPosition != null) {
                treeModelService.addMarriages(
                        heraldryPosition.addXAndY(
                                -extensionConfig.getMarriageLabelHorizontalDistance(),
                                -extensionConfig.getMarriageLabelVerticalDistance()
                        ),
                        calculateMarriageLabelWidth(),
                        child.getParents().getDatePlace().getLocalizedDate(configService.getLocale())
                );
            }
            return new ParentsDto(fatherPosition, motherPosition, heraldryPosition.getY() - yGenerationShift);
        }
    }

    private int calculateMarriageLabelWidth() {
        if (configService.getLabelShape().equals(LabelShape.OVAL)) {
            return extensionConfig.getMarriageLabelWidth() - 2 * Spaces.LABEL_GAP;
        } else {
            return extensionConfig.getSpouseDistance();
        }
    }

    @Override
    public void addPerson(Position personCenter, AncestorPerson person) {
        treeModelService.addPerson(personCenter, person);
    }

    @Override
    public void addRootPerson(Position center, AncestorPerson person) {
        treeModelService.addRootPerson(center, person);
    }

    @Override
    public void addMarriage(Position labelPosition, int labelWidth, String text) {
        treeModelService.addMarriages(labelPosition, labelWidth, text);
    }

    @Override
    public void addLine(Position start, Position end, Relation lineType) {
        treeModelService.addLine(start, end, lineType);
    }

    @Override
    public void addHeraldry(Position childPosition, String simpleBirthPlace) {
        treeModelService.addHeraldry(childPosition, simpleBirthPlace);
    }

    @Override
    public TreeModel getTreeModel() {
        return treeModelService.getTreeModel();
    }

    protected ConfigurationService getConfiguration() {
        return configService;
    }

}
