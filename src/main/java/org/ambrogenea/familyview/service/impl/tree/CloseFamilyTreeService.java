package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SpecificAncestorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalAncestorService;

public class CloseFamilyTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, Position rootPosition, ConfigurationService configuration) {
        SpecificAncestorService ancestorService = new HorizontalAncestorService(configuration);
        ancestorService.drawPerson(rootPosition, rootPerson);

        if (configuration.isShowParents()) {
            ancestorService.addFather(rootPosition, rootPerson);
            ancestorService.addMother(rootPosition, rootPerson, rootPerson.getParents().getMarriageDate());
            if (configuration.isShowHeraldry() && !rootPerson.getParents().isEmpty()) {
                ancestorService.addHeraldry(rootPosition, rootPerson.getSimpleBirthPlace());
            }
        }

        if (configuration.isShowSpouses()) {
            if (configuration.isShowSiblings()) {
                if (configuration.isShowChildren()) {
//                    int lastSpousePosition = ancestorService.drawAllSpousesWithKids(rootPosition, rootPerson);
//                    ancestorService.drawSiblingsAroundWives(rootPosition, rootPerson, lastSpousePosition);
                    ancestorService.addSpouse(rootPosition, rootPerson);
                    ancestorService.addSiblingsAroundMother(rootPosition, rootPerson);
                    ancestorService.generateChildren(rootPosition, rootPerson.getSpouseCouple());
                } else {
                    Position lastSpouse = ancestorService.addAllSpouses(rootPosition, rootPerson);
                    ancestorService.addSiblingsAroundWives(rootPosition, rootPerson, lastSpouse.getX());
                }
            } else if (configuration.isShowChildren()) {
//                ancestorService.drawAllSpousesWithKids(rootPosition, rootPerson);
                ancestorService.addAllSpouses(rootPosition, rootPerson);
                ancestorService.generateChildren(rootPosition, rootPerson.getSpouseCouple());
            } else {
                ancestorService.addAllSpouses(rootPosition, rootPerson);
            }
        } else if (configuration.isShowSiblings()) {
            ancestorService.drawSiblings(rootPosition, rootPerson);
        }

        return ancestorService.getTreeModel();
    }
}
