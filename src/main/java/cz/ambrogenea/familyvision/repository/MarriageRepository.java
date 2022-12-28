package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.Marriage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MarriageRepository {

    private final HashMap<String, Marriage> marriageMap = new HashMap<>();

    public Marriage save(Marriage marriage){
        marriageMap.put(marriage.getGedcomFamilyId(), marriage);
        return marriage;
    }
    public Optional<Marriage> findByGedcomId(String gedcomId){
        return Optional.ofNullable(marriageMap.get(gedcomId));
    }

    public List<Marriage> findAll(){
        return new ArrayList<>(marriageMap.values());
    }

}
