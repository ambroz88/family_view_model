package org.ambrogenea.familyview.model;

import java.util.HashSet;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Scene {

    private final HashSet<Person> people;
    private final HashSet<Marriage> marriages;
    private final HashSet<Line> lines;
    private final HashSet<Arc> arcs;

    public Scene() {
        people = new HashSet<>();
        marriages = new HashSet<>();
        lines = new HashSet<>();
        arcs = new HashSet<>();
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void addMarriage(Marriage wedding) {
        marriages.add(wedding);
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void addArc(Arc arc) {
        arcs.add(arc);
    }
}
