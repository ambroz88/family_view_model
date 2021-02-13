package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;

public interface LineageService extends SpecificAncestorService {

    void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);

    void generateFathersFamily(Position childPosition, AncestorPerson person);

    void generateMotherFamily(Position childPosition, AncestorPerson person);

    Position calculateMotherPosition(Position fatherPosition, AncestorPerson rootPerson);

}
