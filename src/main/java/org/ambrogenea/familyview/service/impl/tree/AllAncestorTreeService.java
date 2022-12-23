package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.CommonAncestorService;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.CommonAncestorServiceImpl;
import org.ambrogenea.familyview.service.impl.HorizontalConfigurationService;
import org.ambrogenea.familyview.service.impl.VerticalConfigurationService;

public class AllAncestorTreeService implements TreeService {

    private CommonAncestorService ancestorService;
    private ConfigurationService configService;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        final String treeName = "Vývod z předků ";
        configService = configuration;
        ancestorService = new CommonAncestorServiceImpl(rootPerson, treeName, configuration);
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
                ancestorService.addLine(motherHeraldryPosition, parentsDto.wifePosition(), Relation.DIRECT);
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
                ancestorService.addSiblings(parentsDto.husbandPosition(), father);
                ancestorService.addLine(fatherHeraldryPosition, parentsDto.husbandPosition(), Relation.DIRECT);
            }
            addAllParents(fatherHeraldryPosition, father);
        }
    }

}
