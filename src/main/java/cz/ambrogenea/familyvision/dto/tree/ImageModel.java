package cz.ambrogenea.familyvision.dto.tree;

/**
 * @author Jiri Ambroz
 */
public record ImageModel(
        String imageName,
        int x,
        int y
) {

    public ImageModel(String imageName, Position imageLeftCorner) {
        this(
                imageName,
                imageLeftCorner.x(),
                imageLeftCorner.y()
        );
    }

}
