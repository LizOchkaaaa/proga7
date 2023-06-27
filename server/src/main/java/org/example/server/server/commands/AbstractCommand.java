package org.example.server.server.commands;

import org.example.interfaces.Execute;
import org.example.Request;
import org.example.Response;

public abstract class AbstractCommand implements Execute {
    private String name;
    private String discription;

    private Integer countOfInlineExtraArgs;

    private String fullname;
    private boolean isNeededElementFields;

    private final boolean isRequiredAuthorization;
    public AbstractCommand(String name, String description , Integer countOfInlineExtraArgs , String fullname , boolean isNeededElementFields, boolean anIsRequiredAuthorization) {
        this.name = name;
        this.discription = description;
        this.countOfInlineExtraArgs = countOfInlineExtraArgs;
        this.fullname = fullname;
        this.isNeededElementFields = isNeededElementFields;
        this.isRequiredAuthorization = anIsRequiredAuthorization;
    }

    public Integer getCountOfInlineExtraArgs() {
        return countOfInlineExtraArgs;
    }

    @Override
    public String toString() {
        if(fullname == ""){
            return name + " - " + discription;
        }
        return name + " " + fullname + " - " + discription;
    }

    public String getName() {
        return name;
    }

    public boolean isNeededElementFields() {
        return isNeededElementFields;
    }
    public boolean getAuthorizationStatus() {
        return isRequiredAuthorization;
    }


    @Override
    public abstract Response execute(Request aRequest);
}
