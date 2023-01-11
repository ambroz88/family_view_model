package cz.ambrogenea.familyvision.service;

import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.io.InputStream;

public interface ParsingService {
    String saveData(InputStream stream) throws IOException, SAXParseException;
}
