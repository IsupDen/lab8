package util;

import java.io.Serializable;

public class Session implements Serializable {
    private final String name;
    private final String password;
    private TypeOfSession typeOfSession;


    public Session(String name, String password, TypeOfSession typeOfSession) {
        this.name = name;
        this.password = (password.trim().equals("")) ? null : password;
        this.typeOfSession = typeOfSession;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public TypeOfSession getTypeOfSession() {
        return typeOfSession;
    }

    public void setTypeOfSession(TypeOfSession typeOfSession) {
        this.typeOfSession = typeOfSession;
    }

    @Override
    public String toString() {
        return "username: " + name;
    }
}
