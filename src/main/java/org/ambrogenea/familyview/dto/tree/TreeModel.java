package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.utils.Tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public record TreeModel(
        String treeName,
        PersonRecord rootPerson,
        Set<PersonRecord> persons,
        Set<Marriage> marriages,
        Set<Line> lines,
        Set<Arc> arcs,
        Set<ImageModel> images,
        Set<ResidenceDto> residences,
        ArrayList<String> cityRegister,
        PageMaxCoordinates pageMaxCoordinates
) {

    public TreeModel(PersonRecord rootPerson, String treeName) {
        this(
                treeName + Tools.getNameIn2ndFall(rootPerson),
                rootPerson,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new ArrayList<>(),
                new PageMaxCoordinates()
        );
//        persons.add(rootPerson);
    }

    public void addPerson(PersonRecord person) {
        pageMaxCoordinates.verifyExtremes(person.position());
        persons.add(person);
    }

    public void addResidence(Position position, Residence residence) {
        residences.add(new ResidenceDto(position, residence));
    }

    public void addCityToRegister(String city) {
        if (!cityRegister.contains(city)) {
            cityRegister.add(city);
        }
    }

    public PageSetup getPageSetup(ConfigurationService config) {
        return pageMaxCoordinates.getPageSetup(config);
    }

}
