package util;

import data.LabWork;

import java.io.Serializable;

public class UserCommand implements Serializable {
    private final String commandName;
    private final String argName;
    private LabWork labWork;

    public UserCommand(String commandName, String argName) {

        this.commandName = commandName;
        this.argName = argName;
        labWork = null;
    }

    public UserCommand addLabWork(LabWork labWork) {
        this.labWork = labWork;
        return this;
    }

    public String getCommand() {
        return commandName;
    }

    public String getArg() {
        return argName;
    }

    public LabWork getLabWork() {
        return labWork;
    }

    @Override
    public String toString() {
        System.out.println(commandName + " " + argName);
        return commandName + " "
                + (argName != null ? argName + "\n" : "\n")
                + (labWork != null ? labWork : "");
    }

    public boolean isArgInt() {
        try {
            if (argName != null) {
                Integer.parseInt(argName);
                return true;
            } else return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
