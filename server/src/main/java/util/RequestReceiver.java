package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class RequestReceiver implements Runnable{

    final Logger logger = Logger.getLogger(RequestReceiver.class.getCanonicalName());
    private final DatagramPacket datagramPacket;
    private final DatagramSocket datagramSocket;
    private final RequestHandler requestHandler;

    public RequestReceiver(DatagramSocket datagramSocket, DatagramPacket datagramPacket,
                           Invoker invoker, ExecutorService threadPool) {
        this.datagramSocket = datagramSocket;
        requestHandler = new RequestHandler(invoker, threadPool);
        this.datagramPacket = datagramPacket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()));
            Request request = AutoFieldsSetter.setFields((Request) inObj.readObject());

            requestHandler.process(request, datagramSocket, datagramPacket.getSocketAddress());
        } catch (ClassNotFoundException e) {
            logger.severe("Client sent outdated request!");
        } catch (IOException e) {
            logger.severe("Some problem's with getting request!");
        }
    }
}
