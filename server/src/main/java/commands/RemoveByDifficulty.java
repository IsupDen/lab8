package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import data.LabWork;
import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

import java.util.ArrayList;

import static util.TextFormat.successText;

public class RemoveByDifficulty extends Command{
    private final Receiver receiver;

    public RemoveByDifficulty(Receiver receiver) {
        super( "remove all elements from the collection by difficulty", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request command) {
        return new Response(receiver.removeByDifficulty(command.getCommand().getArg(), command.getSession().getName()));
    }
}
