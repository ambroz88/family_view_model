package cz.ambrogenea.familyvision.service.impl.tree;

import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.ParentsDto;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.enums.Relation;
import cz.ambrogenea.familyvision.service.CommonAncestorService;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;

public class AllAncestorTreeService implements TreeService {

    private CommonAncestorService ancestorService;
    private VisualConfigurationService configService;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson) {
        final String treeName = "Vývod z předků ";
        configService = Config.visual();
        ancestorService = new CommonAncestorServiceImpl(rootPerson, treeName);
        Position heraldryPosition = ancestorService.addSiblingsAndDescendents(rootPerson);

        addAllParents(heraldryPosition, rootPerson);
        return ancestorService.getTreeModel();
    }

    private void addAllParents(Position heraldryPosition, AncestorPerson child) {
        ParentsDto parentsDto;
        if (child.getAncestorGenerations() == 1) {
            parentsDto = ancestorService.generateVerticalParents(heraldryPosition, child);
        } else {
            parentsDto = ancestorService.generateHorizontalParents(heraldryPosition, child);
        }
        ConfigurationExtensionService horizontalConfig = Config.horizontal();
        ConfigurationExtensionService verticalConfig = Config.vertical();

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
                mother.moveOlderSiblingsToYounger();
                ancestorService.addSiblings(new Position(motherHeraldryPosition.x() - Config.horizontal().getSpouseDistance() - configService.getAdultImageWidth() / 2, parentsDto.wifePosition().y()), mother);
                if (mother.getYoungerSiblings().isEmpty()) {
                    ancestorService.addLine(motherHeraldryPosition, parentsDto.wifePosition(), Relation.DIRECT);
                } else {
                    ancestorService.addLine(parentsDto.wifePosition(), motherHeraldryPosition, Relation.DIRECT);
                }

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
                ancestorService.addSiblings(new Position(fatherHeraldryPosition.x() + configService.getAdultImageWidth() / 2, parentsDto.husbandPosition().y()), father);
                if (father.getOlderSiblings().isEmpty()) {
                    ancestorService.addLine(fatherHeraldryPosition, parentsDto.husbandPosition(), Relation.DIRECT);
                } else {
                    ancestorService.addLine(parentsDto.husbandPosition(), fatherHeraldryPosition, Relation.DIRECT);
                }
            }
            addAllParents(fatherHeraldryPosition, father);
        }
    }

}
