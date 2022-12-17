package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;

public class AllDescendentsTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        final String treeName = "Rozrod ";
        LineageService lineageService = new LineageServiceImpl(rootPerson, treeName, configuration);
        lineageService.addClosestFamily(rootPerson);

        TreeModel treeModel = lineageService.getTreeModel();
        return treeModel;
    }

}
