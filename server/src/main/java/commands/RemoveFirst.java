package commands;

import collection.CollectionManager;
import util.Receiver;
import util.Request;
import util.Response;

public class RemoveFirst extends Command{
    private final Receiver receiver;

    private final CollectionManager collectionManager;

    public RemoveFirst(Receiver receiver) {
        super("remove the first element from the collection", true);
        this.receiver = receiver;
        this.collectionManager = CollectionManager.getInstance();
    }

    @Override
    public Response execute(Request request) {
        String username = request.getSession().getName();
        return new Response(receiver.removeById(username, collectionManager.getCollection().get(0).getId()));
    }
}
