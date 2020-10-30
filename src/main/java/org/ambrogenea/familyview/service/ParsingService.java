package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.FamilyData;

import java.io.IOException;
import java.io.InputStream;

public interface ParsingService {

    FamilyData parse(InputStream stream) throws IOException;

}
