package cz.ambrogenea.familyvision.mapper.util;

public class Verification {

    public static String gedcomId(String gedcomId) {
        String correctId = gedcomId;
        if (gedcomId != null && gedcomId.length() < 5) {
            String zeros = generateZeros(5 - gedcomId.length());
            correctId = gedcomId.charAt(0) + zeros + gedcomId.substring(1);
        }
        return correctId;
    }

    private static String generateZeros(int zeroCount) {
        return "0".repeat(zeroCount);
    }

}
