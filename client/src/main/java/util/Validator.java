package util;

public class Validator {
    private static AvailableCommands availableCommands;
    private static Validator instance;

    private Validator() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            availableCommands = new AvailableCommands();
            instance = new Validator();
        }
        return instance;
    }

    public boolean notObjectArgumentCommands(UserCommand command) {
        return validateNoArgumentCommand(command) ||
                validateNumArgumentCommands(command) ||
                validateStringArgumentCommands(command);
    }

    public boolean objectArgumentCommands(UserCommand command) {
        return validateObjectArgumentCommands(command) ||
                validateObjAndNumArgumentCommand(command);
    }

    private boolean validateNoArgumentCommand(UserCommand command) {
        return availableCommands.noArgCommands.contains(command.getCommand()) &&
                command.getArg() == null;
    }

    private boolean validateNumArgumentCommands(UserCommand command) {
        return availableCommands.numArgCommands.contains(command.getCommand()) &&
                command.isArgInt() && Integer.parseInt(command.getArg()) > 0;
    }

    private boolean validateStringArgumentCommands(UserCommand command) {
        return availableCommands.strArgCommands.contains(command.getCommand()) &&
                command.getArg() != null;
    }

    private boolean validateObjectArgumentCommands(UserCommand command) {
        return availableCommands.objArgCommands.contains(command.getCommand()) &&
                command.getArg() == null;
    }

    private boolean validateObjAndNumArgumentCommand(UserCommand command) {
        return availableCommands.objAndNumArgCommand.contains(command.getCommand()) &&
                command.isArgInt() && Integer.parseInt(command.getArg()) > 0;
    }

    public boolean validateScriptArgumentCommand(UserCommand command) {
        return availableCommands.scriptArgCommand.equals(command.getCommand()) &&
                command.getArg() != null;
    }
}
