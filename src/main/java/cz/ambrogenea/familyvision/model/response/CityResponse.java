package cz.ambrogenea.familyvision.model.response;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record CityResponse(
        long id,
        String name,
        String shortName,
        String czechName
) {
}
