package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import data.LabWork;
import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

import java.util.ArrayList;
import java.util.Collections;

import static util.TextFormat.successText;

public class Reorder extends Command{
    private final Receiver receiver;

    public Reorder(Receiver receiver) {
        super( "sort the collection in reverse order", false);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request command) {
        ArrayList<LabWork> labWorks = receiver.show();
        if (labWorks == null) return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        else {
            Collections.reverse(labWorks);
            return new Response(labWorks, TypeOfAnswer.SUCCESSFUL);
        }
    }
}
