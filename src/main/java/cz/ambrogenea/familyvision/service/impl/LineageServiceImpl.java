package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.enums.Relation;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.LineageService;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.ParentsDto;

import java.util.Objects;

public class LineageServiceImpl extends CommonAncestorServiceImpl implements LineageService {

    public LineageServiceImpl(AncestorPerson rootPerson, String treeName, ConfigurationService configurationService) {
        super(rootPerson, treeName, configurationService);
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
                addSiblings(parentPosition, parent);

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
                addSiblings(parentPosition, parent);

                generateFathersFamily(nextHeraldryPosition, parent);
            }
        }
        return getTreeModel();
    }

    private ParentsDto generateSwitchedParents(Position heraldryPosition, AncestorPerson child) {
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

}
