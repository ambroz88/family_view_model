package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;

import java.util.Objects;

public class LineageServiceImpl extends CommonAncestorServiceImpl implements LineageService {

    public LineageServiceImpl(AncestorPerson rootPerson, String treeName, ConfigurationService configurationService) {
        super(rootPerson, treeName, configurationService);
    }

    @Override
    public Position addClosestFamily(AncestorPerson person) {
        generateSpouseAndSiblings(person);
        Position heraldryPosition = new Position(0, -(configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2);
        treeModelService.addLine(new Position(), heraldryPosition, Relation.DIRECT);
        return heraldryPosition;
    }

    @Override
    public TreeModel generateFathersFamily(Position heraldryPosition, AncestorPerson person) {
        if (person != null) {
            ParentsDto parents = generateParents(heraldryPosition, person);
            if (parents != null) {
                Position parentPosition = Objects.requireNonNullElse(parents.husbandPosition(), parents.wifePosition());
                Position nextHeraldryPosition = new Position(parentPosition.x(), parents.nextHeraldryY());
                AncestorPerson parent = Objects.requireNonNullElse(person.getFather(), person.getMother());

                if (parent.hasMinOneParent()) {
                    treeModelService.addLine(parentPosition, nextHeraldryPosition, Relation.DIRECT);
                }
                if (configService.isShowSiblings()) {
                    addSiblings(parentPosition, parent);
                    if (!parent.getYoungerSiblings().isEmpty()) {
                        addLineAboveSpouse(parentPosition, parents.nextHeraldryY());
                    }
                }

                generateFathersFamily(nextHeraldryPosition, parent);
            }
        }
        return getTreeModel();
    }

    @Override
    public TreeModel generateMotherFamily(Position heraldryPosition, AncestorPerson person) {
        if (person != null) {
            ParentsDto parents = generateSwitchedParents(heraldryPosition, person);
            if (parents != null) {
                Position parentPosition = Objects.requireNonNullElse(parents.wifePosition(), parents.husbandPosition());
                Position nextHeraldryPosition = new Position(parentPosition.x(), parents.nextHeraldryY());
                AncestorPerson parent = Objects.requireNonNullElse(person.getMother(), person.getFather());

                if (parent.hasMinOneParent()) {
                    treeModelService.addLine(parentPosition, nextHeraldryPosition, Relation.DIRECT);
                }
                if (configService.isShowSiblings()) {
                    addSiblings(parentPosition, parent);
                    if (!parent.getYoungerSiblings().isEmpty()) {
                        addLineAboveSpouse(parentPosition, parents.nextHeraldryY());
                    }
                }

                generateFathersFamily(nextHeraldryPosition, parent);
            }
        }
        return getTreeModel();
    }

    public ParentsDto generateSwitchedParents(Position heraldryPosition, AncestorPerson child) {
        if (configService.isShowCouplesVertical()) {
            return generateSwitchedParents(heraldryPosition, child, new VerticalConfigurationService(configService));
        } else {
            return generateSwitchedParents(heraldryPosition, child, new HorizontalConfigurationService(configService));
        }
    }

    private ParentsDto generateSwitchedParents(Position heraldryPosition, AncestorPerson child, ConfigurationExtensionService extensionConfig) {
        Position motherPosition;
        if (child.getMother() != null) {
            motherPosition = extensionConfig.getFatherPositionFromHeraldry(heraldryPosition);
            treeModelService.addPerson(motherPosition, child.getMother());
        } else {
            motherPosition = null;
        }

        Position fatherPosition;
        if (child.getFather() != null) {
            if (motherPosition == null) {
                fatherPosition = extensionConfig.getFatherPositionFromHeraldry(heraldryPosition);
            } else {
                fatherPosition = extensionConfig.getMotherPositionFromHeraldry(heraldryPosition);
            }
            treeModelService.addPerson(fatherPosition, child.getFather());
        } else {
            fatherPosition = null;
        }

        return getParentsDto(heraldryPosition, fatherPosition, motherPosition, child, extensionConfig);
    }

    @Override
    public TreeModel generateParentsFamily(Position heraldryPosition, AncestorPerson person) {
        ParentsDto parentsDto = generateHorizontalParents(heraldryPosition, person);
        addFatherFamily(person.getFather(), parentsDto);
        addMotherFamily(person.getMother(), parentsDto);
        return getTreeModel();
    }

    private void addFatherFamily(AncestorPerson father, ParentsDto parentsDto) {
        int fatherSiblingsWidth = 0;
        if (configService.isShowSiblings() && father.getFather() != null) {
            father.moveYoungerSiblingsToOlder();

            int fathersSiblings = father.getFather().getMaxYoungerSiblings();
            if (fathersSiblings > 0) {
                fatherSiblingsWidth = fathersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }
        int fatherHeraldryX = parentsDto.husbandPosition().x() - extensionConfig.getMotherHorizontalDistance() - fatherSiblingsWidth;

        Position fatherHeraldry = new Position(fatherHeraldryX, parentsDto.nextHeraldryY());
        generateFathersFamily(fatherHeraldry, father);
        if (father.getOlderSiblings().isEmpty()) {
            treeModelService.addLine(fatherHeraldry, parentsDto.husbandPosition(), Relation.DIRECT);
        } else {
            addSiblings(new Position(fatherHeraldryX + configService.getAdultImageWidth() / 2, parentsDto.husbandPosition().y()), father);
            treeModelService.addLine(parentsDto.husbandPosition(), fatherHeraldry, Relation.DIRECT);
        }
    }

    private void addMotherFamily(AncestorPerson mother, ParentsDto parentsDto) {
        int motherSiblingsWidth = 0;
        if (configService.isShowSiblings() && mother.getFather() != null) {
            mother.moveOlderSiblingsToYounger();

            int mothersSiblings = mother.getFather().getMaxOlderSiblings();
            if (mothersSiblings > 0) {
                motherSiblingsWidth = mothersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }

        int motherHeraldryX = parentsDto.wifePosition().x() + extensionConfig.getFatherHorizontalDistance() + motherSiblingsWidth;

        Position motherHeraldry = new Position(motherHeraldryX, parentsDto.nextHeraldryY());
        generateFathersFamily(motherHeraldry, mother);
        if (mother.getYoungerSiblings().isEmpty()) {
            treeModelService.addLine(motherHeraldry, parentsDto.wifePosition(), Relation.DIRECT);
        } else {
            addSiblings(new Position(motherHeraldryX - extensionConfig.getSpouseDistance() - configService.getAdultImageWidth() / 2, parentsDto.wifePosition().y()), mother);
            treeModelService.addLine(parentsDto.wifePosition(), motherHeraldry, Relation.DIRECT);
        }
    }

    @Override
    public TreeModel addAllParents(Position heraldryPosition, AncestorPerson child) {
        ParentsDto parentsDto;
        if (child.getAncestorGenerations() == 1) {
            parentsDto = generateVerticalParents(heraldryPosition, child);
        } else {
            parentsDto = generateHorizontalParents(heraldryPosition, child);
        }
        ConfigurationExtensionService horizontalConfig = new HorizontalConfigurationService(configService);
        ConfigurationExtensionService verticalConfig = new VerticalConfigurationService(configService);

        if (child.getMother() != null) {
            AncestorPerson mother = child.getMother();
            double motherParentsCount = Math.min(mother.getInnerParentsCount(), mother.getLastParentsCount());

            int mothersParentHeraldryHorizontalShift;
            if (mother.getAncestorGenerations() == 1) {
                mothersParentHeraldryHorizontalShift = 0;
            } else {
                mothersParentHeraldryHorizontalShift = (int) (motherParentsCount * (verticalConfig.getCoupleWidth() + horizontalConfig.getMarriageLabelWidth()))
                        - configService.getAdultImageWidth() / 2 - horizontalConfig.getMarriageLabelWidth() + horizontalConfig.getMarriageLabelWidth() / 2;
            }
            Position motherHeraldryPosition = new Position(
                    parentsDto.wifePosition().x() + mothersParentHeraldryHorizontalShift,
                    parentsDto.nextHeraldryY()
            );
            if (mother.hasMinOneParent()) {
//                mother.moveOlderSiblingsToYounger();
//                addSiblings(parentsDto.husbandPosition(), mother);
                treeModelService.addLine(motherHeraldryPosition, parentsDto.wifePosition(), Relation.DIRECT);
            }
            addAllParents(motherHeraldryPosition, mother);
        }

        if (child.getFather() != null) {
            AncestorPerson father = child.getFather();
            double fatherParentsCount = Math.min(father.getInnerParentsCount(), father.getLastParentsCount());

            int fathersParentHeraldryHorizontalShift;
            if (father.getAncestorGenerations() == 1) {
                fathersParentHeraldryHorizontalShift = verticalConfig.getMarriageLabelWidth();
            } else {
                fathersParentHeraldryHorizontalShift = (int) (fatherParentsCount * (verticalConfig.getCoupleWidth() + horizontalConfig.getMarriageLabelWidth()))
                        - configService.getAdultImageWidth() / 2 - horizontalConfig.getMarriageLabelWidth() + horizontalConfig.getMarriageLabelWidth() / 2;
            }

            Position fatherHeraldryPosition = new Position(
                    parentsDto.husbandPosition().x() - fathersParentHeraldryHorizontalShift,
                    parentsDto.nextHeraldryY()
            );
            if (father.hasMinOneParent()) {
                father.moveYoungerSiblingsToOlder();
                addSiblings(parentsDto.husbandPosition(), father);
                treeModelService.addLine(fatherHeraldryPosition, parentsDto.husbandPosition(), Relation.DIRECT);
            }
            addAllParents(fatherHeraldryPosition, father);
        }

        return getTreeModel();
    }

}
