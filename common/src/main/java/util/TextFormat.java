package util;

public class TextFormat {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public static String errText(String text) {return ANSI_RED + text + ANSI_RESET;}

    public static String successText(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public static String helpText(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }
}
