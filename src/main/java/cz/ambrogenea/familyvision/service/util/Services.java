package cz.ambrogenea.familyvision.service.util;

import cz.ambrogenea.familyvision.service.CityService;
import cz.ambrogenea.familyvision.service.FamilyTreeService;
import cz.ambrogenea.familyvision.service.MarriageService;
import cz.ambrogenea.familyvision.service.PersonService;
import cz.ambrogenea.familyvision.service.impl.CityServiceImpl;
import cz.ambrogenea.familyvision.service.impl.FamilyTreeServiceImpl;
import cz.ambrogenea.familyvision.service.impl.MarriageServiceImpl;
import cz.ambrogenea.familyvision.service.impl.PersonServiceImpl;

public class Services {

    private static PersonService personService;
    private static MarriageService marriageService;
    private static FamilyTreeService familyTreeService;
    private static CityService cityService;


    public static PersonService person() {
        if (personService == null) {
            personService = new PersonServiceImpl();
        }
        return personService;
    }

    public static MarriageService marriage() {
        if (marriageService == null) {
            marriageService = new MarriageServiceImpl();
        }
        return marriageService;
    }

    public static FamilyTreeService familyTree() {
        if (familyTreeService == null) {
            familyTreeService = new FamilyTreeServiceImpl();
        }
        return familyTreeService;
    }

    public static CityService city() {
        if (cityService == null) {
            cityService = new CityServiceImpl();
        }
        return cityService;
    }

}
