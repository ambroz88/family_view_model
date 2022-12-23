package cz.ambrogenea.familyvision.dto.tree;

import cz.ambrogenea.familyvision.domain.DatePlace;
import cz.ambrogenea.familyvision.domain.Personalize;
import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.enums.Sex;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
            ageInYears = (int) (TimeUnit.DAYS.convert(
                    deathDatePlace().getDate().getTime() - birthDatePlace().getDate().getTime(), TimeUnit.MILLISECONDS
            ) / 365);
        }
        return ageInYears;
    }

    private boolean isYoungerThan(int ageLimit) {
        int deathAge = getAge();
        return deathAge < ageLimit && deathAge != -1;
    }

}
