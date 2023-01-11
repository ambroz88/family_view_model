package cz.ambrogenea.familyvision.model.command;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record DatePlaceCreateCommand(
        String date,
        String place
) {
}
