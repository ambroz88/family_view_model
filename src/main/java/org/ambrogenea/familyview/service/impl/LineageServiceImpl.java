package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;

import java.util.List;
import java.util.Objects;

public class LineageServiceImpl extends CommonAncestorServiceImpl implements LineageService {

    public LineageServiceImpl(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Override
    public Position addClosestFamily(Position rootPosition, AncestorPerson person) {
        treeModelService.addRootPerson(rootPosition, person);
        generateSpouseAndSiblings(rootPosition, person);
        generateChildren(rootPosition, person.getSpouseCouple());

        Position heraldryPosition = rootPosition.addXAndY(0, -(configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2);
        treeModelService.addLine(rootPosition, heraldryPosition, Relation.DIRECT);
        return heraldryPosition;
    }

    @Override
    public TreeModel generateFathersFamily(Position heraldryPosition, AncestorPerson person) {
        if (person != null) {
            ParentsDto parents = generateParents(heraldryPosition, person);
            if (parents != null) {
                Position parentPosition = Objects.requireNonNullElse(parents.getHusbandPosition(), parents.getWifePosition());
                Position nextHeraldryPosition = new Position(parentPosition.getX(), parents.getNextHeraldryY());
                AncestorPerson parent = Objects.requireNonNullElse(person.getFather(), person.getMother());

                if (parent.hasMinOneParent()) {
                    treeModelService.addLine(parentPosition, nextHeraldryPosition, Relation.DIRECT);
                }
                if (configService.isShowSiblings()) {
                    addSiblings(parentPosition, parent);
                    if (!parent.getYoungerSiblings().isEmpty()) {
                        addLineAboveSpouse(parentPosition, parents.getNextHeraldryY());
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
                Position parentPosition = Objects.requireNonNullElse(parents.getWifePosition(), parents.getHusbandPosition());
                Position nextHeraldryPosition = new Position(parentPosition.getX(), parents.getNextHeraldryY());
                AncestorPerson parent = Objects.requireNonNullElse(person.getMother(), person.getFather());

                if (parent.hasMinOneParent()) {
                    treeModelService.addLine(parentPosition, nextHeraldryPosition, Relation.DIRECT);
                }
                if (configService.isShowSiblings()) {
                    addSiblings(parentPosition, parent);
                    if (!parent.getYoungerSiblings().isEmpty()) {
                        addLineAboveSpouse(parentPosition, parents.getNextHeraldryY());
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
            motherPosition = heraldryPosition.addXAndY(
                    -extensionConfig.getFatherHorizontalDistance(),
                    -extensionConfig.getFatherVerticalDistance()
            );
            treeModelService.addPerson(motherPosition, child.getMother());
        } else {
            motherPosition = null;
        }

        Position fatherPosition;
        if (child.getFather() != null) {
            int fatherXShift;
            int fatherYShift;
            if (motherPosition == null) {
                fatherXShift = 0;
                fatherYShift = extensionConfig.getFatherVerticalDistance();
            } else {
                fatherXShift = extensionConfig.getMotherHorizontalDistance();
                fatherYShift = extensionConfig.getMotherVerticalDistance();
            }
            fatherPosition = heraldryPosition.addXAndY(fatherXShift, -fatherYShift);
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
            int fathersSiblings = father.getFather().getMaxYoungerSiblings();
            if (fathersSiblings > 0) {
                fatherSiblingsWidth = fathersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }
        int fatherHeraldryX = parentsDto.getHusbandPosition().getX() - extensionConfig.getMotherHorizontalDistance() - fatherSiblingsWidth;

        Position fatherHeraldry = new Position(fatherHeraldryX, parentsDto.getNextHeraldryY());
        generateFathersFamily(fatherHeraldry, father);
        treeModelService.addLine(fatherHeraldry, parentsDto.getHusbandPosition(), Relation.DIRECT);
    }

    private void addMotherFamily(AncestorPerson mother, ParentsDto parentsDto) {
        int motherSiblingsWidth = 0;
        if (configService.isShowSiblings() && mother.getFather() != null) {
            int mothersSiblings = mother.getFather().getMaxOlderSiblings();
            if (mothersSiblings > 0) {
                motherSiblingsWidth = mothersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }

        int motherX = parentsDto.getWifePosition().getX() + extensionConfig.getFatherHorizontalDistance() + motherSiblingsWidth;

        Position motherHeraldry = new Position(motherX, parentsDto.getNextHeraldryY());
        generateFathersFamily(motherHeraldry, mother);
        treeModelService.addLine(motherHeraldry, parentsDto.getWifePosition(), Relation.DIRECT);
    }

    @Override
    public TreeModel generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth) {

        return getTreeModel();
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
                    parentsDto.getWifePosition().getX() + mothersParentHeraldryHorizontalShift,
                    parentsDto.getNextHeraldryY()
            );
            if (mother.hasMinOneParent()) {
                treeModelService.addLine(motherHeraldryPosition, parentsDto.getWifePosition(), Relation.DIRECT);
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
                    parentsDto.getHusbandPosition().getX() - fathersParentHeraldryHorizontalShift,
                    parentsDto.getNextHeraldryY()
            );
            if (father.hasMinOneParent()) {
                treeModelService.addLine(fatherHeraldryPosition, parentsDto.getHusbandPosition(), Relation.DIRECT);
            }
            addAllParents(fatherHeraldryPosition, father);
        }

        return getTreeModel();
    }

    @Override
    public Position addCoupleFamily(Position firstChildPosition, AncestorCouple couple, int descendentsWidth) {
        return null;
    }

}
