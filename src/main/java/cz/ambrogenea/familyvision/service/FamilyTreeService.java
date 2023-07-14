package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.FamilyTree;
import cz.ambrogenea.familyvision.model.command.FamilyTreeCommand;

import java.util.List;

public interface FamilyTreeService {
    FamilyTree saveFamilyTree(FamilyTreeCommand familyTreeCommand);
    FamilyTree getFamilyTreeById(Long id);
    List<FamilyTree> getFamilyTrees();
    void deleteFamilyTree(Long treeId);
}
