package util;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RequestHandler{

    private final Invoker invoker;
    private final ExecutorService threadPool;

    public RequestHandler(Invoker invoker, ExecutorService threadPool) {
        this.invoker = invoker;
        this.threadPool = threadPool;
    }

    public void process(Request request, DatagramSocket datagramSocket, SocketAddress socketAddress) {
        try {
            Task task = new Task(invoker, request);
            Future<Response> future = threadPool.submit(task);
            Response response = future.get();
            Deliver deliver = new Deliver(datagramSocket, response, socketAddress);
            Thread deliverManager = new Thread(deliver);
            deliverManager.start();
        } catch (ExecutionException | InterruptedException ignored) {
        }
    }
}
