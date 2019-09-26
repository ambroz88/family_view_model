package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DataModel {

    private final static String VALUE_INDIVIDUAL = "INDI";
    private final static String VALUE_FAMILY = "FAM";
    private final static String TYPE_CHILD = "CHIL";

    private final HashMap<String, Couple> spouseMap;
    private final HashMap<String, Person> individualMap;
    private final ArrayList<Person> recordList;

    private String recordType;

    public DataModel() {
        spouseMap = new HashMap<>();
        individualMap = new HashMap<>();
        recordList = new ArrayList<>();
        recordType = "";
    }

    public DataModel(DataModel model) {
        this.spouseMap = model.getSpouseMap();
        this.individualMap = model.getIndividualMap();
        this.recordList = model.getRecordList();
    }

    public void loadGEDCOMLines(ArrayList<String> rows) {
        Information info;
        Person person = null;
        String lastType = "";
        Couple couple = new Couple();

        for (String row : rows) {
            info = new Information(row);

            determineRecordType(info);

            if (recordType.equals(VALUE_INDIVIDUAL)) {
                if (info.getCode() == 0) {
                    addPerson(person);
                    person = new Person(info.getType());
                } else if (person != null) {
                    person.setInformation(info, lastType);
                }

                if (!info.getType().equals(Person.TYPE_DATE)) {
                    lastType = info.getType();
                }

            } else if (recordType.equals(VALUE_FAMILY)) {
                if (info.getCode() == 0) {
                    addPerson(person);
                    person = null;
                    couple = spouseMap.get(info.getType().replace("@", ""));
                } else if (info.getType().equals(TYPE_CHILD)) {
                    couple.addChildren(info.getValue());
                }
            }
        }
    }

    private void determineRecordType(Information info) {
        if (info.getValue().equals(VALUE_INDIVIDUAL)) {
            if (info.getCode() == 0) {
                recordType = VALUE_INDIVIDUAL;
            }
        } else if (info.getValue().equals(VALUE_FAMILY)) {
            if (info.getCode() == 0) {
                recordType = VALUE_FAMILY;
            }
        }
    }

    private void addPerson(Person person) {
        if (person != null) {
            if (spouseMap.containsKey(person.getSpouseID())) {
                Couple partner = spouseMap.get(person.getSpouseID());
                partner.addSpouse(person);
            } else if (person.getSpouseID() != null) {
                spouseMap.put(person.getSpouseID(), new Couple(person));
            }

            individualMap.put(person.getId(), person);
            recordList.add(person);
        }
    }

    public int getIndividualsCount() {
        if (recordList == null) {
            return 1;
        }
        return recordList.size();
    }

    public Person getPerson(int index) {
        return recordList.get(index);
    }

    public HashMap<String, Couple> getSpouseMap() {
        return spouseMap;
    }

    public HashMap<String, Person> getIndividualMap() {
        return individualMap;
    }

    public ArrayList<Person> getRecordList() {
        return recordList;
    }

}
