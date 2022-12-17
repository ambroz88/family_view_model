package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;

public interface LineageService extends CommonAncestorService {
    Position addClosestFamily(AncestorPerson person);
    TreeModel generateFathersFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel generateMotherFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel generateParentsFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel addAllParents(Position heraldryPosition, AncestorPerson child);
}
