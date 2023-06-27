package org.example.server.server.commands;

import org.example.Request;
import org.example.Response;
import org.example.TypeOfAnswer;
import org.example.interfaces.Execute;
import org.example.models.StudyGroup;
import org.example.server.server.Receiver;

public class AddIfMaxCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public AddIfMaxCommand(Receiver receiver) {
        super("add_if_max", "add a new element to the collection if its value is greater than the value of the largest element in this collection", 0 , "{element}" , true, true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        StudyGroup studyGroup = request.getCommand().getStudyGroup();
        String username = request.getSession().getName();
        TypeOfAnswer status = receiver.addIfMax(studyGroup);
        status.equals(TypeOfAnswer.SUCCESSFUL);
        return new Response(status);
    }
    }
