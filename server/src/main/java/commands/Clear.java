package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import util.Receiver;
import util.Request;
import util.Response;

import static util.TextFormat.successText;

public class Clear extends Command{
    private final Receiver receiver;

    public Clear(Receiver receiver) {
        super("clear the collection", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        String user = request.getSession().getName();
        return new Response(receiver.clear(user));
    }
}
