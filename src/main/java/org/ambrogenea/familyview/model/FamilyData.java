package org.ambrogenea.familyview.model;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FamilyData {

    private final LinkedHashMap<String, Couple> spouseMap;
    private final LinkedHashMap<String, Person> individualMap;

    public FamilyData() {
        spouseMap = new LinkedHashMap<>();
        individualMap = new LinkedHashMap<>();
    }

    public FamilyData(FamilyData model) {
        this.spouseMap = model.getSpouseMap();
        this.individualMap = model.getIndividualMap();
    }

    public void addPerson(AncestorPerson person) {
        if (person != null) {
            addSpouse(person);
            individualMap.put(person.getId(), person);
        }
    }

    private void addSpouse(AncestorPerson person) {
        if (!person.getSpouseID().isEmpty()) {
            for (String coupleID : person.getSpouseID()) {
                if (spouseMap.containsKey(coupleID)) {
                    Couple partner = spouseMap.get(coupleID);
                    partner.addSpouse(person);
                } else {
                    spouseMap.put(coupleID, new Couple(person));
                }
            }
        }
    }

    public Person getPerson(int index) {
        Optional<Person> person = getIndividualMap().values().stream().skip(index).findFirst();
        return person.orElseThrow();
    }

    public LinkedHashMap<String, Couple> getSpouseMap() {
        return spouseMap;
    }

    public LinkedHashMap<String, Person> getIndividualMap() {
        return individualMap;
    }

}
