package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.logging.Logger;

public class Deliver implements Runnable {

    final Logger logger = Logger.getLogger(Deliver.class.getCanonicalName());

    private final DatagramSocket datagramSocket;
    private final Response answer;
    private final SocketAddress socketAddress;

    public Deliver(DatagramSocket datagramSocket, Response answer, SocketAddress socketAddress) {
        this.datagramSocket = datagramSocket;
        this.answer = answer;
        this.socketAddress = socketAddress;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
            outObj.writeObject(answer);

            DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(),
                    byteArrayOutputStream.size(), socketAddress);
            datagramSocket.send(packet);
        } catch (IOException e) {
            logger.severe("Some troubles with sending answer!");
        }
    }
}
