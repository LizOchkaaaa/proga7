package org.example.server.server.commands;

import org.example.Request;
import org.example.Response;
import org.example.interfaces.Execute;
import org.example.models.StudyGroup;
import org.example.server.server.Receiver;

public class UpdateIdCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;;
    public UpdateIdCommand(Receiver receiver) {
        super("update", "update the value of the collection element whose" +
                " id is equal to the given one", 1 , "id {element}" , true , true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        StudyGroup upgradedGroup = request.getCommand().getStudyGroup();
        int id = Integer.parseInt(request.getCommand().getArg().get(0));
        return new Response(receiver.updateId(upgradedGroup, id));
    }

}

