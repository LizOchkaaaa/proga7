package org.example.client;

import org.example.Response;
import org.example.TypeOfAnswer;
import org.example.models.StudyGroup;

import java.util.Comparator;

public class Animator {

    private static Animator instance;

    private Animator() {
    }

    public static Animator getInstance() {
        if (instance == null) instance = new Animator();
        return instance;
    }

    public String animate(Response aResponse) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        if (aResponse.getStatus().equals(TypeOfAnswer.SUCCESSFUL)) {
            if (aResponse.getInfo() != null){
                return sb.append(aResponse.getInfo()).toString();
            }
            if (aResponse.getInformation() != null) {
                if (aResponse.getInformation().get("1") == null) {
                    aResponse.getInformation().
                            forEach((key, value) -> sb.append("\t")
                                    .append(" : ")
                                    .append(value.toUpperCase())
                                    .append("\n\n"));
                } else {
                    aResponse.getInformation()
                            .keySet()
                            .stream()
                            .map(Integer::parseInt)
                            .sorted(Integer::compareTo)
                            .map(String::valueOf)
                            .forEach(key -> sb.append("\t")
                                    .append(" : ")
                                    .append(aResponse.getInformation().get(key).toUpperCase())
                                    .append("\n\n"));
                }
            }
            if (aResponse.getStackOfStudyGroups()!= null) {
                aResponse.getStackOfStudyGroups().stream()
                        .sorted(Comparator.comparing(StudyGroup::getCoordinates))
                        .forEach(sg -> sb.append(sg).append("\n\n"));
            }
            if (aResponse.getStudyGroup() != null) {
                sb.append(aResponse.getStudyGroup().toString())
                        .append("\n");
            }
            if (aResponse.getCount() != null) {
                sb.append("\tAmount of elements: ")
                        .append("\n");
            }
            if (sb.toString().equals("\n")) return ("\n\tAction processed successful!\n");
        } else {
            switch (aResponse.getStatus()) {
                case OBJECTNOTEXIST:
                    return ("\n\tNo object with such parameters was found!\n");
                case DUPLICATESDETECTED:
                    return ("\tThis element probably duplicates " +
                            "existing one and can't be added\n");
                case ISNTMAX:
                    return ("\n\tStudy group isn't max!\n");
                case ISNTMIN:
                    return ("\n\tStudy group isn't min!\n");
                case PERMISSIONDENIED:
                    return ("\n\tPermission denied!\n");
                case SQLPROBLEM:
                    return ("\n\tSome problem's with database on server!\n");
                case EMPTYCOLLECTION:
                    return ("\n\tCollection is empty!\n");
                case ALREADYREGISTERED:
                    return ("\n\tThis account already registered!\n");
                case NOTMATCH:
                    return ("\n\tAccount with this parameters doesn't exist!\n");
                case NOGREATER:
                    return ("\n\tNo element greater!\n");
            }
        }
        return sb.toString();
    }
}