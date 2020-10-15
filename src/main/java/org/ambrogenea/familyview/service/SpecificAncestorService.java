package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Couple;

public interface SpecificAncestorService extends CommonAncestorService {

    Position addMother(Position childPosition, AncestorPerson mother, String marriageDate);

    Position addFather(Position childPosition, AncestorPerson father);

    Position addSpouse(Position rootPersonPosition, AncestorPerson person);

    Position addAllSpouses(Position rootPersonPosition, AncestorPerson person);

    int generateChildren(Position fatherPosition, Couple couple);

    void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild);

    void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX);

    void addVerticalLineToParents(Position child);

}
