package database;

import data.LabWork;
import util.TypeOfAnswer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Random;
import java.util.logging.Logger;

public class DBWorker {

    final Logger logger = Logger.getLogger(DBWorker.class.getCanonicalName());

    private final Connection db;
    private final MessageDigest digest;

    private final Random random = new SecureRandom();

    public DBWorker(Connection connection) throws NoSuchAlgorithmException {
        db = connection;
        digest = MessageDigest.getInstance("SHA-1");
    }

    public ResultSet getCollection() {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.takeAll.getStatement());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.severe("SQL problem with taking all collection!");
            return null;
        }
    }

    public Integer addLabWork(LabWork labWork) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.addLabWork.getStatement());
            Integer newId = setLabWorkToStatement(preparedStatement, labWork);
            preparedStatement.executeUpdate();
            return (newId == null) ? 0 : newId;
        } catch (SQLException e) {
            logger.severe("SQL problem with adding new element!");
            return 0;
        }
    }

    public TypeOfAnswer updateById(LabWork labWork, int id) {
        try {
            TypeOfAnswer status = getByID(id, labWork.getUser());
            if (!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;
            PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.updateLabWork.getStatement());
            setUpdatedLabWorkToStatement(preparedStatement, labWork);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe("SQL problem with updating element !");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer removeById(int id, String user) {
        try (PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.deleteById.getStatement());) {
            TypeOfAnswer status = getByID(id, user);
            if (!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException e) {
            logger.severe("SQL problem with removing element!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer getByID(int id, String user) {
        try (PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.getById.getStatement())) {
            preparedStatement.setInt(1, id);
            ResultSet deletingLabWork = preparedStatement.executeQuery();
            if (!deletingLabWork.next()) return TypeOfAnswer.OBJECTNOTEXIST;
            if (!deletingLabWork.getString("username").equals(user)) return TypeOfAnswer.PERMISSIONDENIED;
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException e) {
            logger.severe("SQL problem with getting element!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer clear(String user) {
        try (PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.clearAllByUser.getStatement())) {
            preparedStatement.setString(1, user);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException e) {
            logger.severe("SQL problem with removing elements!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public boolean addUser(String user, String password) {
        try (PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.addUserWithPassword.getStatement())) {
            byte[] salt = new byte[6];
            random.nextBytes(salt);
            preparedStatement.setString(1, user);
            preparedStatement.setBytes(2, getHash(password, salt));
            preparedStatement.setBytes(3, salt);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.severe("SQL problem with adding user!");
            return false;
        }
    }

    public boolean loginUser(String user, String password) {
        byte[] salt;
        try (PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.getSalt.getStatement())) {
            preparedStatement.setString(1, user);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next())
                salt = result.getBytes("salt");
            else return false;
        } catch (SQLException e) {
            logger.severe("SQL problem with get user salt!");
            return false;
        }
        try (PreparedStatement preparedStatement = db.prepareStatement(SQLRequests.checkUser.getStatement())) {

            preparedStatement.setString(1, user);
            preparedStatement.setBytes(2, getHash(password, salt));
            ResultSet login = preparedStatement.executeQuery();
            return login.next();
        } catch (SQLException e) {
            logger.severe("SQL problem with logging user!");
            return false;
        }
    }

    private Integer generateId() {
        try (Statement statement = db.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLRequests.generateId.getStatement());
            if (resultSet.next()) return resultSet.getInt("nextval");
            return null;
        } catch (SQLException e) {
            logger.severe("SQL problem with generating id!");
            return null;
        }
    }

    private Integer setLabWorkToStatement(PreparedStatement statement, LabWork labWork) throws SQLException {
        Integer newId = generateId();
        if (newId == null) return null;

        labWork.setId(newId);
        statement.setInt(1, newId);
        statement.setString(2, labWork.getName());
        statement.setInt(3, labWork.getCoordinates().getX());
        statement.setInt(4, labWork.getCoordinates().getY());
        statement.setDouble(5, labWork.getMinimalPoint());
        if (labWork.getPersonalQualitiesMinimum() == null) statement.setNull(6, Types.INTEGER);
        else statement.setInt(6, labWork.getPersonalQualitiesMinimum());
        if (labWork.getDifficulty() == null) statement.setNull(7, Types.VARCHAR);
        else statement.setString(7, labWork.getDifficulty().toString());
        statement.setString(8, labWork.getAuthor().getName());
        statement.setDouble(9, labWork.getAuthor().getHeight());
        statement.setString(10, labWork.getAuthor().getHairColor().toString());
        statement.setString(11, labWork.getAuthor().getEyeColor().toString());
        statement.setString(12, labWork.getAuthor().getNationality().toString());
        statement.setString(13, labWork.getUser());

        return newId;
    }

    private void setUpdatedLabWorkToStatement(PreparedStatement statement, LabWork labWork) throws SQLException {
        labWork.setId(generateId());

        statement.setString(1, labWork.getName());
        statement.setInt(2, labWork.getCoordinates().getX());
        statement.setInt(3, labWork.getCoordinates().getY());
        statement.setDouble(4, labWork.getMinimalPoint());
        if (labWork.getPersonalQualitiesMinimum() == null) statement.setNull(5, Types.INTEGER);
        else statement.setInt(5, labWork.getPersonalQualitiesMinimum());
        if (labWork.getDifficulty() == null) statement.setNull(6, Types.VARCHAR);
        else statement.setString(6, labWork.getDifficulty().toString());
        statement.setString(7, labWork.getAuthor().getName());
        statement.setDouble(8, labWork.getAuthor().getHeight());
        statement.setString(9, labWork.getAuthor().getHairColor().toString());
        statement.setString(10, labWork.getAuthor().getEyeColor().toString());
        statement.setString(11, labWork.getAuthor().getNationality().toString());
        statement.setInt(12, labWork.getId());
    }

    private byte[] getHash(String password, byte[] salt) {
        digest.update("}aZy*}".getBytes(StandardCharsets.UTF_8));
        if ((password == null)) {
            digest.update("null".getBytes(StandardCharsets.UTF_8));
        } else {
            digest.update(password.getBytes(StandardCharsets.UTF_8));
        }
        digest.update(salt);
        return digest.digest();
    }

}
