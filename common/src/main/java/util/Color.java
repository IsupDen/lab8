package util;

public enum Color {
    AQUA("AQUA"),
    BLUEVIOLET("BLUEVIOLET"),
    CHARTREUSE("CHARTREUSE"),
    CORNFLOWERBLUE("CORNFLOWERBLUE"),
    DARKMAGENTA("DARKMAGENTA"),
    DEEPPINK("DEEPPINK"),
    DEEPSKYBLUE("DEEPSKYBLUE"),
    FORESTGREEN("FORESTGREEN"),
    GOLD("GOLD"),
    GREENYELLOW("GREENYELLOW");

    private final String stringValue;

    Color(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
