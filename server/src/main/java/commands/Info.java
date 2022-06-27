package commands;

import collection.CollectionManager;
import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

public class Info extends Command{

    private final Receiver receiver;

    public Info(Receiver receiver) {
        super("Print information about the collection", false);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        return new Response(receiver.info(), TypeOfAnswer.SUCCESSFUL);
    }
}
