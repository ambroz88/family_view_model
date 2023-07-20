package cz.ambrogenea.familyvision.dto.tree;

import cz.ambrogenea.familyvision.dto.DatePlaceDto;
import cz.ambrogenea.familyvision.domain.Personalize;
import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.enums.Sex;

import java.util.ArrayList;

public record PersonRecord(
        Position position,
        String id,
        String firstName,
        String surname,
        Sex sex,
        DatePlaceDto birthDatePlace,
        DatePlaceDto deathDatePlace,
        String occupation,
        boolean living,
        boolean directLineage,
        ArrayList<Residence> residences
) implements Personalize {
}
