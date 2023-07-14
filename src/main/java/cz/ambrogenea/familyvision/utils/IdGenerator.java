package cz.ambrogenea.familyvision.utils;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {

    private static final Map<String, Long> idMap = new HashMap<>();

    public static Long generate(String collectionName) {
        Long id;
        if (idMap.containsKey(collectionName)) {
            id = idMap.get(collectionName) + 1;
        } else {
            id = 1L;
        }
        idMap.put(collectionName, id);
        return id;
    }
}
