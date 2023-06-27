package org.example.server.server.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {

    private final Connection connectdb;

    public DBInitializer(Connection aconnectdb) {
        connectdb = aconnectdb;
    }

    public void initialize() throws SQLException {
        Statement statement = connectdb.createStatement();
        statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS s368864StudyGroup (" +
                "id int PRIMARY KEY," + "name varchar(255) NOT NULL CHECK(name<>'')," + "xCoordinate float ," +
                "yCoordinate int," + "creationDate date DEFAULT(current_date)," + "studentsCount int NOT NULL CHECK(studentsCount > 0)," +
                "formOfEducation varchar(50)" + "CHECK(formOfEducation='DISTANCE_EDUCATION' OR " + "formOfEducation='FULL_TIME_EDUCATION' OR " + "formOfEducation='EVENING_CLASSES')," +
                "semesterEnum varchar(6) NOT NULL " + "CHECK(semesterEnum='FIRST' OR " + "semesterEnum='SECOND' OR " + "semesterEnum='THIRD' OR " + "semesterEnum='FOURTH')," +
                "groupAdminName varchar(255) NOT NULL  CHECK(groupAdminName<>'')," + "groupAdminBirthday date NOT NULL," + "groupAdminWeight int NOT NULL CHECK(groupAdminWeight>0)," + "groupAdminPassportId varchar(255) NOT NULL CHECK(groupAdminPassportId<>'')," +
                "groupAdminHairColor varchar(6) NOT NULL CHECK(groupAdminHairColor='GREEN' OR " + "groupAdminHairColor='RED' OR " + "groupAdminHairColor='ORANGE' OR " + "groupAdminHairColor='WIGHT')," + "author varchar(255)" + ")");


        statement.executeUpdate("CREATE TABLE IF NOT EXISTS s368864Users (" +
                "userName varchar(255) PRIMARY KEY," + "hashPassword BYTEA DEFAULT (null)" + ")");
    }
}
