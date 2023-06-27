package org.example.server.server.commands;

import org.example.Request;
import org.example.Response;
import org.example.TypeOfAnswer;
import org.example.server.server.Receiver;

public class RegisterUser extends AbstractCommand{
    private final Receiver receiver;

    public RegisterUser(Receiver receiver) {
        super("register", "add new user to system", 0, "", true , true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        String password = aRequest.getSession().getPassword();
        if (!receiver.loginUser(username, password)) {
            if (receiver.registerUser(username, password)) return new Response(TypeOfAnswer.SUCCESSFUL);
        }
        return new Response(TypeOfAnswer.ALREADYREGISTERED);
    }
}
