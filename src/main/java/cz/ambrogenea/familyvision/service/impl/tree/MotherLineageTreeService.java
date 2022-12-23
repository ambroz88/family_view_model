package cz.ambrogenea.familyvision.service.impl.tree;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.LineageService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.impl.LineageServiceImpl;

public class MotherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        final String treeName = "Rodová linie matky ";
        LineageService lineageService = new LineageServiceImpl(rootPerson, treeName, configuration);
        Position heraldryPosition = lineageService.addSiblingsAndDescendents(rootPerson);

        TreeModel treeModel;
        if (rootPerson.getMother() != null) {
            treeModel = lineageService.generateMotherFamily(heraldryPosition, rootPerson);
        } else {
            lineageService.generateHorizontalParents(heraldryPosition, rootPerson);
            treeModel = lineageService.getTreeModel();
        }

        return treeModel;
    }

}
