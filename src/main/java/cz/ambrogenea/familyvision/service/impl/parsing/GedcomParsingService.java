package cz.ambrogenea.familyvision.service.impl.parsing;

import cz.ambrogenea.familyvision.mapper.command.MarriageCreateCommandMapper;
import cz.ambrogenea.familyvision.mapper.command.PersonCreateCommandMapper;
import cz.ambrogenea.familyvision.service.ParsingService;
import cz.ambrogenea.familyvision.service.Services;
import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.parser.ModelParser;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.io.InputStream;

public class GedcomParsingService implements ParsingService {

    @Override
    public String saveData(InputStream stream) throws IOException, SAXParseException {
        Gedcom gedcom = new ModelParser().parseGedcom(stream);
        Services.reset();
        gedcom.getPeople().forEach(
                person -> Services.person().createPerson(PersonCreateCommandMapper.map(person))
        );
        gedcom.getFamilies().forEach(
                family -> Services.marriage().createMarriage(MarriageCreateCommandMapper.map(family))
        );
        return "It was parsed " + gedcom.getPeople().size() + " people and " + gedcom.getFamilies().size() + " families";
    }

}
