package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;

import java.util.List;

public interface LineageService extends CommonAncestorService {
    Position addClosestFamily(Position rootPosition, AncestorPerson person);
    TreeModel generateFathersFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel generateMotherFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel generateParentsFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel addAllParents(Position heraldryPosition, AncestorPerson child);

    TreeModel generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth);
    Position addCoupleFamily(Position firstChildPosition, AncestorCouple couple, int descendentsWidth);
}
