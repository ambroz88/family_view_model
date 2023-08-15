package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.mapper.domain.MarriageMapper;
import cz.ambrogenea.familyvision.mapper.dto.MarriageDtoMapper;
import cz.ambrogenea.familyvision.model.command.MarriageCreateCommand;
import cz.ambrogenea.familyvision.model.dto.MarriageDto;
import cz.ambrogenea.familyvision.repository.MarriageRepository;
import cz.ambrogenea.familyvision.service.CityService;
import cz.ambrogenea.familyvision.service.MarriageService;
import cz.ambrogenea.familyvision.service.util.Services;

public class MarriageServiceImpl implements MarriageService {

    private final MarriageRepository marriageRepository = new MarriageRepository();
    private final CityService cityService = Services.city();

    @Override
    public Marriage createMarriage(MarriageCreateCommand marriageCreateCommand) {
        Long cityId = cityService.getCityId(marriageCreateCommand.getPlace());
        Marriage marriage = marriageRepository.save(MarriageMapper.map(marriageCreateCommand, cityId));
        saveSpousesId(marriageCreateCommand, marriage.getId());
        final Long treeId = marriageCreateCommand.getFamilyTreeId();
        marriageCreateCommand.getChildrenGedcomIds().forEach(childId -> {
                    Person child = Services.person().getByGedcomId(childId, treeId);
                    if (child != null) {
                        Person father = Services.person().getByGedcomId(marriageCreateCommand.getGedcomHusbandId(), treeId);
                        Person mother = Services.person().getByGedcomId(marriageCreateCommand.getGedcomWifeId(), treeId);
                        if (father != null) {
                            child.setFatherId(father.getId());
                        }
                        if (mother != null) {
                            child.setMotherId(mother.getId());
                        }
                        child.setParentId(marriage.getId());
                        Services.person().savePerson(child);
                        marriage.getChildrenIds().add(child.getId());
                    }
                }
        );
        return marriage;
    }

    private void saveSpousesId(MarriageCreateCommand marriageCreateCommand, Long marriageId) {
        Person husband = Services.person().getByGedcomId(marriageCreateCommand.getGedcomHusbandId(), marriageCreateCommand.getFamilyTreeId());
        Person wife = Services.person().getByGedcomId(marriageCreateCommand.getGedcomWifeId(), marriageCreateCommand.getFamilyTreeId());
        if (husband != null) {
            husband.getSpouseId().add(marriageId);
            Services.person().savePerson(husband);
        }
        if (wife != null) {
            wife.getSpouseId().add(marriageId);
            Services.person().savePerson(wife);
        }
    }

    @Override
    public Marriage getMarriageById(Long id) {
        return marriageRepository.findById(id).orElse(null);
    }

    @Override
    public MarriageDto getMarriageDtoById(Long id) {
        return getMarriageById(id) != null ? MarriageDtoMapper.map(getMarriageById(id)) : null;
    }

}
