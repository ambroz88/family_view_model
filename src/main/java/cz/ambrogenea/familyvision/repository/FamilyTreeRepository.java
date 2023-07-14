package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.FamilyTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FamilyTreeRepository {

    private final HashMap<Long, FamilyTree> familyTreeMap = new HashMap<>();

    public FamilyTree save(FamilyTree familyTree) {
        familyTreeMap.put(familyTree.getId(), familyTree);
        return familyTree;
    }

    public Optional<FamilyTree> findById(Long id) {
        return Optional.ofNullable(familyTreeMap.get(id));
    }

    public List<FamilyTree> findAll() {
        return new ArrayList<>(familyTreeMap.values());
    }

    public void deleteById(Long id) {
        familyTreeMap.remove(id);
    }

}
