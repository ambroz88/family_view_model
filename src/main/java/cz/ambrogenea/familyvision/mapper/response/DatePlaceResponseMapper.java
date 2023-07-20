package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.model.response.DatePlaceResponse;

public class DatePlaceResponseMapper {

    public static DatePlaceResponse map(String date, String place) {
        String year = "";
        if (date != null && !date.isEmpty()) {
            int index = date.lastIndexOf(" ");
            if (index != -1) {
                year = date.substring(index + 1);
            } else {
                year = date;
            }
        }
        return new DatePlaceResponse(
                year,
                place != null ? place.split(",")[0] : ""
        );
    }

}
