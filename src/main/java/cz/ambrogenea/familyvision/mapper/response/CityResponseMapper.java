package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.domain.City;
import cz.ambrogenea.familyvision.model.response.CityResponse;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class CityResponseMapper {

    public static CityResponse map(City domain) {
        return new CityResponse(
                domain.getId(),
                domain.getName(),
                domain.getAlternativeName(),
                domain.getShortName(),
                domain.getDistrict(),
                domain.getRegion(),
                domain.getCountry()
        );
    }

}
