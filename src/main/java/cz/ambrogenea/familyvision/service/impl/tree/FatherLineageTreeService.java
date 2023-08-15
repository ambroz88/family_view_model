package cz.ambrogenea.familyvision.service.impl.tree;

import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.tree.Position;
import cz.ambrogenea.familyvision.model.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.LineageService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.util.Config;

public class FatherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson) {
        final String treeName;
        if (Config.treeShape().getAncestorGenerations() == 0) {
            treeName = "Rozrod ";
        } else {
            treeName = "Rodová linie ";
        }
        LineageService lineageService = new LineageServiceImpl(rootPerson, treeName);
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
