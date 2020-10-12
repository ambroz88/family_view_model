package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.SpecificAncestorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalAncestorService;
import org.ambrogenea.familyview.service.impl.VerticalAncestorService;

public class CloseFamilyTreeService implements TreeService {

    private final SpecificAncestorService ancestorService;
    private final Configuration configuration;
    private final AncestorPerson rootPerson;

    public CloseFamilyTreeService(Configuration config, AncestorPerson rootPerson) {
        this.configuration = config;
        this.rootPerson = rootPerson;
        if (config.isShowCouplesVertical()) {
            ancestorService = new VerticalAncestorService(configuration);
        } else {
            ancestorService = new HorizontalAncestorService(configuration);
        }
    }

    @Override
    public TreeModel generateTreeModel(Position rootPosition) {
        ancestorService.drawPerson(rootPosition, rootPerson);

        if (configuration.isShowParents()) {
            ancestorService.drawFather(rootPosition, rootPerson);
            ancestorService.drawMother(rootPosition, rootPerson, rootPerson.getParents().getMarriageDate());
            if (configuration.isShowHeraldry() && !rootPerson.getParents().isEmpty()) {
                ancestorService.addHeraldry(rootPosition, rootPerson.getSimpleBirthPlace());
            }
        }

        if (configuration.isShowSpousesFamily()) {
            if (configuration.isShowSiblingsFamily()) {
                if (configuration.isShowChildren()) {
//                    int lastSpousePosition = ancestorService.drawAllSpousesWithKids(rootPosition, rootPerson);
//                    ancestorService.drawSiblingsAroundWives(rootPosition, rootPerson, lastSpousePosition);
                    ancestorService.drawSpouse(rootPosition, rootPerson);
                    ancestorService.drawSiblingsAroundMother(rootPosition, rootPerson);
                    ancestorService.drawChildren(rootPosition, rootPerson.getSpouseCouple());
                } else {
                    Position lastSpouse = ancestorService.drawAllSpouses(rootPosition, rootPerson);
                    ancestorService.drawSiblingsAroundWives(rootPosition, rootPerson, lastSpouse.getX());
                }
            } else if (configuration.isShowChildren()) {
//                ancestorService.drawAllSpousesWithKids(rootPosition, rootPerson);
                ancestorService.drawAllSpouses(rootPosition, rootPerson);
                ancestorService.drawChildren(rootPosition, rootPerson.getSpouseCouple());
            } else {
                ancestorService.drawAllSpouses(rootPosition, rootPerson);
            }
        } else if (configuration.isShowSiblingsFamily()) {
            ancestorService.drawSiblings(rootPosition, rootPerson);
        }

        return ancestorService.getTreeModel();
    }
}
