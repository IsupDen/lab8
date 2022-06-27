package util;

import data.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;

import static util.TextFormat.errText;
import static util.TextFormat.helpText;

public class FieldsReceiver implements FieldsReceiverInterface {


    private final Console console;

    public FieldsReceiver(Console console) {
        this.console = console;
    }

    private Object workWithTypes(String line, TypesOfArg type, boolean minExist, boolean nullAvailable) {

        try {
            switch (type) {
                case INTEGER: {
                    if (line != null) Integer.parseInt(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case DOUBLE: {
                    if (line != null) Double.parseDouble(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case STRING: {
                    if (line != null) return line;
                    break;
                }
            }
        } catch (NumberFormatException e) {
            line = null;
        }

        if (line != null) {
            switch (type) {

                case INTEGER: {
                    if (!minExist || Integer.parseInt(line) > 0) return Integer.parseInt(line);
                    break;
                }
                case DOUBLE: {
                    if (!minExist || Double.parseDouble(line) > 0) return Double.parseDouble(line);
                    break;
                }
            }
        }

        return null;
    }

    private String getUniversalRequest(String requestField, String options, Console console, boolean nullAvailable) throws IOException {

        do {
            StringBuilder sb = new StringBuilder();
            sb.append(errText(requestField)).
                    append(errText(" should be ")).append(errText(options)).
                    append(errText("!\n"));
            sb.append("Enter ").append(requestField).append(" again: ");
            console.print(sb, true);

            String line = console.read();

            if (line == null && console.getExeStatus()) return null;
            if (line != null || nullAvailable) return line;
        } while (true);
    }

    private Object getGeneralRequest(String requestField, String options,
                                     Console console, boolean minExist, boolean nullAvailable, TypesOfArg type) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(StringUtils.repeat("-", requestField.length())).append("\n");
        sb.append(requestField.toUpperCase()).append("\n");
        sb.append(StringUtils.repeat("-", requestField.length())).append("\n");
        sb.append("Enter ").append(requestField).append(": ");
        console.print(sb, true);

        String line;
        try {
            line = console.read();
        } catch (IOException exception) {
            return null;
        }

        if (line == null) return null;
        else if (line.trim().equals("")) line = null;

        Object firstRequest = workWithTypes(line, type, minExist, nullAvailable);

        if (firstRequest != null || (nullAvailable && line == null)) return firstRequest;

        do {
            try {
                line = getUniversalRequest(requestField, options, console, nullAvailable);
            } catch (IOException exception) {
                return null;
            }

            if (line == null) return null;
            else if (line.trim().equals("")) line = null;

            Object request = workWithTypes(line, type, minExist, nullAvailable);

            if (request != null || (nullAvailable && line == null)) return request;

        } while (true);
    }


    private String getFirstEnumRequest(String requestField, String enumerateList, Console console) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(StringUtils.repeat("-", requestField.length())).append("\n");
        sb.append(requestField.toUpperCase()).append("\n");
        sb.append(StringUtils.repeat("-", requestField.length())).append("\n");
        sb.append("Available ").append(requestField).append(":\n");
        sb.append(helpText(enumerateList));
        sb.append("\nEnter ").append(requestField).append(": ");
        console.print(sb, true);

        try {
            return console.read();
        } catch (IOException exception) {
            return null;
        }
    }

    private String getUniversalEnumRequest(String requestField, Console console) {

        StringBuilder sb = new StringBuilder();
        sb.append(errText("It's incorrect ")).
                append(errText(requestField)).append(errText("!"));
        sb.append("\nEnter ").append(requestField).append(" again: ");
        console.print(sb, true);

        try {
            return console.read();
        } catch (IOException exception) {
            return null;
        }
    }


    @Override
    public String getName() {
        return (String) getGeneralRequest("LabWork name", "not empty string",
                console, false, false, TypesOfArg.STRING);
    }

    @Override
    public Coordinates getCoordinates() {

        Integer x = (Integer) getGeneralRequest("x coordinate",
                "not null int number", console, false, false, TypesOfArg.INTEGER);
        if (x == null) return null;

        Integer y = (Integer) getGeneralRequest("y coordinate", "not null int number", console,
                false, false, TypesOfArg.INTEGER);
        if (y == null) return null;

        return new Coordinates(x, y);
    }

    @Override
    public Double getMinimalPoint() {

        return (Double) getGeneralRequest("LabWork minimal point",
                "positive double", console, true, false, TypesOfArg.DOUBLE);
    }

    @Override
    public Integer getPersonalQualitiesMinimum() {

        return (Integer) getGeneralRequest("Personal qualities minimum",
                "not null positive Integer number or you can skip this field", console, true, true, TypesOfArg.INTEGER);
    }

    @Override
    public Difficulty getDifficulty() {

        String line = getFirstEnumRequest("LabWork difficulty", Arrays.toString(Difficulty.values()), console);
        if (line == null || line.trim().equals("")) return null;

        while (true) {

            if (line != null && line.trim().equals("")) line = null;
            if (line == null) return null;

            if (Difficulty.isIncludeElement(line))
                return Difficulty.valueOf(line.toUpperCase());

            line = getUniversalEnumRequest("Difficulty", console);
        }
    }

    @Override
    public Person getAuthor() {
        console.print("\n------\n" +
                "Author\n" +
                "------\n", true);

        String name = (String) getGeneralRequest("Author's name", "not empty string",
                console, false, false, TypesOfArg.STRING);

        if (name == null) return null;

        Double height = (Double) getGeneralRequest("Author's height",
                "not null positive double number", console, true, false, TypesOfArg.DOUBLE);

        if (height == null) return null;

        String eyes = getFirstEnumRequest("Author's eyes color", Arrays.toString(EyeColor.values()), console);

        if (eyes == null) return null;

        while (eyes == null || !EyeColor.isIncludeElement(eyes.toUpperCase())) {

            eyes = getUniversalEnumRequest("Author's eyes color", console);

            if (eyes == null) return null;
            if (eyes.trim().equals("")) eyes = null;
        }

        EyeColor eyeColor = EyeColor.valueOf(eyes.toUpperCase());

        String hairs = getFirstEnumRequest("Author's hair color", Arrays.toString(HairColor.values()), console);

        if (hairs == null) return null;

        while (hairs == null || !HairColor.isIncludeElement(hairs.toUpperCase())) {

            hairs = getUniversalEnumRequest("Author's hair color", console);

            if (hairs == null) return null;
            if (hairs.trim().equals("")) hairs = null;
        }

        HairColor hairColor = HairColor.valueOf(hairs.toUpperCase());

        String country = getFirstEnumRequest("Author's nationality", Arrays.toString(Country.values()), console);

        if (country == null) return null;

        while (country == null || !Country.isIncludeElement(country.toUpperCase())) {

            country = getUniversalEnumRequest("Author's nationality", console);

            if (country == null) return null;
            if (country.trim().equals("")) country = null;
        }

        Country nationality = Country.valueOf(country.toUpperCase());

        return new Person(name, height, hairColor, eyeColor, nationality);
    }
}
