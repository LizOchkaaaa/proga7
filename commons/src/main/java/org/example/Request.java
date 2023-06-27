package org.example;

import java.io.Serializable;

public class Request implements Serializable {
    private final CommandFactory command;
    private Session session;

    public Request(CommandFactory Acommand , Session Asession) {
        command = Acommand;
        session = Asession;
    }

    public CommandFactory getCommand(){
        return command;
    }

    public Session getSession(){
        return session;
    }

    @Override
    public String toString() {
        return command.toString() + "from (" + session.toString() + ")";
    }


}
