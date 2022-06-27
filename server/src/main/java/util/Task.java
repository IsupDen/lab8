package util;

import java.util.concurrent.Callable;

public class Task implements Callable<Response> {

    private final Invoker invoker;
    private final Request request;

    public Task(Invoker invoker, Request request) {
        this.invoker = invoker;
        this.request = request;
    }

    @Override
    public Response call() {
        return invoker.execute(request);
    }
}
