package cz.ambrogenea.familyvision.model.response;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record CityResponse(
        Long id,
        String name,
        String alternativeName,
        String shortName,
        String district,
        String region,
        String country
) {
}
