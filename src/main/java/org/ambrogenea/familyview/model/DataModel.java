package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.TreeMap;

import org.ambrogenea.familyview.model.enums.InfoType;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DataModel {

    private final TreeMap<String, Couple> spouseMap;
    private final TreeMap<String, Person> individualMap;
    private final ArrayList<Person> recordList;

    private InfoType recordType;

    public DataModel() {
        spouseMap = new TreeMap<>();
        individualMap = new TreeMap<>();
        recordList = new ArrayList<>();
        recordType = InfoType.NONE;
    }

    public DataModel(DataModel model) {
        this.spouseMap = model.getSpouseMap();
        this.individualMap = model.getIndividualMap();
        this.recordList = model.getRecordList();
    }

    public void loadGEDCOMLines(ArrayList<String> rows) {
        Information info;
        Person person = null;
        InfoType lastType = InfoType.INDIVIDUAL;
        Couple couple = new Couple();

        for (String row : rows) {
            info = new Information(row);

            determineRecordType(info);

            if (recordType.equals(InfoType.INDIVIDUAL)) {
                if (info.getCode() == 0) {
                    addPerson(person);
                    person = new Person(info.getValue());
                } else if (person != null) {
                    person.setInformation(info, lastType);
                }

            } else if (recordType.equals(InfoType.FAMILY)) {
                if (info.getCode() == 0) {
                    addPerson(person);
                    person = null;
                    couple = spouseMap.get(info.getValue().replace(Information.MARKER, ""));
                } else if (info.getType().equals(InfoType.CHILD)) {
                    couple.addChildrenIndex(info.getValue().replace(Information.MARKER, ""));
                } else if (info.getType().equals(InfoType.DATE) && lastType.equals(InfoType.MARRIAGE)) {
                    couple.setMarriageDate(info.getValue());
                } else if (info.getType().equals(InfoType.PLACE) && lastType.equals(InfoType.MARRIAGE)) {
                    couple.setMarriagePlace(info.getValue());
                }
            }

            if (!info.getType().equals(InfoType.DATE) && !info.getType().equals(InfoType.ADDRESS)) {
                lastType = info.getType();
            }
        }
    }

    private void determineRecordType(Information info) {
        if (info.getType().equals(InfoType.INDIVIDUAL)) {
            if (info.getCode() == 0) {
                recordType = InfoType.INDIVIDUAL;
            }
        } else if (info.getType().equals(InfoType.FAMILY)) {
            if (info.getCode() == 0) {
                recordType = InfoType.FAMILY;
            }
        }
    }

    private void addPerson(Person person) {
        if (person != null) {
            person.setDbPosition(recordList.size());
            addSpouse(person);
            individualMap.put(person.getTreeID(), person);
            recordList.add(person);
        }
    }

    private void addSpouse(Person person) {
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

    public int getIndividualsCount() {
        if (recordList == null) {
            return 1;
        }
        return recordList.size();
    }

    public Person getPerson(int index) {
        return recordList.get(index);
    }

    public TreeMap<String, Couple> getSpouseMap() {
        return spouseMap;
    }

    public TreeMap<String, Person> getIndividualMap() {
        return individualMap;
    }

    public ArrayList<Person> getRecordList() {
        return recordList;
    }

}
