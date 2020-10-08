package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.domain.Position;

public interface LineageService extends SpecificAncestorService {

    void drawSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);

    void drawFathersFamilyVertical(Position child, AncestorPerson person);

}
