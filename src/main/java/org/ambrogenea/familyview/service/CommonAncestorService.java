package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;

public interface CommonAncestorService {

    Position addRootSpouses(Position rootPersonPosition, AncestorPerson person);
    void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);
    int generateChildren(Position firstChildrenPosition, AncestorCouple spouseCouple, AncestorPerson rootSpouse);
    void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);
    ParentsDto generateParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateHorizontalParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateVerticalParents(Position heraldryPosition, AncestorPerson child);

    void addPerson(Position center, AncestorPerson person);
    TreeModel getTreeModel();

}
