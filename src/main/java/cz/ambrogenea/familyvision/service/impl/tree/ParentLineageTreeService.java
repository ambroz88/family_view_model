package cz.ambrogenea.familyvision.service.impl.tree;

import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.ParentsDto;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.enums.Relation;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.LineageService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;

public class ParentLineageTreeService implements TreeService {

    private LineageService lineageService;
    private VisualConfigurationService configService;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson) {
        configService = Config.visual();
        final String treeName = "Rodové linie rodičů ";
        lineageService = new LineageServiceImpl(rootPerson, treeName);
        Position heraldryPosition = lineageService.addSiblingsAndDescendents(rootPerson);

        TreeModel treeModel;
        if (rootPerson.hasBothParents() && rootPerson.getFather().hasMinOneParent() && rootPerson.getMother().hasMinOneParent()) {
            treeModel = generateParentsFamily(heraldryPosition, rootPerson);
        } else if (rootPerson.getMother() != null && rootPerson.getMother().hasMinOneParent()) {
            treeModel = lineageService.generateMotherFamily(heraldryPosition, rootPerson);
        } else if (rootPerson.getFather() != null && rootPerson.getFather().hasMinOneParent()) {
            treeModel = lineageService.generateFathersFamily(heraldryPosition, rootPerson);
        } else {
            lineageService.generateHorizontalParents(heraldryPosition, rootPerson);
            treeModel = lineageService.getTreeModel();
        }

        return treeModel;
    }

    private TreeModel generateParentsFamily(Position heraldryPosition, AncestorPerson person) {
        ParentsDto parentsDto = lineageService.generateHorizontalParents(heraldryPosition, person);
        addFatherFamily(person.getFather(), parentsDto);
        addMotherFamily(person.getMother(), parentsDto);
        return lineageService.getTreeModel();
    }

    private void addFatherFamily(AncestorPerson father, ParentsDto parentsDto) {
        int fatherSiblingsWidth = 0;
        if (father.getFather() != null) {
            father.moveYoungerSiblingsToOlder();

            int fathersSiblings = father.getFather().getMaxYoungerSiblings();
            if (fathersSiblings > 0) {
                fatherSiblingsWidth = fathersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }

        int fatherHeraldryX = parentsDto.husbandPosition().x() - Config.horizontal().getMotherHorizontalDistance() - fatherSiblingsWidth;

        Position fatherHeraldry = new Position(fatherHeraldryX, parentsDto.nextHeraldryY());
        lineageService.generateFathersFamily(fatherHeraldry, father);
        if (father.getOlderSiblings().isEmpty()) {
            lineageService.addLine(fatherHeraldry, parentsDto.husbandPosition(), Relation.DIRECT);
        } else {
            lineageService.addSiblings(new Position(fatherHeraldryX + configService.getAdultImageWidth() / 2, parentsDto.husbandPosition().y()), father);
            lineageService.addLine(parentsDto.husbandPosition(), fatherHeraldry, Relation.DIRECT);
        }
    }

    private void addMotherFamily(AncestorPerson mother, ParentsDto parentsDto) {
        int motherSiblingsWidth = 0;
        if (mother.getFather() != null) {
            mother.moveOlderSiblingsToYounger();

            int mothersSiblings = mother.getFather().getMaxOlderSiblings();
            if (mothersSiblings > 0) {
                motherSiblingsWidth = mothersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }

        ConfigurationExtensionService extensionConfig = Config.horizontal();
        int motherHeraldryX = parentsDto.wifePosition().x() + extensionConfig.getFatherHorizontalDistance() + motherSiblingsWidth;

        Position motherHeraldry = new Position(motherHeraldryX, parentsDto.nextHeraldryY());
        lineageService.generateFathersFamily(motherHeraldry, mother);
        if (mother.getYoungerSiblings().isEmpty()) {
            lineageService.addLine(motherHeraldry, parentsDto.wifePosition(), Relation.DIRECT);
        } else {
            lineageService.addSiblings(new Position(motherHeraldryX - extensionConfig.getSpouseDistance() - configService.getAdultImageWidth() / 2, parentsDto.wifePosition().y()), mother);
            lineageService.addLine(parentsDto.wifePosition(), motherHeraldry, Relation.DIRECT);
        }
    }

}
