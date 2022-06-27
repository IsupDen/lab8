package commands;

import collection.CollectionManager;
import collection.CollectionManagerInterface;
import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.TextFormat.helpText;

public class Group extends Command{
    private final Receiver receiver;

    public Group(Receiver receiver) {
        super( "group the elements of the collection by" +
                "the value of the coordinates field, display the number of elements in each group", false);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request command) {
        return new Response(
            receiver.show()
                .stream()
                .collect(Collectors.toMap(labWork -> labWork.getCoordinates().toString(), count -> 1, Integer::sum))
                .entrySet()
                    .stream()
                    .map(e -> e.getKey() + " : " + e.getValue())
                    .collect(Collectors.joining("\n")), TypeOfAnswer.SUCCESSFUL
        );
    }
}
