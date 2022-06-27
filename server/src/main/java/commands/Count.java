package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import data.LabWork;
import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

import java.util.ArrayList;

import static util.TextFormat.helpText;
import static util.TextFormat.successText;

public class Count extends Command{
    private final Receiver receiver;

    public Count(Receiver receiver) {
        super("print the number of elements whose "
                + "personalQualitiesMinimum field value is less than the specified one", false);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        Integer count = Integer.valueOf(request.getCommand().getArg());
        Long countLabWorks = receiver.countLessThanPersonalQualitiesMinimum(count);
        return (countLabWorks == null)
                ? new Response(TypeOfAnswer.EMPTYCOLLECTION)
                : new Response(countLabWorks, TypeOfAnswer.SUCCESSFUL);
    }
}
