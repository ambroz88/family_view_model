package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;

public interface LineageService extends SpecificAncestorService {

    void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);

    void generateFathersFamily(Position childPosition, AncestorPerson person);

    void generateMotherFamily(Position childPosition, AncestorPerson person);

}
