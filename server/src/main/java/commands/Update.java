package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import data.LabWork;
import util.Receiver;
import util.Request;
import util.Response;

import static util.TextFormat.errText;
import static util.TextFormat.successText;

public class Update extends Command{
    private final Receiver receiver;

    public Update(Receiver receiver) {
        super("update the element`s value, whose ID is equal to the given", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        LabWork upgradedLabWork = request.getCommand().getLabWork();
        int id = Integer.parseInt(request.getCommand().getArg());
        return new Response(receiver.updateId(upgradedLabWork, id));
    }
}
