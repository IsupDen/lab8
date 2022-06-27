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
import java.util.List;
import java.util.Set;

import static util.TextFormat.helpText;

public class Show extends Command{
    private final Receiver receiver;

    public Show(Receiver receiver) {
        super("print all elements in string representation to standard output", false);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        ArrayList<LabWork> labWorks = receiver.show();
        if (labWorks == null) return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        else return new Response(labWorks, TypeOfAnswer.SUCCESSFUL);
    }
}
