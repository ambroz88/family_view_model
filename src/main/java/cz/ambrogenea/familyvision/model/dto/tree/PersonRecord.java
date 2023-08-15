package cz.ambrogenea.familyvision.model.dto.tree;

import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.enums.Sex;
import cz.ambrogenea.familyvision.model.dto.DatePlaceDto;

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
