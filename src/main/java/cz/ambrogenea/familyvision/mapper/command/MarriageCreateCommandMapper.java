package cz.ambrogenea.familyvision.mapper.command;

import cz.ambrogenea.familyvision.enums.InfoType;
import cz.ambrogenea.familyvision.model.command.DatePlaceCreateCommand;
import cz.ambrogenea.familyvision.model.command.MarriageCreateCommand;
import org.folg.gedcom.model.ChildRef;
import org.folg.gedcom.model.Family;

import java.util.stream.Collectors;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class MarriageCreateCommandMapper {

    public static MarriageCreateCommand map(Family gedcomFamily) {
        MarriageCreateCommand createCommand = new MarriageCreateCommand();
        createCommand.setGedcomFamilyId(gedcomFamily.getId());
        if (!gedcomFamily.getHusbandRefs().isEmpty()) {
            createCommand.setGedcomHusbandId(gedcomFamily.getHusbandRefs().get(0).getRef());
        }
        if (!gedcomFamily.getWifeRefs().isEmpty()) {
            createCommand.setGedcomWifeId(gedcomFamily.getWifeRefs().get(0).getRef());
        }
        gedcomFamily.getEventsFacts().forEach(fact -> {
                    if (fact.getTag().equals(InfoType.MARRIAGE.toString())) {
                        createCommand.setDatePlace(new DatePlaceCreateCommand(fact.getDate(), fact.getPlace()));
                    }
                }
        );
        createCommand.setChildrenGedcomIds(gedcomFamily.getChildRefs().stream()
                .map(ChildRef::getRef)
                .collect(Collectors.toList())
        );
        return createCommand;
    }

}
