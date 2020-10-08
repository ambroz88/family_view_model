package org.ambrogenea.familyview.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class TreeModel {

    protected final Set<Line> lines;
    protected final Set<Arc> arcs;
    protected final Set<PersonRecord> persons;
    protected final Set<Marriage> marriages;
    protected final Set<ImageModel> images;
    protected final TreeMap<String, Color> cityRegister;
    protected final ArrayList<ResidenceModel> residences;

    public TreeModel() {
        persons = new HashSet<>();
        marriages = new HashSet<>();
        lines = new HashSet<>();
        arcs = new HashSet<>();
        images = new HashSet<>();
        residences = new ArrayList<>();
        cityRegister = new TreeMap<>();
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

    public TreeMap<String, Color> getCityRegister() {
        return cityRegister;
    }

    public ArrayList<ResidenceModel> getResidences() {
        return residences;
    }
}
