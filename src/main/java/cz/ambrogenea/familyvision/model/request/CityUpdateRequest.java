package cz.ambrogenea.familyvision.model.request;

public record CityUpdateRequest(
        String name,
        String shortName,
        String czechName
) {
}
