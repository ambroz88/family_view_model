package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.Marriage;

import java.util.HashMap;
import java.util.Optional;

public class MarriageRepository {

    private final HashMap<Long, Marriage> marriageIdMap = new HashMap<>();

    public Marriage save(Marriage marriage){
        marriageIdMap.put(marriage.getId(), marriage);
        return marriage;
    }

    public Optional<Marriage> findById(Long id){
        return Optional.ofNullable(marriageIdMap.get(id));
    }

}
