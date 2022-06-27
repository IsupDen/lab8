package data;

import java.io.Serializable;

public enum Country implements Serializable {
    GERMANY("GERMANY"),
    VATICAN("VATICAN"),
    ITALY("ITALY"),
    NORTH_KOREA("NORTH_KOREA");

    private final String stringValue;

    Country(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static boolean isIncludeElement(String country) {
        for (Country currentCounty : Country.values()) {
            if (country.equals(currentCounty.getStringValue())) {
                return true;
            }
        }
        return false;
    }
}
