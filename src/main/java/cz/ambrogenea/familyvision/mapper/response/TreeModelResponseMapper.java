package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.model.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.model.response.tree.ImageModelResponse;
import cz.ambrogenea.familyvision.model.response.tree.TreeModelResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TreeModelResponseMapper {

    public static TreeModelResponse map(TreeModel treeModel) {
        return new TreeModelResponse(
                treeModel.treeName(),
                PersonRecordResponseMapper.map(treeModel.rootPerson()),
                treeModel.persons().stream().map(PersonRecordResponseMapper::map).collect(Collectors.toSet()),
                treeModel.marriages().stream().map(MarriageResponseMapper::map).collect(Collectors.toSet()),
                treeModel.lines().stream().map(LineResponseMapper::map).collect(Collectors.toSet()),
                treeModel.arcs().stream().map(ArcResponseMapper::map).collect(Collectors.toSet()),
                treeModel.images().stream().map(imageModel ->
                        new ImageModelResponse(
                                imageModel.imageName(),
                                imageModel.x(),
                                imageModel.y()
                        )
                ).collect(Collectors.toSet()),
                treeModel.residences().stream().map(ResidenceResponseMapper::map).collect(Collectors.toSet()),
                new ArrayList<>(treeModel.cityRegister()),
                PageSetupResponseMapper.map(treeModel.pageMaxCoordinates())
        );
    }
}
