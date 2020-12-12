package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;
import org.ambrogenea.familyview.utils.Tools;

public class AllAncestorTreeService implements TreeService {

    private LineageService specificAncestorService;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        if (configuration.isShowCouplesVertical()) {
            specificAncestorService = new VerticalLineageService(configuration);
        } else {
            specificAncestorService = new HorizontalLineageService(configuration);
        }

        specificAncestorService.addFirstParents(pageSetup.getRootPosition(), rootPerson);

        Position rootPosition = pageSetup.getRootPosition();
        specificAncestorService.addRootPerson(rootPosition, rootPerson);
        specificAncestorService.generateSpouseAndSiblings(rootPosition, rootPerson);
        specificAncestorService.generateChildren(rootPosition, rootPerson.getSpouseCouple());

        TreeModel treeModel = specificAncestorService.getTreeModel();
        treeModel.setPageSetup(pageSetup);
        treeModel.setTreeName("Vývod z předků " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
