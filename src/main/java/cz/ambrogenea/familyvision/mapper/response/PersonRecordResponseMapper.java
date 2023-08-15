package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.model.dto.tree.PersonRecord;
import cz.ambrogenea.familyvision.model.response.tree.PersonRecordResponse;
import cz.ambrogenea.familyvision.model.response.tree.PositionResponse;

public class PersonRecordResponseMapper {

    public static PersonRecordResponse map(PersonRecord personRecord) {
        return new PersonRecordResponse(
                new PositionResponse(
                        personRecord.position().x(),
                        personRecord.position().y()
                ),
                personRecord.id(),
                personRecord.firstName(),
                personRecord.surname(),
                personRecord.sex(),
                personRecord.birthDatePlace(),
                personRecord.deathDatePlace(),
                personRecord.occupation(),
                personRecord.living(),
                personRecord.directLineage()
        );
    }
}
