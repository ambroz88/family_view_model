package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;

public interface CommonAncestorService {

    Position addRootSpouses(Position rootPersonPosition, AncestorPerson person);
    void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson);
    int generateChildren(Position fatherPosition, AncestorCouple spouseCouple);
    void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);
    ParentsDto generateParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateHorizontalParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateVerticalParents(Position heraldryPosition, AncestorPerson child);

    void addPerson(Position center, AncestorPerson person);
    void addRootPerson(Position center, AncestorPerson person);
    void addLine(Position start, Position end, Relation lineType);
    void addHeraldry(Position childPosition, String simpleBirthPlace);

    TreeModel getTreeModel();

}
