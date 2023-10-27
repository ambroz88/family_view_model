package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.service.util.JsonParser;
import cz.ambrogenea.familyvision.service.impl.DocumentGeneratorService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentController {

    private final DocumentGeneratorService generatorService = new DocumentGeneratorService();

    public List<String> generateTreeModels(Long personId, String format) {
        return generatorService.createWordDocument(personId, format)
                .stream().map(treeModel ->
                        {
                            try {
                                return JsonParser.get().writeValueAsString(treeModel);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).collect(Collectors.toList());
    }

    public void loadImage(InputStream image, String familyName, int imageWidth, int imageHeight){
        generatorService.addImageToPage(familyName, image, imageWidth, imageHeight);
    }

    public void saveDocument(String fileName) throws IOException {
        generatorService.saveDocument(fileName);
    }

}
