package util;

import collection.CollectionManager;
import commands.*;

import java.util.*;

public class Invoker{


    private final Receiver receiver;
    private final Map<String, Command> commands;

    public Invoker(Receiver receiver) {
        this.receiver = receiver;
        commands = new HashMap<>();
        initMap();
    }

    public Receiver getReceiver() {return receiver;}

    public Response execute(Request request) {
        String command = request.getCommand().getCommand();
        String username = request.getSession().getName();
        String password = request.getSession().getPassword();
        return (!commands.get(command).getAuthorizationStatus()) ? commands.get(command).execute(request)
                : (command.equals("register") || receiver.loginUser(username, password))
                ? commands.get(command).execute(request)
                : new Response(TypeOfAnswer.NOTMATCH);
    }

    private void initMap() {
        commands.put("help", new Help(commands, receiver));
        commands.put("add", new Add(receiver));
        commands.put("add_if_max", new AddIfMax(receiver));
        commands.put("clear", new Clear(receiver));
        commands.put("count_less_than_pqm", new Count(receiver));
        commands.put("group_counting_by_coordinates", new Group(receiver));
        commands.put("info", new Info(receiver));
        commands.put("remove_by_id", new Remove(receiver));
        commands.put("remove_all_by_difficulty", new RemoveByDifficulty(receiver));
        commands.put("remove_first", new RemoveFirst(receiver));
        commands.put("reorder", new Reorder(receiver));
        commands.put("show", new Show(receiver));
        commands.put("update", new Update(receiver));
        commands.put("register", new RegisterUser(receiver));
        commands.put("login", new LoginUser(receiver));

    }
}
