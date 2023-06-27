package org.example.server.server;

import org.example.MainServer;
import org.example.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class RequestReceiver implements Runnable{
    private InetSocketAddress address;
    private final DatagramChannel channel;
    private final RequestHandler requestHandler;
    private byte[] buffer;
    private final Logger logger = MainServer.logger;

    public RequestReceiver(InetSocketAddress asocket, DatagramChannel achannel , Invoker ainvoker , Executor aDeliverManager, ExecutorService executor) throws SocketException {
        address = asocket;
        channel = achannel;
        requestHandler = new RequestHandler(ainvoker , aDeliverManager, executor);
        buffer = new byte[4096];

    }

    @Override
    public void run() {
        while (true) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
                do {
                    address = (InetSocketAddress) channel.receive(byteBuffer);
                } while (address == null);
                ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
                Request request = AutoGenFieldsSetter.setFields((Request) inputStream.readObject());
                logger.info("Server received command: {}" + request);

                requestHandler.process(request, channel, address);
            } catch (ClassNotFoundException e) {
                logger.info("Client sent outdated request");
            } catch (IOException e) {
                logger.info("Some problem's with getting request");
            }
        }
    }
}
