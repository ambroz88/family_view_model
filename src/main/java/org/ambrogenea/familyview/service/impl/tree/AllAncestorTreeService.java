package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;

public class AllAncestorTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        final String treeName = "Vývod z předků ";
        LineageService lineageService = new LineageServiceImpl(rootPerson, treeName, configuration);
        Position heraldryPosition = lineageService.addClosestFamily(rootPerson);

        TreeModel treeModel = lineageService.addAllParents(heraldryPosition, rootPerson);
        return treeModel;
    }

}
