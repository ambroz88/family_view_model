package org.ambrogenea.familyview.dto.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;

public class TreeModel {

    protected final Set<Line> lines;
    protected final Set<Arc> arcs;
    protected final Set<PersonRecord> persons;
    protected final Set<Marriage> marriages;
    protected final Set<ImageModel> images;
    protected final Set<ResidenceDto> residences;
    protected final ArrayList<String> cityRegister;

    private PersonRecord rootPerson;
    private PageSetup pageSetup;

    public TreeModel() {
        persons = new HashSet<>();
        marriages = new HashSet<>();
        lines = new HashSet<>();
        arcs = new HashSet<>();
        images = new HashSet<>();
        residences = new HashSet<>();
        cityRegister = new ArrayList<>();
    }

    public Set<Line> getLines() {
        return lines;
    }

    public Set<Arc> getArcs() {
        return arcs;
    }

    public Set<PersonRecord> getPersons() {
        return persons;
    }

    public void addPersonWithResidence(PersonRecord person, ConfigurationService service) {
        persons.add(person);
        if (service.isShowResidence()) {
            addResidence(person, service);
        }
    }

    public Set<Marriage> getMarriages() {
        return marriages;
    }

    public Set<ImageModel> getImages() {
        return images;
    }

    public PersonRecord getRootPerson() {
        return rootPerson;
    }

    public void setRootPerson(PersonRecord rootPerson) {
        this.rootPerson = rootPerson;
        persons.add(rootPerson);
    }

    public PageSetup getPageSetup() {
        return pageSetup;
    }

    public TreeModel setPageSetup(PageSetup pageSetup) {
        this.pageSetup = pageSetup;
        return this;
    }

    public Set<ResidenceDto> getResidences() {
        return residences;
    }

    public ArrayList<String> getCityRegister() {
        return cityRegister;
    }

    private void addResidence(final PersonRecord person, ConfigurationService configuration) {
        Residence residence;
        int yShift;
        Position position;
        for (int i = 0; i < person.getResidences().size(); i++) {

            residence = person.getResidences().get(i);
            if (!residence.getCity().isEmpty()) {

                if (person.isDirectLineage()) {
                    yShift = -configuration.getAdultImageHeight() / 2 + i * (Spaces.RESIDENCE_SIZE + 5);

                    if (person.getSex().equals(Sex.FEMALE)) {
                        position = person.getPosition().addXAndY(
                                (configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP) / 2, yShift);
                    } else {
                        position = person.getPosition().addXAndY(
                                -(configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP) / 2 - Spaces.RESIDENCE_SIZE, yShift);
                    }
                } else {
                    position = person.getPosition().addXAndY(
                            -(configuration.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) / 2 - Spaces.RESIDENCE_SIZE,
                            -configuration.getSiblingImageHeight() / 2 + i * (Spaces.RESIDENCE_SIZE + 5)
                    );
                }

                residences.add(new ResidenceDto(position, residence));
                addCityToRegister(residence.getCity());
            }
        }
    }

    private void addCityToRegister(String city) {
        if (!cityRegister.contains(city)) {
            cityRegister.add(city);
        }
    }

}
