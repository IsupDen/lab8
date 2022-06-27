package commands;

import util.Request;
import util.Response;

public interface CommandInterface {
    String getDescription();

    boolean getAuthorizationStatus();
    Response execute(Request request);
}
