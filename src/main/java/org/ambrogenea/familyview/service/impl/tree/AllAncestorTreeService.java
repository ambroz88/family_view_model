package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.PageSetup;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;
import org.ambrogenea.familyview.utils.Tools;

public class AllAncestorTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        LineageService lineageService = new LineageServiceImpl(configuration);
        Position heraldryPosition = lineageService.addClosestFamily(new Position(), rootPerson);

        TreeModel treeModel = lineageService.addAllParents(heraldryPosition, rootPerson);
        treeModel.setTreeName("Vývod z předků " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
