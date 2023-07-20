package cz.ambrogenea.familyvision.dto;

import cz.ambrogenea.familyvision.domain.Person;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record MarriageDto(
        Person husband,
        Person wife,
        Long treeId,
        DatePlaceDto weddingDatePlace,
        List<String> childrenIds
) {
}
