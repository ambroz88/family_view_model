package cz.ambrogenea.familyvision.service.impl.tree;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.LineageService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.impl.LineageServiceImpl;

public class FatherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        final String treeName = "Rodová linie ";
        LineageService lineageService = new LineageServiceImpl(rootPerson, treeName, configuration);
        Position heraldryPosition = lineageService.addSiblingsAndDescendents(rootPerson);

        TreeModel treeModel;
        if (rootPerson.getFather() != null) {
            treeModel = lineageService.generateFathersFamily(heraldryPosition, rootPerson);
        } else {
            lineageService.generateHorizontalParents(heraldryPosition, rootPerson);
            treeModel = lineageService.getTreeModel();
        }

        return treeModel;
    }

}
