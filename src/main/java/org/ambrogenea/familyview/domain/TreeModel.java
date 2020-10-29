package org.ambrogenea.familyview.domain;

import org.ambrogenea.familyview.service.PageSetup;

import java.util.HashSet;
import java.util.Set;

public class TreeModel {

    protected final Set<Line> lines;
    protected final Set<Arc> arcs;
    protected final Set<PersonRecord> persons;
    protected final Set<Marriage> marriages;
    protected final Set<ImageModel> images;
    private PersonRecord rootPerson;
    private PageSetup pageSetup;

    public TreeModel() {
        persons = new HashSet<>();
        marriages = new HashSet<>();
        lines = new HashSet<>();
        arcs = new HashSet<>();
        images = new HashSet<>();
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

}
