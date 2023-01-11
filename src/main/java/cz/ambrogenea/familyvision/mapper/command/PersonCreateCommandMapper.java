package cz.ambrogenea.familyvision.mapper.command;

import cz.ambrogenea.familyvision.enums.InfoType;
import cz.ambrogenea.familyvision.enums.Sex;
import cz.ambrogenea.familyvision.mapper.util.Verification;
import cz.ambrogenea.familyvision.model.command.DatePlaceCreateCommand;
import cz.ambrogenea.familyvision.model.command.PersonCreateCommand;
import cz.ambrogenea.familyvision.model.command.ResidenceCreateCommand;
import org.folg.gedcom.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonCreateCommandMapper {

    public static PersonCreateCommand map(Person gedcomPerson) {
        PersonCreateCommand createCommand = new PersonCreateCommand();
        List<ResidenceCreateCommand> residences = new ArrayList<>();

        String name = gedcomPerson.getNames().get(0).getValue();
        String firstName = gedcomPerson.getNames().get(0).getGiven();
        String surname = gedcomPerson.getNames().get(0).getSurname();
        createCommand.setLiving(true);

        if (firstName == null && surname == null) {
            if (name.contains("/")) {
                String[] nameParts = name.split("/");
                firstName = nameParts[0].trim();
                if (nameParts.length > 1) {
                    surname = nameParts[1];
                }
            }
        }
        gedcomPerson.getEventsFacts().forEach(fact -> {
                    if (fact.getTag().equals(InfoType.SEX.toString())) {
                        createCommand.setSex(Sex.getSex(fact.getValue()));
                    } else if (fact.getTag().equals(InfoType.BIRTH.toString())) {
                        createCommand.setBirthDatePlace(new DatePlaceCreateCommand(fact.getDate(), fact.getPlace()));
                    } else if (fact.getTag().equals(InfoType.DEATH.toString())) {
                        createCommand.setLiving(false);
                        if (!"DECEASED".equals(fact.getValue())) {
                            createCommand.setDeathDatePlace(new DatePlaceCreateCommand(fact.getDate(), fact.getPlace()));
                        }
                    } else if (fact.getTag().equals(InfoType.OCCUPATION.toString())) {
                        createCommand.setOccupation(fact.getValue());
                    } else if (fact.getTag().equals(InfoType.RESIDENCE.toString())) {
                        if (fact.getAddress() != null && fact.getAddress().getCity() != null) {
                            residences.add(new ResidenceCreateCommand(fact.getAddress().getCity(), fact.getDate()));
                        }
                    }
                }
        );
        createCommand.setGedcomId(Verification.gedcomId(gedcomPerson.getId()));
        createCommand.setFirstName(firstName);
        createCommand.setSurname(surname);
        createCommand.setResidenceList(residences);
        return createCommand;
    }

}
