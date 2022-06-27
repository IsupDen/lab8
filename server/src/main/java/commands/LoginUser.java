package commands;

import util.Receiver;
import util.Request;
import util.Response;
import util.TypeOfAnswer;

public class LoginUser extends Command{
    private final Receiver receiver;

    public LoginUser(Receiver receiver) {
        super("add new user to system", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        String username = request.getSession().getName();
        String password = request.getSession().getPassword();
        return receiver.loginUser(username, password)
                ? new Response(TypeOfAnswer.SUCCESSFUL)
                : new Response(TypeOfAnswer.NOTMATCH);
    }
}
