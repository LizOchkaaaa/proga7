package org.example.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class ClientWorker {

    private final SocketAddress socketAddress;
    private DatagramSocket datagramSocket;
    private final ResponseHandler responseHandler;

    public ClientWorker(SocketAddress aSocketAddress) {
        responseHandler = ResponseHandler.getInstance();
        socketAddress = aSocketAddress;
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(socketAddress);
        } catch (IOException e) {
           System.out.println("Some problem's with network!");
        }
    }

    public String sendRequest(byte[] dataToSend) {


        try {

            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length);
            packet.setAddress(InetAddress.getLocalHost());
            packet.setPort(43414);
            datagramSocket.send(packet);
            return responseHandler.receive(receiveAnswer());
        } catch (IOException exception) {
            RequestHandler.getInstance().setSocketStatus(false);
            return ("Command didn't send, try again");
        }
    }

    public String receiveAnswer() {

        long timeStart = System.currentTimeMillis();
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            if ((System.currentTimeMillis() - timeStart) < 5000) {
                try {
                    datagramSocket.receive(packet);
                    if (packet.getLength() != 0) {
                        return ResponseHandler.getInstance().receive(ByteBuffer.wrap(packet.getData()));
                    }
                } catch (IOException ignored) {
                    return ("Server doesn't exist!\nWait until the server is up and running ");
                }
            } else {
                return ("Server isn't available at the moment! " +
                        "Please, select another remote host!");
            }
        }
    }
}


