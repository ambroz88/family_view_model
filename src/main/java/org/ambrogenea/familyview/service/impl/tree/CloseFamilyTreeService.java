package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.SpecificAncestorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalAncestorService;

public class CloseFamilyTreeService implements TreeService {

    private final SpecificAncestorService ancestorService;
    private final Configuration configuration;
    private final AncestorPerson rootPerson;

    public CloseFamilyTreeService(Configuration config, AncestorPerson rootPerson) {
        this.configuration = config;
        this.rootPerson = rootPerson;
        ancestorService = new HorizontalAncestorService(configuration);
    }

    @Override
    public TreeModel generateTreeModel(Position rootPosition) {
        ancestorService.drawPerson(rootPosition, rootPerson);

        if (configuration.isShowParents()) {
            ancestorService.addFather(rootPosition, rootPerson);
            ancestorService.addMother(rootPosition, rootPerson, rootPerson.getParents().getMarriageDate());
            if (configuration.isShowHeraldry() && !rootPerson.getParents().isEmpty()) {
                ancestorService.addHeraldry(rootPosition, rootPerson.getSimpleBirthPlace());
            }
        }

        if (configuration.isShowSpousesFamily()) {
            if (configuration.isShowSiblingsFamily()) {
                if (configuration.isShowChildren()) {
//                    int lastSpousePosition = ancestorService.drawAllSpousesWithKids(rootPosition, rootPerson);
//                    ancestorService.drawSiblingsAroundWives(rootPosition, rootPerson, lastSpousePosition);
                    ancestorService.addSpouse(rootPosition, rootPerson);
                    ancestorService.addSiblingsAroundMother(rootPosition, rootPerson);
                    ancestorService.addChildren(rootPosition, rootPerson.getSpouseCouple());
                } else {
                    Position lastSpouse = ancestorService.addAllSpouses(rootPosition, rootPerson);
                    ancestorService.addSiblingsAroundWives(rootPosition, rootPerson, lastSpouse.getX());
                }
            } else if (configuration.isShowChildren()) {
//                ancestorService.drawAllSpousesWithKids(rootPosition, rootPerson);
                ancestorService.addAllSpouses(rootPosition, rootPerson);
                ancestorService.addChildren(rootPosition, rootPerson.getSpouseCouple());
            } else {
                ancestorService.addAllSpouses(rootPosition, rootPerson);
            }
        } else if (configuration.isShowSiblingsFamily()) {
            ancestorService.drawSiblings(rootPosition, rootPerson);
        }

        return ancestorService.getTreeModel();
    }
}
