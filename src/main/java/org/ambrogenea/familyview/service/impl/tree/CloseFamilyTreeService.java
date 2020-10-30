package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.SpecificAncestorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalAncestorService;

public class CloseFamilyTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        SpecificAncestorService ancestorService = new HorizontalAncestorService(configuration);
        Position rootPosition = pageSetup.getRootPosition();
        ancestorService.addRootPerson(rootPosition, rootPerson);

        if (configuration.isShowParents()) {
            ancestorService.addFather(rootPosition, rootPerson.getFather());
            ancestorService.addMother(rootPosition, rootPerson.getMother(), rootPerson.getParents().getMarriageDate());
            if (configuration.isShowHeraldry() && !rootPerson.getParents().isEmpty()) {
                ancestorService.addHeraldry(rootPosition, rootPerson.getSimpleBirthPlace());
            }
            ancestorService.addVerticalLineToParents(rootPosition);
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
                    Position lastSpouse = ancestorService.addRootSpouses(rootPosition, rootPerson);
                    ancestorService.addSiblingsAroundWives(rootPosition, rootPerson, lastSpouse.getX());
                }
            } else if (configuration.isShowChildren()) {
//                ancestorService.drawAllSpousesWithKids(rootPosition, rootPerson);
                ancestorService.addRootSpouses(rootPosition, rootPerson);
                ancestorService.generateChildren(rootPosition, rootPerson.getSpouseCouple());
            } else {
                ancestorService.addRootSpouses(rootPosition, rootPerson);
            }
        } else if (configuration.isShowSiblings()) {
            ancestorService.addSiblings(rootPosition, rootPerson);
        }

        return ancestorService.getTreeModel().setPageSetup(pageSetup);
    }
}
