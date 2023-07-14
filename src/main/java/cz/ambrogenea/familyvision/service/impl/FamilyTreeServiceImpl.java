package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.FamilyTree;
import cz.ambrogenea.familyvision.model.command.FamilyTreeCommand;
import cz.ambrogenea.familyvision.repository.FamilyTreeRepository;
import cz.ambrogenea.familyvision.service.FamilyTreeService;

import java.util.List;
import java.util.Optional;

public class FamilyTreeServiceImpl implements FamilyTreeService {

    private final FamilyTreeRepository familyTreeRepository = new FamilyTreeRepository();

    @Override
    public FamilyTree saveFamilyTree(FamilyTreeCommand familyTreeCommand) {
        FamilyTree tree = new FamilyTree(familyTreeCommand.treeName());
        return familyTreeRepository.save(tree);
    }

    @Override
    public FamilyTree getFamilyTreeById(Long id) {
        return familyTreeRepository.findById(id).orElse(null);
    }

    @Override
    public List<FamilyTree> getFamilyTrees() {
        return familyTreeRepository.findAll();
    }

    @Override
    public void deleteFamilyTree(Long treeId) {
        familyTreeRepository.deleteById(treeId);
    }

}
