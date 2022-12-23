package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.FamilyData;

import java.io.IOException;
import java.io.InputStream;

public interface ParsingService {

    FamilyData parse(InputStream stream) throws IOException;

}
