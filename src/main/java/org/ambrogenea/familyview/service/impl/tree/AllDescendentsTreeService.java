package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.CommonAncestorService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.CommonAncestorServiceImpl;

public class AllDescendentsTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        final String treeName = "Rozrod ";
        CommonAncestorService ancestorService = new CommonAncestorServiceImpl(rootPerson, treeName, configuration);
        ancestorService.addSiblingsAndDescendents(rootPerson);

        return ancestorService.getTreeModel();
    }

}
