package cz.ambrogenea.familyvision.model.response;

import cz.ambrogenea.familyvision.enums.Sex;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record PersonResponse(
        long id,
        String gedcomId,
        long familyTreeId,
        String firstName,
        String surname,
        Sex sex,
        DatePlaceResponse birthDatePlace,
        DatePlaceResponse deathDatePlace,
        String occupation,
        boolean living,
        Long fatherId,
        Long motherId,
        List<MarriageResponse> marriages,
        List<ResidenceResponse> residenceList
) {
}
