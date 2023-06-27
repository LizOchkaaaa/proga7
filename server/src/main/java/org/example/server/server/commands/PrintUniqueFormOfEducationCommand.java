package org.example.server.server.commands;

import org.example.TypeOfAnswer;
import org.example.server.server.Receiver;
import org.example.interfaces.Execute;
import org.example.Request;
import org.example.Response;

public class PrintUniqueFormOfEducationCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public PrintUniqueFormOfEducationCommand(Receiver receiver) {
        super("print_unique_form_of_education", "print the unique values of the" +
                "formOfEducation field of all elements in the collection", 1 , "" , false , true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        return new Response(receiver.printEnum(), TypeOfAnswer.SUCCESSFUL);
    }
}
