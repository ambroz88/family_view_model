package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.domain.Residence;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record ResidenceDto(
        Position position,
        String city,
        String date,
        int number
) {

    public ResidenceDto(Position position, Residence residence) {
        this(position, residence.getCity(), residence.getDate(), residence.getNumber());
    }

}
