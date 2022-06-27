package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import util.Receiver;
import util.Request;
import util.Response;

import static util.TextFormat.errText;
import static util.TextFormat.successText;

public class Remove extends Command{
    private final Receiver receiver;

    public Remove(Receiver receiver) {
        super("remove an element from the collection by ID", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {

        String username = request.getSession().getName();
        int id = Integer.parseInt(request.getCommand().getArg());
        return new Response(receiver.removeById(username, id));
    }
}
