package cz.ambrogenea.familyvision.mapper.command;

import cz.ambrogenea.familyvision.model.command.FamilyTreeCommand;
import cz.ambrogenea.familyvision.model.request.FamilyTreeRequest;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FamilyTreeCommandMapper {

    public static FamilyTreeCommand map(FamilyTreeRequest familyTreeRequest) {
        return new FamilyTreeCommand(familyTreeRequest.treeName());
    }

}
