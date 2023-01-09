package cz.ambrogenea.familyvision.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    private static ObjectMapper objectMapper;

    public static ObjectMapper get() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

}
