package org.helal_anwar.quiz_time.app;

import java.sql.*;

public class DatabaseLoader {
    private final String root_path = System.getProperty("user.home");
    private Connection connection;
    String name;

    public DatabaseLoader(String name) {
        this.name = name;
        System.out.println(name);
        init();
    }

    private void init() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + root_path + "\\" + this.name + ".db");

            System.out.println("Opened database successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createTable() {
        try {
            getStatement().executeUpdate(new SqlFileReader("login_data.sql").getQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(String username, String password, String bestScore) {
        String sql = "INSERT INTO user(user_name, password, best_score) VALUES(?, ?, ?)";

        try (PreparedStatement pst = getConnection().prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, bestScore);
            pst.executeUpdate();
            System.out.println("User '" + username + "' inserted successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_name = ?";

        try (PreparedStatement pst = getConnection().prepareStatement(sql)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean isValidUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_name = ? AND password = ?";

        try (PreparedStatement pst = getConnection().prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
