package org.example;

import org.example.models.StudyGroup;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Stack;

public class Response implements Serializable {
    private String information;
    private StudyGroup studyGroup;
    private Map<String, String> information1 = null;
    private Stack<StudyGroup> stackOfStudyGroups = null;
    private final TypeOfAnswer status;
    private Long count = null;
    private Stack<StudyGroup> collection;

    public Response(StudyGroup aStudyGroup, TypeOfAnswer Astatus) {
        studyGroup = aStudyGroup;
        status = Astatus;
    }
    public Response(Stack<StudyGroup> studyGroups, TypeOfAnswer Astatus){
        stackOfStudyGroups = studyGroups;
        status = Astatus;
    }

    public Response(String anInformation, TypeOfAnswer Astatus){
         information = anInformation;
         status = Astatus;
    }

    public Response(TypeOfAnswer Astatus, Stack<StudyGroup> aCollection) {
        status = Astatus;
        collection = aCollection;
    }
    public Response(TypeOfAnswer Astatus){
        status = Astatus;
    }

    public Response(String astring){
        information = astring;
        status = null;
    }



    public TypeOfAnswer getStatus(){
        return status;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public Stack<StudyGroup> getCollection() {
        return collection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (information != null)
            sb.append(information);

        if (studyGroup != null)
            sb.append(studyGroup).append("\n");

        if (collection != null)
            collection.stream().sorted(Comparator.comparing(StudyGroup::getCoordinates)).
                    forEach(sg -> sb.append(sg).append("\n"));
        return sb.toString();
    }

    public Map<String, String> getInformation() {
        return information1;
    }
    public String getInfo(){
        return information;
    }

    public Stack<StudyGroup> getStackOfStudyGroups() {
        return stackOfStudyGroups;

    }

    public Long getCount() {
        return count;
    }
}
