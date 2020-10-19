package org.ambrogenea.familyview.domain;

import java.util.HashSet;
import java.util.Set;

public class TreeModel {

    protected final Set<Line> lines;
    protected final Set<Arc> arcs;
    protected final Set<PersonRecord> persons;
    protected final Set<Marriage> marriages;
    protected final Set<ImageModel> images;

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

    public void addPerson(PersonRecord person){
        persons.add(person);
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

}
