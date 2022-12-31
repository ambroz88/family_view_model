package cz.ambrogenea.familyvision.service.util;

import cz.ambrogenea.familyvision.service.MarriageService;
import cz.ambrogenea.familyvision.service.PersonService;
import cz.ambrogenea.familyvision.service.impl.MarriageServiceImpl;
import cz.ambrogenea.familyvision.service.impl.PersonServiceImpl;

public class Services {

    private static PersonService personService;
    private static MarriageService marriageService;


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

    public static void reset(){
        personService = null;
        marriageService = null;
    }
}
