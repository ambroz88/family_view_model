package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.MarriageDto;
import cz.ambrogenea.familyvision.mapper.domain.MarriageMapper;
import cz.ambrogenea.familyvision.mapper.dto.MarriageDtoMapper;
import cz.ambrogenea.familyvision.model.command.MarriageCreateCommand;
import cz.ambrogenea.familyvision.repository.MarriageRepository;
import cz.ambrogenea.familyvision.service.MarriageService;
import cz.ambrogenea.familyvision.service.util.Services;

public class MarriageServiceImpl implements MarriageService {

    private final MarriageRepository marriageRepository = new MarriageRepository();

    @Override
    public Marriage createMarriage(MarriageCreateCommand marriageCreateCommand) {
        Marriage marriage = marriageRepository.save(MarriageMapper.map(marriageCreateCommand));
        saveSpousesId(marriageCreateCommand);
        marriage.getChildrenIds().forEach(childId -> {
                    Person child = Services.person().getPersonByGedcomId(childId);
                    if (child != null) {
                        child.setFatherId(marriageCreateCommand.getGedcomHusbandId());
                        child.setMotherId(marriageCreateCommand.getGedcomWifeId());
                        child.setParentId(marriageCreateCommand.getGedcomFamilyId());
                        Services.person().savePerson(child);
                    }
                }
        );
        return marriage;
    }

    private void saveSpousesId(MarriageCreateCommand marriageCreateCommand) {
        Person husband = Services.person().getPersonByGedcomId(marriageCreateCommand.getGedcomHusbandId());
        Person wife = Services.person().getPersonByGedcomId(marriageCreateCommand.getGedcomWifeId());
        if (husband != null && wife != null) {
            husband.getSpouseId().add(marriageCreateCommand.getGedcomFamilyId());
            wife.getSpouseId().add(marriageCreateCommand.getGedcomFamilyId());
            Services.person().savePerson(husband);
            Services.person().savePerson(wife);
        }
    }

    @Override
    public Marriage getMarriageByGedcomId(String gedcomId) {
        return marriageRepository.findByGedcomId(gedcomId).orElse(null);
    }

    @Override
    public MarriageDto getMarriageDtoByGedcomId(String gedcomId) {
        return getMarriageByGedcomId(gedcomId) != null ? MarriageDtoMapper.map(getMarriageByGedcomId(gedcomId)) : null;
    }

}
