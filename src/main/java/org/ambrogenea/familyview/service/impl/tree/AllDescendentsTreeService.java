package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;
import org.ambrogenea.familyview.utils.Tools;

public class AllDescendentsTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        LineageService lineageService = new LineageServiceImpl(configuration);
        lineageService.addClosestFamily(new Position(), rootPerson);

        TreeModel treeModel = lineageService.getTreeModel();
        treeModel.setTreeName("Rozrod " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
