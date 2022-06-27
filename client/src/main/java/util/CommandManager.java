package util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static util.TextFormat.errText;
import static util.TextFormat.successText;


public class CommandManager {
    private final CommandReader commandReader;
    private final Validator validator;
    private final Console console;
    private final Set<String> usedScripts;
    private final LabFactory labFactory;


    public CommandManager(CommandReader commandReader) {
        this.commandReader = commandReader;
        validator = Validator.getInstance();
        console = Console.getInstance();
        usedScripts = new HashSet<>();
        labFactory = new LabFactory();
    }


    public void transferCommand(UserCommand command) {

        if (validator.notObjectArgumentCommands(command))
            console.println("\n" + RequestHandler.getInstance().send(command) + "\n");
        else if (validator.objectArgumentCommands(command)) {
            console.println("\n" + RequestHandler.getInstance().send(command, labFactory.createLabWork()) + "\n");
        } else if (validator.validateScriptArgumentCommand(command)) {
            executeScript(command.getArg());
        } else {
            console.println(errText("Command entered incorrectly!"));
        }
    }

    public void transferScriptCommand(UserCommand command) {
        if (validator.notObjectArgumentCommands(command))
            RequestHandler.getInstance().send(command);
        else if (validator.objectArgumentCommands(command)) {
            RequestHandler.getInstance().send(command, labFactory.createLabWork());
        } else if (validator.validateScriptArgumentCommand(command)) {
            executeScript(command.getArg());
        }
    }

    public String executeScript(String scriptName) {

        if (usedScripts.add(scriptName)) {

            ScriptReader scriptReader = new ScriptReader(this, commandReader, new File(scriptName));
            try {
                scriptReader.read();
                usedScripts.remove(scriptName);

                return "The script " + scriptName
                        + " was processed successfully!";
            } catch (IOException exception) {

                usedScripts.remove(scriptName);

                if (!new File(scriptName).exists()) return "The script does not exist!";
                else if (!new File(scriptName).canRead()) return "The system does not have permission to read the file!";
                else return "We have some problem's with script!";
            }

        } else return "Recursion has been detected! Script " + scriptName +
                " will not be ran!";
    }
}
