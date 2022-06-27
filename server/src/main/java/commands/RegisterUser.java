package commands;

import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

public class RegisterUser extends Command {
    private final Receiver receiver;

    public RegisterUser(Receiver receiver) {
        super("add new user to system", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        String username = request.getSession().getName();
        String password = request.getSession().getPassword();
        if (!receiver.loginUser(username, password)) {
            if (receiver.registerUser(username, password)) return new Response(TypeOfAnswer.SUCCESSFUL);
        }
        return new Response(TypeOfAnswer.ALREADYREGISTERED);
    }
}
