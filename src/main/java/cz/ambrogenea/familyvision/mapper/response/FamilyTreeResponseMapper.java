package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.domain.FamilyTree;
import cz.ambrogenea.familyvision.model.response.FamilyTreeResponse;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FamilyTreeResponseMapper {

    public static FamilyTreeResponse map(FamilyTree domain) {
        return new FamilyTreeResponse(
                domain.getId(),
                domain.getTreeName()
        );
    }

}
