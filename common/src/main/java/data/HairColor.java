package data;

import java.io.Serializable;

public enum HairColor implements Serializable {
    BLACK("BLACK"),
    BLUE("BLUE"),
    ORANGE("ORANGE"),
    WHITE("WHITE"),
    BROWN("BROWN");

    private final String stringValue;

    HairColor(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static boolean isIncludeElement(String hairColor) {
        for (HairColor currentHairColor : HairColor.values()) {
            if (hairColor.equals(currentHairColor.getStringValue())) {
                return true;
            }
        }
        return false;
    }
}
