package util;

import java.io.Serializable;

public class Request implements Serializable {

    private final UserCommand userCommand;
    private final Session session;

    public Request(UserCommand userCommand, Session session) {
        this.userCommand = userCommand;
        this.session = session;
    }

    public UserCommand getCommand(){
        return userCommand;
    }

    public Session getSession(){
        return session;
    }

    @Override
    public String toString() {
        return userCommand.toString() + "from (" + session.toString() + ")";
    }


}
