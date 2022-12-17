package org.ambrogenea.familyview.service.impl.parsing;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.parsing.DocumentInputs;
import org.ambrogenea.familyview.dto.tree.PageSetup;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.DocumentGeneratorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.selection.FathersSelectionService;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;
import org.ambrogenea.familyview.utils.Tools;
import org.ambrogenea.familyview.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.ArrayList;
import java.util.List;

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
