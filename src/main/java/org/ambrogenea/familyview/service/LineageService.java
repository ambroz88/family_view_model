package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;

public interface LineageService extends SpecificAncestorService {

    void drawSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);

    void drawFathersFamily(Position childPosition, AncestorPerson person);

    void drawMotherFamily(Position childPosition, AncestorPerson person);

}
