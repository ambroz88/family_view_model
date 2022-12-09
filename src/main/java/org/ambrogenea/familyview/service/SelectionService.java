package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.domain.FamilyData;

public interface SelectionService {

    AncestorPerson select(String personId,  int generationLimit);

    void setFamilyData(FamilyData familyData);

}
