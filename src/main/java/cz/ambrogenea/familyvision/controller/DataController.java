package cz.ambrogenea.familyvision.controller;

import cz.ambrogenea.familyvision.service.ParsingService;
import cz.ambrogenea.familyvision.service.impl.parsing.GedcomParsingService;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DataController {

    private final ParsingService parsingService;

    public DataController() {
        parsingService = new GedcomParsingService();
    }

    public String parseData(File gedcomFile) throws IOException, SAXParseException {
        return parsingService.saveData(new FileInputStream(gedcomFile));
    }

}
