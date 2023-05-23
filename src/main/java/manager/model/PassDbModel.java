package manager.model;

import manager.encoder.Encoder;
import org.sqlite.JDBC;

import static manager.encoder.Encoder.encrypt;
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
                System.out.println("Pin successfully checked.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pin.equals(password);
    }

    public int getTableSize() {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT COUNT(*) AS count FROM passwords";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                System.out.println("Size successfully counted.");
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Password> getPasswords() {
        try (Statement statement = connection.createStatement()) {
            List<Password> passwords = new ArrayList<>();
            String query = "SELECT id, name, login, password FROM passwords WHERE id <> 0";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Password password = new Password(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
                passwords.add(password);
                System.out.printf("Successfully got row: %s\n", password.toString());
            }
            return passwords;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void addPassword(String name, String login, String password) {
        String query = "INSERT INTO passwords (name, login, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, login);
            statement.setString(3, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Row successfully added.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
