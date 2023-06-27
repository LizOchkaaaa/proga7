package org.example.server.server.commands;

import org.example.TypeOfAnswer;
import org.example.models.StudyGroup;
import org.example.server.server.Receiver;
import org.example.interfaces.Execute;
import org.example.Request;
import org.example.Response;

import java.util.Stack;

public class ShowCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public ShowCommand(Receiver receiver) {
        super("show", "print to standard output all elements of the collection in string representation", 0 , "" , false , true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        Stack<StudyGroup> studyGroups = receiver.show();
        if (studyGroups == null) return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        else return new Response(studyGroups, TypeOfAnswer.SUCCESSFUL);
    }
}
