package org.example.server.server;

import org.example.models.StudyGroup;

import java.util.Date;
import java.util.Stack;

/** A class that contains a collection and data about the collection */
public class LocalDateBase {
    private Stack<StudyGroup> mainCollection;
    private Date dateOfLastChange;
    private final Date dateOfInitialization;

    public LocalDateBase() {
        dateOfInitialization = new Date();
        dateOfLastChange = new Date();
        mainCollection = new Stack<StudyGroup>();
    }

    public Stack<StudyGroup> getMainCollection() {
        return mainCollection;
    }

    public void setMainCollection(Stack<StudyGroup> mainCollection) {
        this.mainCollection = mainCollection;
    }

    public Date getDateOfLastChange() {
        return dateOfLastChange;
    }

    public Date getDateOfInitialization() {
        return dateOfInitialization;
    }

    public void setDateOfLastChange(Date date){
        this.dateOfLastChange = date;
    }

    public void add(StudyGroup studyGroup){
        mainCollection.add(studyGroup);
    }
    public StudyGroup getId(Integer key) {
        return mainCollection
                .stream()
                .filter(sg -> sg.getId().equals(key))
                .findAny()
                .orElse(null);
    }
    public void clear(String username) {
        mainCollection
                .stream()
                .filter(sg -> sg != null && sg.getAuthor().equals(username))
                .forEach(sg -> mainCollection.remove(sg));
    }
}
