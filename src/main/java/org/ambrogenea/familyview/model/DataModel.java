package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DataModel {

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
        AncestorPerson person = null;
        String lastType = "";
        Couple couple = new Couple();

        for (String row : rows) {
            info = new Information(row);

            determineRecordType(info);

            if (recordType.equals(Information.VALUE_INDIVIDUAL)) {
                if (info.getCode() == 0) {
                    addPerson(person);
                    person = new AncestorPerson(info.getType());
                } else if (person != null) {
                    person.setInformation(info, lastType);
                }

            } else if (recordType.equals(Information.VALUE_FAMILY)) {
                if (info.getCode() == 0) {
                    addPerson(person);
                    person = null;
                    couple = spouseMap.get(info.getType().replace(Information.MARKER, ""));
                } else if (info.getType().equals(Information.TYPE_CHILD)) {
                    couple.addChildrenIndex(info.getValue().replace(Information.MARKER, ""));
                } else if (info.getType().equals(Information.TYPE_DATE) && lastType.equals(Information.TYPE_MARRIAGE)) {
                    couple.setMarriageDate(info.getValue());
                } else if (info.getType().equals(Information.TYPE_PLACE) && lastType.equals(Information.TYPE_MARRIAGE)) {
                    couple.setMarriagePlace(info.getValue());
                }
            }

            if (!info.getType().equals(Information.TYPE_DATE)) {
                lastType = info.getType();
            }
        }
    }

    private void determineRecordType(Information info) {
        if (info.getValue().equals(Information.VALUE_INDIVIDUAL)) {
            if (info.getCode() == 0) {
                recordType = Information.VALUE_INDIVIDUAL;
            }
        } else if (info.getValue().equals(Information.VALUE_FAMILY)) {
            if (info.getCode() == 0) {
                recordType = Information.VALUE_FAMILY;
            }
        }
    }

    private void addPerson(AncestorPerson person) {
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
