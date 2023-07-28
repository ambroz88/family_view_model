package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.domain.City;
import cz.ambrogenea.familyvision.model.response.DatePlaceResponse;
import cz.ambrogenea.familyvision.service.CityService;
import cz.ambrogenea.familyvision.service.util.Services;

public class DatePlaceResponseMapper {

    private static final CityService cityService = Services.city();

    public static DatePlaceResponse map(String date, Long cityId) {
        String year = "";
        if (date != null && !date.isEmpty()) {
            int index = date.lastIndexOf(" ");
            if (index != -1) {
                year = date.substring(index + 1);
            } else {
                year = date;
            }
        }
        String place = "";
        City city = cityService.getCityById(cityId);
        if (city != null) {
            place = city.getName();
        }
        return new DatePlaceResponse(year, place);
    }

}
