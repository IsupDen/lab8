package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import data.LabWork;
import util.Receiver;
import util.Request;
import util.Response;

import static util.TextFormat.successText;

public class Add extends Command{

    private final Receiver receiver;

    public Add(Receiver receiver) {
        super("add new element to the collection", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        LabWork labWork = request.getCommand().getLabWork();
        return new Response(labWork, receiver.add(labWork));
    }
}
