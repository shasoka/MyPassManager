package manager.model;

import manager.encoder.Encoder;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PassDbModel {

    private static final String CON_STR = "jdbc:sqlite:passwords.db";

    private static PassDbModel instance = null;

    private Connection connection;

    public static synchronized PassDbModel getInstance() throws SQLException {
            if (instance == null) {
                instance = new PassDbModel();
            }
            return instance;
    }

    private PassDbModel() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean checkPinFromDb(String pin) {

        String password = null;

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT password FROM passwords WHERE id = 0";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pin.equals(password);
    }

    public List<Password> getPasswords() {
        try (Statement statement = this.connection.createStatement()) {
            Encoder encoder = new Encoder();
            List<Password> passwords = new ArrayList<>();
            String query = "SELECT id, name, login, password FROM passwords WHERE id <> 0";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                passwords.add(new Password(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("password")));
//                        encoder.decrypt(resultSet.getString("password"))));
            }
            return passwords;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
