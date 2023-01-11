package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.enums.LineageType;
import cz.ambrogenea.familyvision.mapper.response.TreeModelResponseMapper;
import cz.ambrogenea.familyvision.model.response.tree.TreeModelResponse;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.impl.selection.AllAncestorsSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.LineageSelectionService;
import cz.ambrogenea.familyvision.service.impl.tree.AllAncestorTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.FatherLineageTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.MotherLineageTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.ParentLineageTreeService;
import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.service.util.JsonParser;

public class TreeGeneratorController {

    private AncestorPerson rootPerson;

    public String generateTree(String gedcomId) {
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
        try {
            TreeModelResponse treeModelResponse = TreeModelResponseMapper.map(treeService.generateTreeModel(rootPerson));
            return JsonParser.get().writeValueAsString(treeModelResponse);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String updateTree() {
        TreeService treeService = switch (Config.treeShape().getLineageType()) {
            case FATHER -> new FatherLineageTreeService();
            case MOTHER -> new MotherLineageTreeService();
            case PARENTS -> new ParentLineageTreeService();
            case ALL -> new AllAncestorTreeService();
        };

        try {
            TreeModelResponse treeModelResponse = TreeModelResponseMapper.map(treeService.generateTreeModel(rootPerson));
            return JsonParser.get().writeValueAsString(treeModelResponse);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
