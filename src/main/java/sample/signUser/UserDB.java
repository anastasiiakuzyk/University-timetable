package sample.signUser;

import java.sql.*;

public class UserDB {
    public static final String USER_TABLE = "users";
    public static final String USERS_ID = "idusers";
    public static final String USERS_FIRSTNAME = "firstname";
    public static final String USERS_LASTNAME = "lastname";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String dbHost = "localhost";
        String dbPort = "1433";
        String dbUser = "tester";
        String dbPass = "pass";
        String dbName = "rozkladDB";
        String connectionString = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s;"
                , dbHost, dbPort, dbName, dbUser, dbPass);
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        dbConnection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to " + dbName);
        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + USER_TABLE + "(" +
                USERS_FIRSTNAME + "," + USERS_LASTNAME + "," +
                USERS_USERNAME + "," + USERS_PASSWORD + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + USER_TABLE + " WHERE " +
                USERS_USERNAME + "=? AND " + USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
}
