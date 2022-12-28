package cz.ambrogenea.familyvision.service.impl.tree;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.CommonAncestorService;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.TreeService;

public class AllDescendentsTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        final String treeName = "Rozrod ";
        CommonAncestorService ancestorService = new CommonAncestorServiceImpl(rootPerson, treeName, configuration);
        ancestorService.addSiblingsAndDescendents(rootPerson);

        return ancestorService.getTreeModel();
    }

}
