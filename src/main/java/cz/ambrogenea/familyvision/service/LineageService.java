package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;

public interface LineageService extends CommonAncestorService {
    TreeModel generateFathersFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel generateMotherFamily(Position heraldryPosition, AncestorPerson person);
}
