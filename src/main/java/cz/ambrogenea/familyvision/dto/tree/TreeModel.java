package cz.ambrogenea.familyvision.dto.tree;

import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.utils.Tools;

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

    public PageSetup getPageSetup() {
        return pageMaxCoordinates.getPageSetup();
    }

}
