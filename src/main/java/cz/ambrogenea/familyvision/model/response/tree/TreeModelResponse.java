package cz.ambrogenea.familyvision.model.response.tree;

import java.util.ArrayList;
import java.util.Set;

public record TreeModelResponse(
        String treeName,
        PersonRecordResponse rootPerson,
        Set<PersonRecordResponse> persons,
        Set<MarriageResponse> marriages,
        Set<LineResponse> lines,
        Set<ArcResponse> arcs,
        Set<ImageModelResponse> images,
        Set<ResidenceResponse> residences,
        ArrayList<String> cityRegister,
        PageSetupResponse pageSetup
) {
}
