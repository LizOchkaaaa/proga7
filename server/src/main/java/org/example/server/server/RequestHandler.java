package org.example.server.server;

import org.example.Request;
import org.example.Response;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RequestHandler{
    private final Invoker invoker;
    private final Executor deliver;
    private final ExecutorService cachedThreadPool;

    public RequestHandler(Invoker invokerNew, Executor deliverNew, ExecutorService executor){
        invoker = invokerNew;
        deliver = deliverNew;
        cachedThreadPool = executor;
    }


    public void process(Request request , DatagramChannel socket, InetSocketAddress address){
        Task task = new Task(invoker, request);
        Future<Response> responseFuture = cachedThreadPool.submit(task);

        Response response;
        try {
            response = responseFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }

        Deliver deliver1 = new Deliver(invoker , socket , address , response);
        deliver.execute(deliver1);
    }
}
