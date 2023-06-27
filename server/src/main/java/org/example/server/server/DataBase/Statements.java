package org.example.server.server.DataBase;

public enum Statements {

    addStudyGroup("INSERT INTO s368864StudyGroup (id , name , xCoordinate , yCoordinate , creationDate, studentsCount , formOfEducation , " +
                          "semesterEnum , groupAdminName , groupAdminBirthday , groupAdminWeight ,groupAdminPassportId , groupAdminHairColor , author) "
                            + "VALUES(? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
    generateId("SELECT nextval('ids')"),

    addUserWithPassword("INSERT INTO s368864Users (username , hashPassword) VALUES(? , ?)"),

     checkUser("SELECT * FROM s368864Users WHERE username=? AND hashPassword=?"),

     updateStudyGroup("UPDATE s368864StudyGroup SET name=?, xCoordinate=?, yCoordinate=?, creationDate=?, studentsCount=?, formOfEducation=?, " +
                              "semesterEnum=?, groupAdminName=?, groupAdminBirthday=?, groupAdminWeight=?, groupAdminPassportId=?, groupAdminHairColor=? WHERE id = ?"),

     getById("SELECT * FROM s368864StudyGroup WHERE ID= ?"),

     deleteById("DELETE FROM s368864StudyGroup WHERE ID= ?"),

     clearAllByUser("DELETE FROM s368864StudyGroup WHERE author= ?"),

     takeAll("SELECT * FROM s368864StudyGroup");


    private final String statement;


    Statements(String statement1){
        statement = statement1;
    }

    public String getStatement(){
        return statement;
    }
}
