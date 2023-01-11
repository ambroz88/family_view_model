package cz.ambrogenea.familyvision.model.response;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record MarriageResponse(
        long id,
        String gedcomFamilyId,
        long familyTreeId,
        Long husbandId,
        Long wifeId,
        DatePlaceResponse weddingDatePlace,
        List<Long> childrenIds
) {
}
