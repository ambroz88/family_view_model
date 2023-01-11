package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.domain.DatePlace;
import cz.ambrogenea.familyvision.model.response.DatePlaceResponse;
import cz.ambrogenea.familyvision.service.util.Config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePlaceResponseMapper {

    public static DatePlaceResponse map(DatePlace datePlace) {
        String dateSpecification = null;
        if (datePlace.getDateSpecification() != null) {
            dateSpecification = datePlace.getDateSpecification().getString(Config.visual().getLocale());
        }

        String year = "";
        Date date = datePlace.getDate();
        if (date != null) {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy");
            year = dtf.format(date);
        }
        return new DatePlaceResponse(
                year,
                dateSpecification,
                datePlace.getSimplePlace()
        );
    }

}
