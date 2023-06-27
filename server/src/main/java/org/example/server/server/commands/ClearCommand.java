package org.example.server.server.commands;

import org.example.Request;
import org.example.Response;
import org.example.interfaces.Execute;
import org.example.server.server.Receiver;

public class ClearCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public ClearCommand(Receiver receiver) {
        super("clear", "clear the collection", 0 , "" , false, true);
        this.receiver = receiver;
    }


    @Override
    public Response execute(Request request) {
        String username = request.getSession().getName();
        return new Response(receiver.clearCollection(username));
    }
}
