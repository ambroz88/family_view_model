package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.tree.Position;
import cz.ambrogenea.familyvision.model.dto.tree.TreeModel;

public interface LineageService extends CommonAncestorService {
    TreeModel generateFathersFamily(Position heraldryPosition, AncestorPerson person);
    TreeModel generateMotherFamily(Position heraldryPosition, AncestorPerson person);
}
