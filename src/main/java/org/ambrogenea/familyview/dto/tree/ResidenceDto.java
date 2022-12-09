package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.domain.Residence;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ResidenceDto {

    private final Position position;
    private final String city;
    private final String date;
    private final int number;

    public ResidenceDto(Position position, Residence residence) {
        this.position = position;
        this.city = residence.getCity();
        this.date = residence.getDate();
        this.number = residence.getNumber();
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }

    public Position getPosition() {
        return position;
    }

}
