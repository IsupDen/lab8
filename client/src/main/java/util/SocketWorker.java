package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static util.ResponseHandler.receive;
import static util.TextFormat.errText;

public class SocketWorker {
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;

    public SocketWorker(SocketAddress socketAddress) {
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            this.socketAddress = socketAddress;
        } catch (IOException e) {
            Console.getInstance().println(errText("Some problem's with network!"));
        }
    }

    public Response sendRequest(byte[] dataToSend) {

        try {
            ByteBuffer buf = ByteBuffer.wrap(dataToSend);
            do {
                datagramChannel.send(buf, socketAddress);
            } while (buf.hasRemaining());
            return receiveAnswer();
        } catch (IOException exception) {
            RequestHandler.getInstance().setSocketStatus(false);
            //return errText("Command didn't send, try again!");
            return null;
        }
    }

    public Response receiveAnswer() {

        long timeStart = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true) {
            if ((System.currentTimeMillis() - timeStart) < 5000) {
                try {
                    datagramChannel.receive(buffer);
                    if (buffer.position() != 0) {
                        return receive(buffer);
                    }
                } catch (IOException ignored) {}
            } else {
                RequestHandler.getInstance().setSocketStatus(false);
//                return errText("Server isn't available at the moment!" +
//                        "Please, select another remote host!");
                return null;
            }
        }
    }
}
