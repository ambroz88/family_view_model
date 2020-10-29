package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.service.*;
import org.ambrogenea.familyview.service.impl.paging.*;
import org.ambrogenea.familyview.service.impl.selection.*;
import org.ambrogenea.familyview.service.impl.tree.*;

public class IsolatedTreeCreatorImpl implements IsolatedTreeCreator {

    @Override
    public TreeModel generateAllAncestorCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new AllAncestorsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new AllAncestorPageSetup(configurationService, rootPerson);

        TreeService treeService = new AllAncestorTreeService();
        return treeService.generateTreeModel(rootPerson, setup, configurationService);
    }

    @Override
    public TreeModel generateFatherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new FathersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new FatherLineagePageSetup(configurationService, rootPerson);

        TreeService treeService = new FatherLineageTreeService();
        return treeService.generateTreeModel(rootPerson, setup, configurationService);
    }

    @Override
    public TreeModel generateMotherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new MothersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new MotherLineagePageSetup(configurationService, rootPerson);

        TreeService treeService = new MotherLineageTreeService();
        return treeService.generateTreeModel(rootPerson, setup, configurationService);
    }

    @Override
    public TreeModel generateParentLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new ParentsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new ParentLineagePageSetup(configurationService, rootPerson);

        TreeService treeService = new ParentLineageTreeService();
        return treeService.generateTreeModel(rootPerson, setup, configurationService);
    }

    @Override
    public TreeModel generateCloseFamilyCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new CloseFamilySelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());
        PageSetup setup = new CloseFamilyPageSetup(configurationService, rootPerson);

        TreeService treeService = new CloseFamilyTreeService();
        return treeService.generateTreeModel(rootPerson, setup, configurationService);
    }

}
