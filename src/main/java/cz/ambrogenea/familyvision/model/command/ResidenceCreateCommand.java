package cz.ambrogenea.familyvision.model.command;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record ResidenceCreateCommand(
        String place,
        String date
) {
}
