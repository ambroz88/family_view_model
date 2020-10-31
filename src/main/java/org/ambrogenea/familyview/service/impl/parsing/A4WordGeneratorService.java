package org.ambrogenea.familyview.service.impl.parsing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.DocumentGeneratorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.paging.CloseFamilyPageSetup;
import org.ambrogenea.familyview.service.impl.selection.FathersSelectionService;
import org.ambrogenea.familyview.service.impl.tree.CloseFamilyTreeService;
import org.ambrogenea.familyview.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class A4WordGeneratorService implements DocumentGeneratorService {

    private final ConfigurationService configuration;
    private final List<TreeModel> treeModels;
    private final List<Integer> generationCounts;

    public A4WordGeneratorService(ConfigurationService configuration) {
        this.configuration = configuration;
        this.treeModels = new ArrayList<>();
        this.generationCounts = new ArrayList<>();
    }

    @Override
    public List<TreeModel> generateFamilies(String personId, FamilyData familyData) {

        FathersSelectionService fathersSelectionService = new FathersSelectionService(familyData);
        AncestorPerson rootPerson = fathersSelectionService.select(personId, 20);

        if (rootPerson != null) {
            AncestorPerson actualPerson = rootPerson;
            int generations = 0;

            while (actualPerson != null) {
                if (generations < configuration.getGenerationCount()) {
                    treeModels.add(generateFamilyTree(actualPerson));

                    actualPerson = actualPerson.getFather();
                    generations++;
                } else {
                    actualPerson = null;
                }
            }
        }

        return treeModels;
    }

    @Override
    public XWPFDocument generateDocument(List<InputStream> familyImages) {
        XWPFDocument doc = WordGenerator.createWordDocument(WordGenerator.FORMAT_A4);
        TreeModel model;

        for (int i = 0; i < familyImages.size(); i++) {
            model = treeModels.get(i);
            WordGenerator.setMaxHeight(generationCounts.get(i));
            WordGenerator.createFamilyPage(doc, model.getTreeName());
            WordGenerator.addImageToPage(doc, familyImages.get(i), model.getPageSetup().getWidth(), model.getPageSetup().getHeight());
        }

        return doc;
    }

    private TreeModel generateFamilyTree(AncestorPerson actualPerson) {
        CloseFamilyPageSetup setup = new CloseFamilyPageSetup(configuration, actualPerson);
        generationCounts.add(setup.calculateGenerations(actualPerson));

        TreeService closeFamilyTreeService = new CloseFamilyTreeService();
        TreeModel model = closeFamilyTreeService.generateTreeModel(actualPerson, setup, configuration);
        model.setTreeName("Rodina " + actualPerson.getName());
        return model;
    }

}
