package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.domain.DatePlace;
import org.ambrogenea.familyview.domain.Personalize;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.enums.Sex;

import java.time.Period;
import java.util.ArrayList;

public record PersonRecord(
        Position position,
        String id,
        String firstName,
        String surname,
        Sex sex,
        DatePlace birthDatePlace,
        DatePlace deathDatePlace,
        String occupation,
        boolean living,
        boolean directLineage,
        ArrayList<Residence> residences
) implements Personalize {

    @Override
    public String getFirstName() {
        return firstName();
    }

    @Override
    public String getSurname() {
        return surname();
    }

    @Override
    public String getName() {
        if (getFirstName().isEmpty()) {
            return getSurname();
        } else if (getSurname().isEmpty()) {
            return getFirstName();
        }
        return getFirstName() + " " + getSurname();
    }

    @Override
    public Sex getSex() {
        return sex();
    }

    public boolean isChild() {
        return isYoungerThan(8);
    }

    public boolean isTeenager() {
        return isYoungerThan(18);
    }

    public int getAge() {
        int ageInYears = -1;
        if (birthDatePlace().getDate() != null && deathDatePlace().getDate() != null) {
            ageInYears = Period.between(birthDatePlace().getDate(),
                    deathDatePlace().getDate()).getYears();
        }
        return ageInYears;
    }

    private boolean isYoungerThan(int ageLimit) {
        int deathAge = getAge();
        return deathAge < ageLimit && deathAge != -1;
    }

}
