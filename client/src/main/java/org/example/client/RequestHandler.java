package org.example.client;

import org.example.CommandFactory;
import org.example.Request;
import org.example.Session;
import org.example.TypeOfSession;
import org.example.models.StudyGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.util.ArrayList;

public class RequestHandler {
    private static RequestHandler instance;
    private SocketAddress socketAddress;
    private boolean socketStatus;
    private Session session;

    public static RequestHandler getInstance(){
        if(instance == null) instance = new RequestHandler();
        return instance;
    }

    private RequestHandler() {
    }

    public String send(CommandFactory commandFactory) {
        try {
            Request request = new Request(commandFactory, session);
            ClientWorker clientWorker = new ClientWorker(socketAddress);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(request);
            session.setTypeofSession(TypeOfSession.Login);
            return clientWorker.sendRequest(byteArrayOutputStream.toByteArray());
        }  catch (IOException e) {
            return "Request can not be serialized, call programmer";
        }
    }

    public String send(CommandFactory request , StudyGroup studyGroup){
        if(studyGroup != null) {
            request.addStudyGroup(studyGroup);
            return  send(request);
        }
        return "Study group isn't exist";
    }

    public void setRemoteHostSocketAddress(SocketAddress aSocketAddress){
        socketAddress = aSocketAddress;
    }

    public String getInformation(){
        return "Connection\n" +  "remote host address: " + socketAddress;
    }

    public void setSocketStatus(boolean b) {
        socketStatus = b;
    }

    public boolean getSocketStatus(){
        return socketStatus;
    }

    public void setSession(Session aSession) {
        session = aSession;
    }

    public String register(Session aSession) {
        setSession(aSession);
        return send(new CommandFactory("register", new ArrayList<String>()));
    }

    public String login(Session aSession) {
        setSession(aSession);
        return send(new CommandFactory("login", new ArrayList<>()));
    }
}
