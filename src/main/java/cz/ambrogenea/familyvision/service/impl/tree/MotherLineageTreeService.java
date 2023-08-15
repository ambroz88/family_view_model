package cz.ambrogenea.familyvision.service.impl.tree;

import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.tree.Position;
import cz.ambrogenea.familyvision.model.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.LineageService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.util.Config;

public class MotherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson) {
        final String treeName;
        if (Config.treeShape().getAncestorGenerations() == 0) {
            treeName = "Rozrod ";
        } else {
            treeName = "Rodová linie matky ";
        }
        LineageService lineageService = new LineageServiceImpl(rootPerson, treeName);
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
