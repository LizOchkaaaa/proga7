package org.example.server.server;

import org.example.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class Deliver implements Runnable {
    private final Invoker invoker;
    private byte[] buffer;
    private final DatagramChannel datagramChannel;
    private final  InetSocketAddress address;
    private final Response answer;
    public static final Logger logger = getLogger("Deliver");


    public Deliver(Invoker ainvoker, DatagramChannel datagramChannel, InetSocketAddress address, Response answer) {
        invoker = ainvoker;
        this.datagramChannel = datagramChannel;
        this.address = address;
        this.answer = answer;
    }

//    public Deliver(DatagramChannel socket, Response response, SocketAddress address, Invoker invoker, DatagramChannel datagramChannel, SocketAddress address1, Response answer) {
//        this.invoker = invoker;
//        this.datagramChannel = datagramChannel;
//        this.address = address1;
//        this.answer = answer;
//    }

//    public void answer(Request request , DatagramChannel aDatagramChannel, InetSocketAddress aSocketAddress) throws IOException, ExecutionException, InterruptedException {
//        Response response = invoker.execute(request);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
//        outObj.writeObject();
//
//        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
//        aDatagramChannel.send(buffer, aSocketAddress);
//        logger.info("Server send an answer!");
//
//    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
            outObj.writeObject(answer);

            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            datagramChannel.send(buffer, address);
            logger.info("Server send an answer!");
        }catch (IOException e){
            logger.info("Some troubles with sending answer!");
        }
    }
}
