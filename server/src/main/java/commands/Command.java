package commands;

import util.Request;
import util.Response;

import static util.TextFormat.helpText;

public abstract class Command implements CommandInterface{

    private final boolean isRequiredAuthorization;
    private final String description;

    public Command(String description, boolean isRequiredAuthorization) {
        this.isRequiredAuthorization = isRequiredAuthorization;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean getAuthorizationStatus() {
        return isRequiredAuthorization;
    }

    @Override
    public abstract Response execute(Request request);
}
