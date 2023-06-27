package org.example.server.server.commands;

import org.example.Request;
import org.example.Response;
import org.example.interfaces.Execute;
import org.example.server.server.Receiver;

public class RemoveByIdCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public RemoveByIdCommand(Receiver receiver) {
        super("remove_by_id", "remove element from collection by its id", 1 , "id", false , true);
        this.receiver = receiver;

    }

    @Override
    public Response execute(Request request) {
        String username = request.getSession().getName();
        int id = Integer.parseInt(request.getCommand().getArg().get(0));
        return new Response(receiver.removeById(username, id));
    }
}
