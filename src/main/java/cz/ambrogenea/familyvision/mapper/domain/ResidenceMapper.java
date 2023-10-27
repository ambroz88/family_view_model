package cz.ambrogenea.familyvision.mapper.domain;

import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.model.command.ResidenceCreateCommand;

public class ResidenceMapper {

    public static Residence map(ResidenceCreateCommand createCommand) {
        String city = createCommand.place();
        Residence residence = new Residence();

        int lastSpaceIndex = city.lastIndexOf(" ");
        try {
            int houseNumber = Integer.parseInt(city.substring(lastSpaceIndex + 1));
            residence.setNumber(houseNumber);
        } catch (NumberFormatException e) {
            System.out.println("It wasn't possible to identify house number at residence '" + city + "'.");
        }

        int lastCommaIndex = city.indexOf(" - ");
        if (lastCommaIndex != -1) {
            //removing part of the city e.g Praha - Strahov
            city = city.substring(0, lastCommaIndex).stripTrailing();
        } else if (lastSpaceIndex != -1) {
            city = city.substring(0, lastSpaceIndex);
        }

        residence.setCity(city);
        if (createCommand.date() != null) {
            residence.setDate(createCommand.date());
        }
        return residence;
    }
}
