package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;
import org.ambrogenea.familyview.utils.Tools;

public class MotherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        LineageService lineageService = new LineageServiceImpl(configuration);
        Position heraldryPosition = lineageService.addClosestFamily(new Position(), rootPerson);

        TreeModel treeModel;
        if (rootPerson.getMother() != null) {
            treeModel = lineageService.generateMotherFamily(heraldryPosition, rootPerson);
        } else {
            treeModel = new TreeModel();
        }

        treeModel.setTreeName("Rodová linie matky " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
