package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.*;
import org.ambrogenea.familyview.service.impl.paging.*;
import org.ambrogenea.familyview.service.impl.selection.*;
import org.ambrogenea.familyview.service.impl.tree.*;
import org.ambrogenea.familyview.utils.Tools;

public class IsolatedTreeCreatorImpl implements IsolatedTreeCreator {

    @Override
    public TreeModel generateAllAncestorCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new AllAncestorsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new AllAncestorPageSetup(configurationService, rootPerson);

        TreeService treeService = new AllAncestorTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, setup, configurationService);
        treeModel.setPageSetup(setup);
        treeModel.setTreeName("Vývod z předků " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    @Override
    public TreeModel generateFatherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new FathersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new FatherLineagePageSetup(configurationService, rootPerson);

        TreeService treeService = new FatherLineageTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, setup, configurationService);
        treeModel.setPageSetup(setup);
        treeModel.setTreeName("Rodová linie " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    @Override
    public TreeModel generateMotherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new MothersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new MotherLineagePageSetup(configurationService, rootPerson);

        TreeService treeService = new MotherLineageTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, setup, configurationService);
        treeModel.setPageSetup(setup);
        treeModel.setTreeName("Rodová linie matky " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    @Override
    public TreeModel generateParentLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new ParentsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new ParentLineagePageSetup(configurationService, rootPerson);

        TreeService treeService = new ParentLineageTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, setup, configurationService);
        treeModel.setPageSetup(setup);
        treeModel.setTreeName("Rodové linie rodičů " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    @Override
    public TreeModel generateCloseFamilyCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new CloseFamilySelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new CloseFamilyPageSetup(configurationService, rootPerson);

        TreeService treeService = new CloseFamilyTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, setup, configurationService);
        treeModel.setPageSetup(setup);
        treeModel.setTreeName("Rodina " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
