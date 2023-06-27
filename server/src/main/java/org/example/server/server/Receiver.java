package org.example.server.server;

import org.example.TypeOfAnswer;
import org.example.models.*;
import org.example.server.server.DataBase.MainDB;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Stack;
import java.util.logging.Logger;

/**Execution class of all commands*/
public class Receiver {
    private Stack<StudyGroup> studyGroups;
    private final MainDB mainDB;
    private final Date date = null;
    private final Stack<Integer> usedId;
    private int highUsedId;
    public static final Logger logger = null;
    private final LocalDateBase localDateBase;

    public Receiver(MainDB mainDB, LocalDateBase localDateBase) {
        this.mainDB = mainDB;
        usedId = new Stack<>();
        this.localDateBase = localDateBase;
        highUsedId = 0;
        getCollection();

    }

    private void getCollection() {
        try {
            ResultSet data = mainDB.getCollection();
            while (data.next()) {
                localDateBase.
                add(new StudyGroup(
                        data.getInt(1),
                        data.getString(2),
                        new Coordinates(data.getDouble(3), data.getInt(4)),
                        data.getDate(5).toLocalDate().atStartOfDay(ZoneId.of("Europe/Moscow")),
                        data.getInt(6),
                        data.getString(7) != null ?
                                FormOfEducation.valueOf(data.getString(7)) : null,
                        Semester.valueOf(data.getString(8)),
                        new Person(data.getString(9),
                                data.getDate(10).toLocalDate().atStartOfDay(ZoneId.of("Europe/Moscow")),
                                data.getInt(11),
                                data.getString(12),
                                Color.valueOf(data.getString(13))),
                        data.getString(14)));
            }
        } catch (SQLException ignored) {

        }
    }



    public java.util.Date getDateOfInitialization() {
        return localDateBase.getDateOfInitialization();
    }

    public Stack<StudyGroup> getMainCollection() {
        return localDateBase.getMainCollection();
    }

    public java.util.Date getDateOfLastChange() {
        return localDateBase.getDateOfLastChange();
    }

    public void setDateOfLastChange() {
        localDateBase.setDateOfLastChange(new java.util.Date());
    }

    public LocalDateBase getLocalDateBase() {
        return localDateBase;
    }


    public TypeOfAnswer add(StudyGroup aStudyGroup) {
        Integer id = mainDB.addStudyGroup(aStudyGroup);

        if (id != 0) {
            getMainCollection().add(aStudyGroup.setId(id));
            return TypeOfAnswer.SUCCESSFUL;
        } else {
            return TypeOfAnswer.DUPLICATESDETECTED;
        }
    }

     public StudyGroup getId(Integer key) {
            return getMainCollection().stream().filter(sg -> sg.getId().equals(key)).findAny().orElse(null);
    }

    public TypeOfAnswer clearCollection(String anUsername) {
        TypeOfAnswer status = mainDB.clear(anUsername);
        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            localDateBase.clear(anUsername);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public TypeOfAnswer removeById(String anUsername, int anId) {
        TypeOfAnswer status = mainDB.removeById(anId, anUsername);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            StudyGroup studyGroup = getId(anId);
            getMainCollection().remove(studyGroup);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public Stack<StudyGroup> show() {
        if (getMainCollection().isEmpty()) return null;
        else return getMainCollection();
    }
    public String printEnum() {
        Stack<FormOfEducation> setOfFormOfEducation = new Stack<>();
        for (StudyGroup group : getMainCollection()) {
            if (!setOfFormOfEducation.contains(group.getFormOfEducation())) {
                setOfFormOfEducation.add(group.getFormOfEducation());
            }
        }
        return setOfFormOfEducation.toString();
    }
    public boolean remove(StudyGroup group){
         return getMainCollection().remove(group);
    }

    public String sumOfStudentsCount() {
        int sumOfStudentsCountValue = 0;
        Stack<StudyGroup> mainCollection = this.getMainCollection();
        for (StudyGroup group : getMainCollection()) {
            sumOfStudentsCountValue += group.getStudentsCount();
        }
        return "Sum of all StudentsCount in Study Group = " + sumOfStudentsCountValue;
    }




    public TypeOfAnswer updateId(StudyGroup anUpgradedGroup, int anId) {
        TypeOfAnswer status = mainDB.updateId(anUpgradedGroup, anId);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            StudyGroup studyGroup = localDateBase.getId(anId);
            getMainCollection().remove(studyGroup);
            anUpgradedGroup.setId(anId);
            getMainCollection().add(anUpgradedGroup);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }
    public TypeOfAnswer addIfMax(StudyGroup aStudyGroup) {
        if (getMax() != null && aStudyGroup.compareTo(getMax()) >= 0)
            return add(aStudyGroup);
        else return TypeOfAnswer.ISNTMAX;
    }

    public StudyGroup getMax() {
        return getMainCollection()
                .stream()
                .max(StudyGroup::compareTo)
                .orElse(null);
    }

    public synchronized TypeOfAnswer removeGreater(StudyGroup studyGroup){
        int countRemoved = 0;
        TypeOfAnswer status = null;
        for (StudyGroup studyGroups : this.getMainCollection()) {
            if (studyGroups.compareTo(studyGroup) < 0 && studyGroup.getAuthor().equals(studyGroups.getAuthor())) {
                synchronized (mainDB) {
                    try {
                        status = mainDB.removeById(studyGroups.getId(), studyGroup.getAuthor());
                        Thread.sleep(400);
                        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
                            remove(studyGroups);
                            countRemoved += 1;
                        }
                        mainDB.notify();
                    } catch (Exception e) {
                        return TypeOfAnswer.SQLPROBLEM;
                    }
                }
            }
        }
        if (countRemoved > 0){
            setDateOfLastChange();
        }else status = TypeOfAnswer.NOGREATER;
        return status;
    }

    public boolean registerUser(String username, String password) {
        return mainDB.addUser(username, password);
    }

    public boolean loginUser(String anUsername, String aPassword) {
        return mainDB.loginUser(anUsername, aPassword);
    }
}
