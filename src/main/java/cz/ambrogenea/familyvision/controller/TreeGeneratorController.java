package cz.ambrogenea.familyvision.controller;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.enums.LineageType;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.impl.selection.*;
import cz.ambrogenea.familyvision.service.impl.tree.*;
import cz.ambrogenea.familyvision.service.util.Config;

public class TreeGeneratorController {

    private static AncestorPerson rootPerson;

    public static TreeModel generateTree(String gedcomId) {
        SelectionService selectionService;
        if (Config.treeShape().getLineageType() == LineageType.ALL) {
            selectionService = new AllAncestorsSelectionService();
        } else {
            selectionService = new LineageSelectionService();
        }

        TreeService treeService = switch (Config.treeShape().getLineageType()) {
            case FATHER -> new FatherLineageTreeService();
            case MOTHER -> new MotherLineageTreeService();
            case PARENTS -> new ParentLineageTreeService();
            case ALL -> new AllAncestorTreeService();
        };

        rootPerson = selectionService.select(gedcomId);
        return treeService.generateTreeModel(rootPerson);
    }

    public static TreeModel updateTree() {
        TreeService treeService = switch (Config.treeShape().getLineageType()) {
            case FATHER -> new FatherLineageTreeService();
            case MOTHER -> new MotherLineageTreeService();
            case PARENTS -> new ParentLineageTreeService();
            case ALL -> new AllAncestorTreeService();
        };

        return treeService.generateTreeModel(rootPerson);
    }

}
