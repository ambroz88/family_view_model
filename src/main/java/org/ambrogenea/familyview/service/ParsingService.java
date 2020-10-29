package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.model.FamilyData;

import java.io.IOException;
import java.io.InputStream;

public interface ParsingService {

    FamilyData parse(InputStream stream) throws IOException;

}
