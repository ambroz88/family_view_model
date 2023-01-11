package cz.ambrogenea.familyvision.model.response;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record ResidenceResponse(
        long id,
        String cityName,
        Integer number,
        String date
) {
}
