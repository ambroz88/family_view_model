package org.ambrogenea.familyview.dto.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.service.ConfigurationService;

public class TreeModel {

    protected final Set<Line> lines;
    protected final Set<Arc> arcs;
    protected final Set<PersonRecord> persons;
    protected final Set<Marriage> marriages;
    protected final Set<ImageModel> images;
    protected final Set<ResidenceDto> residences;
    protected final ArrayList<String> cityRegister;

    private String treeName;
    private PersonRecord rootPerson;
    private PageSetup pageSetup;

    public TreeModel() {
        treeName = "";
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

    public void addPerson(PersonRecord person) {
        persons.add(person);
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

    public void setPageSetup(PageSetup pageSetup) {
        this.pageSetup = pageSetup;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public Set<ResidenceDto> getResidences() {
        return residences;
    }

    public ArrayList<String> getCityRegister() {
        return cityRegister;
    }

    public void addResidence(Position position, Residence residence) {
        residences.add(new ResidenceDto(position, residence));
    }

    public void addCityToRegister(String city) {
        if (!cityRegister.contains(city)) {
            cityRegister.add(city);
        }
    }

}
