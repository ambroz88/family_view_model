package org.ambrogenea.familyview.service;

import java.util.List;

import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;

public interface LineageService extends SpecificAncestorService {

    void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);

    void generateFathersFamily(Position childPosition, AncestorPerson person);

    void generateMotherFamily(Position childPosition, AncestorPerson person);

    Position generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth);

    void addFirstParents(Position childPosition, AncestorPerson child);

    Position addCoupleFamily(Position firstChildPosition, AncestorCouple couple, int descendentsWidth);

    Position calculateMotherPosition(Position fatherPosition, AncestorPerson rootPerson);

}
