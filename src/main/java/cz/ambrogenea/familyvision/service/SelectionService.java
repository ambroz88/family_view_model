package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.FamilyData;
import cz.ambrogenea.familyvision.dto.AncestorPerson;

public interface SelectionService {

    AncestorPerson select(String personId,  int generationLimit);

    void setFamilyData(FamilyData familyData);

}
