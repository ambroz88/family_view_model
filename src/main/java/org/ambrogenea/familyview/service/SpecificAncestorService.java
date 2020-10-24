package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;

public interface SpecificAncestorService extends CommonAncestorService {

    Position addMother(Position childPosition, AncestorPerson mother, String marriageDate);

    Position addFather(Position childPosition, AncestorPerson father);

    void addGrandParents(AncestorPerson child, Position childPosition);

    void addAllParents(Position childPosition, AncestorPerson child);

    Position addSpouse(Position rootPersonPosition, AncestorPerson person);

    void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild);

    void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX);

    void addVerticalLineToParents(Position child);

}
