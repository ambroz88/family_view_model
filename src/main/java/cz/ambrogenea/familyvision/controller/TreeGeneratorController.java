package cz.ambrogenea.familyvision.controller;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.impl.selection.AllAncestorsSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.FathersSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.MothersSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.ParentsSelectionService;
import cz.ambrogenea.familyvision.service.impl.tree.*;
import cz.ambrogenea.familyvision.service.util.Config;

public class TreeGeneratorController {

    private static AncestorPerson rootPerson;

    public static TreeModel generateTree(String gedcomId) {
        TreeService treeService;
        SelectionService selectionService;
        switch (Config.treeShape().getLineageType()) {
            case FATHER -> {
                selectionService = new FathersSelectionService();
                treeService = new FatherLineageTreeService();
            }
            case MOTHER -> {
                selectionService = new MothersSelectionService();
                treeService = new MotherLineageTreeService();
            }
            case PARENTS -> {
                selectionService = new ParentsSelectionService();
                treeService = new ParentLineageTreeService();
            }
            default -> {
                selectionService = new AllAncestorsSelectionService();
                treeService = new AllAncestorTreeService();
            }
        }

        if (Config.treeShape().getAncestorGenerations() == 0) {
            treeService = new AllDescendentsTreeService();
        }

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

        if (Config.treeShape().getAncestorGenerations() == 0) {
            treeService = new AllDescendentsTreeService();
        }

        return treeService.generateTreeModel(rootPerson);
    }

}
