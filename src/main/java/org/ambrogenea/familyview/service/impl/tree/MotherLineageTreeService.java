package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;
import org.ambrogenea.familyview.utils.Tools;

public class MotherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        LineageService lineageService;
        if (configuration.isShowCouplesVertical()) {
            lineageService = new VerticalLineageService(configuration);
        } else {
            lineageService = new HorizontalLineageService(configuration);
        }

        Position rootPosition = pageSetup.getRootPosition();
        lineageService.addRootPerson(rootPosition, rootPerson);
        lineageService.generateSpouseAndSiblings(rootPosition, rootPerson);
        lineageService.generateChildren(rootPosition, rootPerson.getSpouseCouple());

        if (rootPerson.getMother() != null) {
            lineageService.generateMotherFamily(rootPosition, rootPerson);
        }

        TreeModel treeModel = lineageService.getTreeModel();
        treeModel.setPageSetup(pageSetup);
        treeModel.setTreeName("Rodová linie matky " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
