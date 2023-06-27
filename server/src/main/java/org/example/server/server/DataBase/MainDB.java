package org.example.server.server.DataBase;

import org.example.TypeOfAnswer;
import org.example.models.StudyGroup;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class MainDB {
    private final Connection database;
    private final MessageDigest digest;
    public static final Logger logger = getLogger("Receiver");



    public MainDB(Connection connection) throws NoSuchAlgorithmException {
        database = connection;
        digest = MessageDigest.getInstance("SHA-384");
    }

    public ResultSet getCollection() {
        try {
            PreparedStatement preparedStatement = database.prepareStatement(Statements.takeAll.getStatement());
            return preparedStatement.executeQuery();
        }catch (SQLException throwables){
            logger.info("SQL problem with taking all collection!");
            return null;
        }
    }

    public Integer addStudyGroup(StudyGroup aStudyGroup) {
        try{
            PreparedStatement preparedStatement = database.prepareStatement(Statements.addStudyGroup.getStatement());
            Integer newId = setStudyGroupToStatement(preparedStatement, aStudyGroup);
            preparedStatement.executeUpdate();
            return (newId == null) ? 0 : newId;
        } catch (SQLException throwables) {
            logger.info("SQL problem with adding new element!");
            return 0;
        }
    }

    public TypeOfAnswer updateId(StudyGroup astudyGroup , Integer aId) {
        try {
            TypeOfAnswer status = getById(aId , astudyGroup.getAuthor());
            if (!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;
            PreparedStatement preparedStatement = database.prepareStatement(Statements.updateStudyGroup.getStatement());
            setUpdatedStudyGroupToStatement(preparedStatement, astudyGroup, aId);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        }catch (SQLException throwables){
            logger.info("SQL problem with updating element !");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer removeById(int aId , String aUsername){
        try {
            TypeOfAnswer status = getById(aId , aUsername);
            if(!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;

            PreparedStatement preparedStatement = database.prepareStatement(Statements.deleteById.getStatement());
            System.out.println(aId);
            preparedStatement.setInt(1 , aId);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch (SQLException throwables){
            logger.info("SQL problem with removing element!");
            return TypeOfAnswer.SQLPROBLEM;
        }
        return TypeOfAnswer.SUCCESSFUL;
    }

    public TypeOfAnswer getById(int aId , String aUsername){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = database.prepareStatement(Statements.getById.getStatement());
            preparedStatement.setInt(1 , aId);
            ResultSet deletingStudyGroup = preparedStatement.executeQuery();

            if(!deletingStudyGroup.next()) return TypeOfAnswer.OBJECTNOTEXIST;
            if(!deletingStudyGroup.getString("author").equals(aUsername)) return TypeOfAnswer.PERMISSIONDENIED;

            return TypeOfAnswer.SUCCESSFUL;
        }catch (SQLException throwables) {
            logger.info("SQL problem with getting element!");
            return TypeOfAnswer.SQLPROBLEM;
        }

    }

    public TypeOfAnswer clear(String username){
        try {
            PreparedStatement preparedStatement = database.prepareStatement(Statements.clearAllByUser.getStatement());
            preparedStatement.setString(1 , username);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        }catch (SQLException throwables) {
            logger.info("SQL problem with removing elements!");
            return TypeOfAnswer.SQLPROBLEM;
        }

    }

    public boolean addUser(String aUsername , String apassword){
        try {
            PreparedStatement preparedStatement = database.prepareStatement(Statements.addUserWithPassword.getStatement());
            preparedStatement.setString(1 , aUsername);
            preparedStatement.setBytes(2 , getHash(apassword));
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException throwables){
            logger.info("SQL problem with adding user!");
            return false;
        }

    }

    public boolean loginUser(String aUsername , String apassword){
        try {
            PreparedStatement preparedStatement = database.prepareStatement(Statements.checkUser.getStatement());
            preparedStatement.setString(1 , aUsername);
            preparedStatement.setBytes(2 , getHash(apassword));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException throwables){
            logger.info("SQL problem with logging user!");
            return false;
        }
    }

    private Integer generateId(){
        try {
            Statement statements = database.createStatement();
            ResultSet resultSet = statements.executeQuery(Statements.generateId.getStatement());
            if(resultSet.next()){
                return resultSet.getInt("nextval");
            }
            return null;
        } catch (SQLException throwables){
            logger.info("SQL problem with generating id!");
            return null;
        }
    }

    private Integer setStudyGroupToStatement(PreparedStatement statement , StudyGroup studyGroup) throws SQLException {
        Integer newid = generateId();
        if(newid == null) return null;

        studyGroup.setId(newid);
        statement.setInt(1 , studyGroup.getId());
        statement.setString(2 , studyGroup.getName());
        statement.setDouble(3 , studyGroup.getCoordinates().getX());
        statement.setInt(4 , studyGroup.getCoordinates().getY());
        statement.setDate(5, Date.valueOf(studyGroup.getCreationDate().toLocalDate()));
        statement.setInt(6 , studyGroup.getStudentsCount());
        if(studyGroup.getFormOfEducation() == null) {
           statement.setNull(7 , Types.VARCHAR);
        }else {
            statement.setString(7 , studyGroup.getFormOfEducation().toString());
        }
        if(studyGroup.getSemesterEnum() == null){
            statement.setNull(8 , Types.VARCHAR);
        }else {
            statement.setString(8 , studyGroup.getSemesterEnum().toString());
        }
        statement.setString(9 , studyGroup.getGroupAdmin().getName());
        statement.setDate(10 , Date.valueOf(studyGroup.getGroupAdmin().getBirthday().toLocalDate()));
        statement.setLong(11 , studyGroup.getGroupAdmin().getWeight());
        statement.setString(12 , studyGroup.getGroupAdmin().getPassportID());
        statement.setString(13 , studyGroup.getGroupAdmin().getHairColor().toString());
        statement.setString(14 , studyGroup.getAuthor());
        System.out.println(statement.toString());

        return newid;

    }

    private void setUpdatedStudyGroupToStatement(PreparedStatement statement , StudyGroup studyGroup, Integer id) throws SQLException {
        studyGroup.setId(id);
        statement.setString(1 , studyGroup.getName());
        statement.setDouble(2 , studyGroup.getCoordinates().getX());
        statement.setInt(3 , studyGroup.getCoordinates().getY());
        statement.setDate(4, Date.valueOf(studyGroup.getCreationDate().toLocalDate()));
        statement.setInt(5 , studyGroup.getStudentsCount());
        if(studyGroup.getFormOfEducation() == null) {
            statement.setNull(6 , Types.VARCHAR);
        }else {
            statement.setString(6 , studyGroup.getFormOfEducation().toString());
        }
        if(studyGroup.getSemesterEnum() == null){
            statement.setNull(7 , Types.VARCHAR);
        }else {
            statement.setString(7 , studyGroup.getSemesterEnum().toString());
        }
        statement.setString(8 , studyGroup.getGroupAdmin().getName());
        statement.setDate(9 , Date.valueOf(studyGroup.getGroupAdmin().getBirthday().toLocalDate()));
        statement.setInt(10 , studyGroup.getGroupAdmin().getWeight());
        statement.setString(11 , studyGroup.getGroupAdmin().getPassportID());
        statement.setString(12 , studyGroup.getGroupAdmin().getHairColor().toString());
        statement.setInt(13 , studyGroup.getId());
        System.out.println(statement.toString());
    }

    private byte[] getHash(String string) {
        return (string == null)
                ? digest.digest("null".getBytes(StandardCharsets.UTF_8))
                : digest.digest(string.getBytes(StandardCharsets.UTF_8));
    }
}
