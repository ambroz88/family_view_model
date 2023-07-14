package cz.ambrogenea.familyvision.mapper.domain;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.model.command.MarriageCreateCommand;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class MarriageMapper {

    public static Marriage map(MarriageCreateCommand createCommand) {
        Marriage marriage = new Marriage();
        marriage.setGedcomFamilyId(createCommand.getGedcomFamilyId());
        marriage.setFamilyTreeId(createCommand.getFamilyTreeId());
        marriage.setHusbandId(createCommand.getGedcomHusbandId());
        marriage.setWifeId(createCommand.getGedcomWifeId());
        if (DatePlaceMapper.isValidDatePlace(createCommand.getDatePlace())) {
            marriage.setWeddingDatePlace(DatePlaceMapper.map(createCommand.getDatePlace()));
        }
        marriage.setChildrenIds(createCommand.getChildrenGedcomIds());
        return marriage;
    }

}
