package cz.ambrogenea.familyvision.mapper.dto;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.enums.Sex;
import cz.ambrogenea.familyvision.model.dto.AncestorCouple;
import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.MarriageDto;
import cz.ambrogenea.familyvision.service.util.Services;

import java.util.ArrayList;

public class AncestorCoupleMapper {

    public static AncestorCouple mapWithoutKids(Marriage marriage, boolean isDirectLineage) {
        AncestorCouple couple = new AncestorCouple();
        Person husband = Services.person().getByGedcomId(marriage.getHusbandId(), marriage.getFamilyTreeId());
        if (husband != null) {
            final AncestorPerson ancestorHusband = AncestorPersonMapper.map(husband);
            ancestorHusband.setDirectLineage(isDirectLineage);
            couple.setHusband(ancestorHusband);
        }

        Person wife = Services.person().getByGedcomId(marriage.getWifeId(), marriage.getFamilyTreeId());
        if (wife != null) {
            final AncestorPerson ancestorWife = AncestorPersonMapper.map(wife);
            ancestorWife.setDirectLineage(isDirectLineage);
            couple.setWife(ancestorWife);
        }

        couple.setDatePlace(DatePlaceMapper.map(marriage.getWeddingDate(), marriage.getWeddingPlace()));
        couple.setChildrenID(new ArrayList<>(marriage.getChildrenIds()));
        return couple;
    }

    public static AncestorCouple mapWithoutKids(MarriageDto marriage, boolean isDirectLineage) {
        AncestorCouple couple = new AncestorCouple();
        if (marriage.husband() != null) {
            final AncestorPerson ancestorHusband = AncestorPersonMapper.map(marriage.husband());
            ancestorHusband.setDirectLineage(isDirectLineage);
            couple.setHusband(ancestorHusband);
        }

        if (marriage.wife() != null) {
            final AncestorPerson ancestorWife = AncestorPersonMapper.map(marriage.wife());
            ancestorWife.setDirectLineage(isDirectLineage);
            couple.setWife(ancestorWife);
        }

        couple.setDatePlace(marriage.weddingDatePlace());
        couple.setChildrenID(new ArrayList<>(marriage.childrenIds()));

        marriage.childrenIds().forEach(childId -> {
                    Person dbChild = Services.person().getById(childId);
                    if (dbChild.getSex().equals(Sex.FEMALE)) {
                        couple.addGirl();
                    } else {
                        couple.addBoy();
                    }
                }
        );
        return couple;
    }
}
