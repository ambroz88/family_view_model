package cz.ambrogenea.familyvision.service.impl.parsing;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.parsing.DocumentInputs;
import cz.ambrogenea.familyvision.dto.tree.PageSetup;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.DocumentGeneratorService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.impl.selection.FathersSelectionService;
import cz.ambrogenea.familyvision.service.impl.tree.FatherLineageTreeService;
import cz.ambrogenea.familyvision.utils.Tools;
import cz.ambrogenea.familyvision.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.ArrayList;
import java.util.List;

public class A4WordGeneratorService implements DocumentGeneratorService {

    private final ConfigurationService configuration;

    public A4WordGeneratorService(ConfigurationService configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<DocumentInputs> generateFamilies(String personId) {
        List<DocumentInputs> inputs = new ArrayList<>();
        FathersSelectionService fathersSelectionService = new FathersSelectionService(configuration);
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
            PageSetup setup = treeModel.pageMaxCoordinates().getPageSetup(configuration);
            WordGenerator.setMaxHeight(input.getGenerationsShown());
            WordGenerator.createFamilyPage(doc, treeModel.treeName());
            WordGenerator.addImageToPage(doc, input.getImageStream(),
                    setup.pictureWidth(), setup.pictureHeight());
        }

        return doc;
    }

    private DocumentInputs generateFamilyTree(AncestorPerson actualPerson) {
        DocumentInputs input = new DocumentInputs();
        input.setGenerationsShown(Tools.calculateGenerations(actualPerson, configuration));

        TreeService closeFamilyTreeService = new FatherLineageTreeService();
        input.setTreeModel(closeFamilyTreeService.generateTreeModel(actualPerson, configuration));
        return input;
    }

}
