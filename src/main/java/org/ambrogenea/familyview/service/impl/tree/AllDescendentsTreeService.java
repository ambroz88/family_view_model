package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;
import org.ambrogenea.familyview.utils.Tools;

public class AllDescendentsTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        LineageService specificAncestorService;
        if (configuration.isShowCouplesVertical()) {
            specificAncestorService = new VerticalLineageService(configuration);
        } else {
            specificAncestorService = new HorizontalLineageService(configuration);
        }

        Position rootPosition = pageSetup.getRootPosition();
        specificAncestorService.addRootPerson(rootPosition, rootPerson);
        specificAncestorService.addRootSpouses(rootPosition, rootPerson);

        if (rootPerson.getSpouseCouple() != null && rootPerson.getSpouse() == null) {
            specificAncestorService.addLine(rootPosition,
                    rootPosition.addXAndY(0, (configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
        } else if (rootPerson.getSpouseCouple() != null && !rootPerson.getSpouseCouple().getChildren().isEmpty()) {
            specificAncestorService.addLine(rootPosition.addXAndY(configuration.getSpouseDistance() / 2, 0),
                    rootPosition.addXAndY(configuration.getSpouseDistance() / 2, (configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
        }

        specificAncestorService.generateAllDescendents(new Position(Spaces.SIBLINGS_GAP, rootPosition.getY()),
                rootPerson.getSpouseCouples(), pageSetup.getWidth() - Spaces.SIBLINGS_GAP
        );

        TreeModel treeModel = specificAncestorService.getTreeModel();
        treeModel.setPageSetup(pageSetup);
        treeModel.setTreeName("Rozrod " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
