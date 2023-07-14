package cz.ambrogenea.familyvision.service.impl.parsing;

import cz.ambrogenea.familyvision.mapper.command.MarriageCreateCommandMapper;
import cz.ambrogenea.familyvision.mapper.command.PersonCreateCommandMapper;
import cz.ambrogenea.familyvision.service.ParsingService;
import cz.ambrogenea.familyvision.service.util.Services;
import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.parser.ModelParser;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.io.InputStream;

public class GedcomParsingService implements ParsingService {

    @Override
    public String saveData(InputStream stream, Long familyTreeId) throws IOException, SAXParseException {
        Gedcom gedcom = new ModelParser().parseGedcom(stream);
        gedcom.getPeople().forEach(
                person -> Services.person().createPerson(PersonCreateCommandMapper.map(person, familyTreeId))
        );
        gedcom.getFamilies().forEach(
                family -> Services.marriage().createMarriage(MarriageCreateCommandMapper.map(family, familyTreeId))
        );
        return "It was parsed " + gedcom.getPeople().size() + " people and " + gedcom.getFamilies().size() + " families";
    }

}
