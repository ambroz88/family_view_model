package cz.ambrogenea.familyvision.mapper.domain;

import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.model.command.ResidenceCreateCommand;

public class ResidenceMapper {

    public static Residence map(ResidenceCreateCommand createCommand) {
        String city = createCommand.place();
        Residence residence = new Residence();

        int lastCommaIndex = city.indexOf("-");
        int lastSpaceIndex = city.lastIndexOf(" ");
        if (lastCommaIndex != -1) {
            //removing part of the city e.g Praha - Strahov
            if (lastSpaceIndex != -1) {
                try {
                    residence.setNumber(Integer.parseInt(city.substring(lastSpaceIndex + 1)));
                } catch (NumberFormatException e) {
                }
            }
            city = city.substring(0, lastCommaIndex).stripTrailing();
        } else if (lastSpaceIndex != -1) {
            try {
                residence.setNumber(Integer.parseInt(city.substring(lastSpaceIndex + 1)));
                city = city.substring(0, lastSpaceIndex);
            } catch (NumberFormatException e) {
            }
        }

        residence.setCity(city);
        if (createCommand.date() != null) {
            residence.setDate(createCommand.date());
        }
        return residence;
    }
}
