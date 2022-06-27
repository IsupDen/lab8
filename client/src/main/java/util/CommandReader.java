package util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.TextFormat.errText;
import static util.TextFormat.successText;

public class CommandReader {

    private final Console console;
    private final CommandManager commandManager;


    public CommandReader() {
        console = Console.getInstance();
        commandManager = new CommandManager(this);
    }

    public boolean enable() {

        while (true) {

            if (!RequestHandler.getInstance().getSocketStatus()) return false;

            String line;
            console.print("Enter the command: ");
            try {
                line = console.read();
            } catch (IOException exception) {
                line = null;
            }

            UserCommand newCommand = readCommand(line);
            if (newCommand != null) {
                if (newCommand.getCommand().equals("exit") && newCommand.getArg() == null) {
                    console.println(successText("Have a nice day!"));
                    return true;
                }
                commandManager.transferCommand(newCommand);
            } else {
                console.println(errText("Command entered incorrectly!"));
            }
        }
    }

    public UserCommand readCommand(String inputString) {

        if (inputString == null) return null;

        String command;
        String arg;
        Pattern commandName = Pattern.compile("^\\w+");

        Matcher matcher = commandName.matcher(inputString);

        if (matcher.find()) {
            command = matcher.group();
        } else {
            return null;
        }

        arg = inputString.substring(command.length()).trim();
        if (arg.equals("")) arg = null;

        return new UserCommand(command.toLowerCase(), arg);
    }
}
