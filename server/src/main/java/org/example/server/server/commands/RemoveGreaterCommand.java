package org.example.server.server.commands;

import org.example.Request;
import org.example.Response;
import org.example.interfaces.Execute;
import org.example.models.StudyGroup;
import org.example.server.server.Receiver;

public class RemoveGreaterCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public RemoveGreaterCommand(Receiver receiver) {
        super("remove_greater", "remove from the collection all elements greater than the given", 0 , "{element}", true , true);
        this.receiver = receiver;

    }
    @Override
    public Response execute(Request request){
        StudyGroup studyGroup = request.getCommand().getStudyGroup();
        return new Response(receiver.removeGreater(studyGroup));
    }
}
