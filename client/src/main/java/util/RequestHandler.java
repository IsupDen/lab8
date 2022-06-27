package util;

import data.LabWork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

import static util.TextFormat.errText;
import static util.TextFormat.successText;

public class RequestHandler {

    private static RequestHandler instance;
    private InetSocketAddress socketAddress;
    private boolean socketStatus;

    private Session session;


    public static RequestHandler getInstance() {
        if (instance == null) instance = new RequestHandler();
        return instance;
    }

    public Response send(UserCommand command) {
        try {
            Request request = new Request(command, session);

            SocketWorker socketWorker = new SocketWorker(socketAddress);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
            outObj.writeObject(request);
            session.setTypeOfSession(TypeOfSession.Login);
            return socketWorker.sendRequest(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            //return errText("util.Request can't be serialized, call programmer!");
            return null;
        }
    }

    public Response send(UserCommand command, LabWork labWork) {
        if (labWork != null) {
            command.addLabWork(labWork);
            return send(command);
        } else {
            return null;
            //return errText("LabWork isn't exist, try again!");
        }
    }

    public void setRemoteHostSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public String getInformation() {

        return successText("\nCONNECTION STATUS:\n") +
                successText("--------------------------------------------------\n") +
                "Remote host address: " + successText(String.valueOf(socketAddress.getAddress())) + "\n" +
                "Remote host port:    " + successText(String.valueOf(socketAddress.getPort())) + "\n" +
                successText("--------------------------------------------------\n");
    }

    public boolean getSocketStatus() {
        return socketStatus;
    }

    public void setSocketStatus(boolean socketStatus) {
        this.socketStatus = socketStatus;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Response register(Session session) {
        setSession(session);
        return send(new UserCommand("register", ""));
    }

    public Response login(Session session) {
        setSession(session);
        return send(new UserCommand("login", ""));
    }

    public Session getSession() {
        return session;
    }
}
