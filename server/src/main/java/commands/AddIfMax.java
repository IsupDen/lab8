package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import data.LabWork;
import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;


import static util.TextFormat.*;

public class AddIfMax extends Command{

    private final Receiver receiver;

    public AddIfMax(Receiver receiver) {
        super("add new element to the collection, if it`s greater, than biggest element of this collection", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        LabWork labWork = request.getCommand().getLabWork();
        TypeOfAnswer status = receiver.addIfMax(labWork);
        return new Response(labWork, status);
    }
}
