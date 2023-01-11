package cz.ambrogenea.familyvision.model.response.tree;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record ResidenceResponse(
        PositionResponse position,
        String city,
        String date,
        int number
) {
}
