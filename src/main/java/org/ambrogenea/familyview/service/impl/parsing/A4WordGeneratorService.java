package org.ambrogenea.familyview.service.impl.parsing;

import java.util.ArrayList;
import java.util.List;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.parsing.DocumentInputs;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.DocumentGeneratorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.paging.CloseFamilyPageSetup;
import org.ambrogenea.familyview.service.impl.selection.FathersSelectionService;
import org.ambrogenea.familyview.service.impl.tree.CloseFamilyTreeService;
import org.ambrogenea.familyview.utils.Tools;
import org.ambrogenea.familyview.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class A4WordGeneratorService implements DocumentGeneratorService {

    private final ConfigurationService configuration;

    public A4WordGeneratorService(ConfigurationService configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<DocumentInputs> generateFamilies(String personId, FamilyData familyData) {
        List<DocumentInputs> inputs = new ArrayList<>();
        FathersSelectionService fathersSelectionService = new FathersSelectionService(familyData);
        AncestorPerson rootPerson = fathersSelectionService.select(personId, 20);

        if (rootPerson != null) {
            AncestorPerson actualPerson = rootPerson;
            int generations = 0;

            while (actualPerson != null) {
                if (generations < configuration.getGenerationCount()) {
                    inputs.add(generateFamilyTree(actualPerson));

                    actualPerson = actualPerson.getFather();
                    generations++;
                } else {
                    actualPerson = null;
                }
            }
        }

        return inputs;
    }

    @Override
    public XWPFDocument generateDocument(List<DocumentInputs> familyInputs) {
        XWPFDocument doc = WordGenerator.createWordDocument(WordGenerator.FORMAT_A4);
        TreeModel treeModel;

        for (DocumentInputs input : familyInputs) {
            treeModel = input.getTreeModel();
            WordGenerator.setMaxHeight(input.getGenerationsShown());
            WordGenerator.createFamilyPage(doc, treeModel.getTreeName());
            WordGenerator.addImageToPage(doc, input.getImageStream(),
                     treeModel.getPageSetup().getWidth(), treeModel.getPageSetup().getHeight());
        }

        return doc;
    }

    private DocumentInputs generateFamilyTree(AncestorPerson actualPerson) {
        DocumentInputs input = new DocumentInputs();

        CloseFamilyPageSetup setup = new CloseFamilyPageSetup(configuration, actualPerson);
        input.setGenerationsShown(Tools.calculateGenerations(actualPerson, configuration));

        TreeService closeFamilyTreeService = new CloseFamilyTreeService();
        input.setTreeModel(closeFamilyTreeService.generateTreeModel(actualPerson, setup, configuration));
        return input;
    }

}
