package org.example.server.server.commands;

import org.example.TypeOfAnswer;
import org.example.models.StudyGroup;
import org.example.server.server.Receiver;
import org.example.interfaces.Execute;
import org.example.Request;
import org.example.Response;

import java.util.Stack;
import java.util.stream.Collectors;

public class ReorderCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public ReorderCommand(Receiver receiver) {
        super("reorder", "sort the collection in reverse order", 0 , "" , false , true);
        this.receiver = receiver;
    }
    @Override
    public Response execute(Request request) {
        if (receiver.getMainCollection().isEmpty()){
            return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        }
        return new Response((Stack<StudyGroup>) receiver.getMainCollection().stream().sorted(StudyGroup::compareTo).collect(Collectors.toCollection(Stack::new)), TypeOfAnswer.SUCCESSFUL);
    }
}
