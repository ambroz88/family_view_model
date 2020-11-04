package org.ambrogenea.familyview.service.impl.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.parsing.Information;
import org.ambrogenea.familyview.enums.InfoType;
import org.ambrogenea.familyview.service.ParsingService;

public class GedcomParsingService implements ParsingService {

    private InfoType recordType;

    public GedcomParsingService() {
        recordType = InfoType.NONE;
    }

    @Override
    public FamilyData parse(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        ArrayList<String> lines = new ArrayList<>();
        while (reader.ready()) {
            lines.add(reader.readLine());
        }

        return parseGEDCOMLines(lines);
    }

    private FamilyData parseGEDCOMLines(ArrayList<String> rows) {
        Information info;
        Person person = null;
        InfoType lastType = InfoType.INDIVIDUAL;
        Couple couple = new Couple();
        FamilyData familyData = new FamilyData();

        for (String row : rows) {
            info = new Information(row);

            determineRecordType(info);

            if (recordType.equals(InfoType.INDIVIDUAL)) {
                if (info.getCode() == 0) {
                    familyData.addPerson(person);
                    person = new Person(info.getValue());
                } else if (person != null) {
                    person.setInformation(info, lastType);
                }

            } else if (recordType.equals(InfoType.FAMILY)) {
                if (info.getCode() == 0) {
                    familyData.addPerson(person);
                    person = null;
                    couple = familyData.getSpouseMap().get(info.getValue().replace(Information.MARKER, ""));
                } else if (info.getType().equals(InfoType.CHILD)) {
                    String childId = info.getValue().replace(Information.MARKER, "");
                    couple.addChildrenIndex(childId);
                    Person child = familyData.getIndividualMap().get(childId);
                    if (couple.hasHusband()) {
                        child.setFatherId(couple.getHusband().getId());
                    }
                    if (couple.hasWife()) {
                        child.setMotherId(couple.getWife().getId());
                    }

                } else if (info.getType().equals(InfoType.DATE) && lastType.equals(InfoType.MARRIAGE)) {
                    couple.setMarriageDate(info.getValue());
                } else if (info.getType().equals(InfoType.PLACE) && lastType.equals(InfoType.MARRIAGE)) {
                    couple.setMarriagePlace(info.getValue());
                }
            }

            if (!info.getType().equals(InfoType.DATE) && !info.getType().equals(InfoType.ADDRESS) && info.getCode() == 1) {
                lastType = info.getType();
            }
        }

        return familyData;
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

}
