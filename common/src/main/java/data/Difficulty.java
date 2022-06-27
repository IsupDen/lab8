package data;

import java.io.Serializable;
import java.util.Arrays;

public enum Difficulty implements Serializable {
    NORMAL("NORMAL"),
    INSANE("INSANE"),
    TERRIBLE("TERRIBLE");

    private final String stringValue;

    Difficulty(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static boolean isIncludeElement(String difficulty) {
        return Arrays.stream(Difficulty.values()).anyMatch(difficulty1 -> difficulty.toUpperCase().equals(difficulty1.getStringValue()));
    }
}
