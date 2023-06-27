package org.example.server.server.commands;

import org.example.Request;
import org.example.Response;
import org.example.models.StudyGroup;
import org.example.interfaces.Execute;
import org.example.server.server.Receiver;

public class AddCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public AddCommand(Receiver receiver) {
        super("add", "add a new element to the collection" , 0 , "{element}" , true, true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        StudyGroup studyGroup = request.getCommand().getStudyGroup();
        return new Response(receiver.add(studyGroup));
    }

}
