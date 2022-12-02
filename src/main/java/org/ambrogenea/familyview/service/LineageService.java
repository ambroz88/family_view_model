package org.ambrogenea.familyview.service;

import java.util.List;

import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;

public interface LineageService extends SpecificAncestorService {

    void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);

    void generateFathersFamily(Position heraldryPosition, AncestorPerson person);

    void generateMotherFamily(Position heraldryPosition, AncestorPerson person);

    void generateParentsFamily(Position heraldryPosition, AncestorPerson person);

    Position generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth);

    void addAllParents(Position heraldryPosition, AncestorPerson child);

    Position addCoupleFamily(Position firstChildPosition, AncestorCouple couple, int descendentsWidth);

}
