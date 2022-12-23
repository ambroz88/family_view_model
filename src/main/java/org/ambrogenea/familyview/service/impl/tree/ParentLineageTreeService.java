package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalConfigurationService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;

public class ParentLineageTreeService implements TreeService {

    private LineageService lineageService;
    private ConfigurationService configService;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        configService = configuration;
        final String treeName = "Rodové linie rodičů ";
        lineageService = new LineageServiceImpl(rootPerson, treeName, configuration);
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

        ConfigurationExtensionService extensionConfig = new HorizontalConfigurationService(configService);
        int fatherHeraldryX = parentsDto.husbandPosition().x() - extensionConfig.getMotherHorizontalDistance() - fatherSiblingsWidth;

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

        ConfigurationExtensionService extensionConfig = new HorizontalConfigurationService(configService);
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
