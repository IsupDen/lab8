package commands;

import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @command "display help for available commands."
 */

public class Help extends Command{

    private final Map<String, Command> commandsInfo;

    private final Receiver receiver;

    public Help(Map<String, Command> commands, Receiver receiver) {
        super("display help for available commands", false);
        commandsInfo = commands;
        this.receiver = receiver;
    }


    @Override
    public Response execute(Request request) {

        Map<String, String> mapOfCommands = commandsInfo.keySet()
                .stream()
                .filter(str -> !(str.equals("register") || str.equals("login")))
                .collect(Collectors.toConcurrentMap(command -> command, command -> commandsInfo.get(command).getDescription()));
        mapOfCommands.put("execute_script", "Read and execute script from entered file");
        mapOfCommands.put("exit", "end the program");
        return new Response(mapOfCommands, TypeOfAnswer.SUCCESSFUL);
    }
}
