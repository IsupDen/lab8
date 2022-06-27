package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreator {

    private final Connection dbConnection;

    public  DBCreator(Connection dbConnection) {this.dbConnection = dbConnection;}

    public void create() throws SQLException {
        Statement statement = dbConnection.createStatement();
        statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");
        statement.executeUpdate("CREATE TABLE  IF NOT EXISTS s335153LabWorks (" +
                "id int PRIMARY KEY," +
                "name varchar(255) CHECK (name<>'')," +
                "xCoordinate int," +
                "yCoordinate int," +
                "creationDate date DEFAULT (current_date)," +
                "creationTime time DEFAULT (current_time)," +
                "minimalPoint real CHECK (minimalPoint > 0)," +
                "personalQualitiesMinimum int CHECK (personalQualitiesMinimum > 0)," +
                "difficulty varchar(10) CHECK (difficulty='NORMAL' OR difficulty='INSANE' OR difficulty='TERRIBLE')," +
                "authorName varchar(255) CHECK (authorName<>'')," +
                "authorHeight bigint CHECK (authorHeight>0)," +
                "authorHairColor varchar(6) CHECK (authorHairColor='BLACK' OR authorHairColor='BLUE' OR " +
                "authorHairColor='ORANGE' OR authorHairColor='WHITE' OR authorHairColor='BROWN')," +
                "authorEyeColor varchar(6) CHECK (authorEyeColor='GREEN' OR authorEyeColor='BLUE' OR " +
                "authorEyeColor='ORANGE' OR authorEyeColor='WHITE' OR authorEyeColor='BROWN')," +
                "authorNationality varchar(11) CHECK (authorNationality='GERMANY' OR authorNationality='VATICAN' OR " +
                "authorNationality='ITALY' OR authorNationality='NORTH_KOREA')," +
                "username varchar(255))");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS s335153users (" +
                "login varchar(255) PRIMARY KEY," +
                "hashPassword BYTEA," +
                "salt BYTEA)");
    }
}
