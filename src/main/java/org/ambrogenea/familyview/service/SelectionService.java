package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.FamilyData;

public interface SelectionService {

    AncestorPerson select(String personId,  int generationLimit);

    void setFamilyData(FamilyData familyData);

}
