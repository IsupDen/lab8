package data;

import java.io.Serializable;

public enum EyeColor implements Serializable {
    GREEN("GREEN"),
    BLUE("BLUE"),
    ORANGE("ORANGE"),
    WHITE("WHITE"),
    BROWN("BROWN");

    private final String stringValue;

    EyeColor(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static boolean isIncludeElement(String eyeColor) {
        for (EyeColor currentEyeColor : EyeColor.values()) {
            if (eyeColor.equals(currentEyeColor.getStringValue())) {
                return true;
            }
        }
        return false;
    }
}
