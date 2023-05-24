package manager.model;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static manager.encoder.Encoder.decrypt;
import static manager.encoder.Encoder.encrypt;

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
            String query = "SELECT password FROM passdb WHERE id = 0";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                password = resultSet.getString("password");
                System.out.println("Got pin from db.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            return pin.equals(decrypt(password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getTableSize() {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT COUNT(*) AS count FROM passdb";
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
            String query = "SELECT id, name, login, password FROM passdb WHERE id <> 0";
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

    public void addPassword(String name, String login, String password, int id) {
        String query = "INSERT INTO passdb (id, name, login, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, login);
            statement.setString(4, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Row successfully added.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePassword(int id) {
        String query = "DELETE FROM passdb WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
                System.out.println("Row successfully deleted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(int id, String name, String login, String password) {
        String query = "UPDATE passdb SET name = ?, login = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, login);
            statement.setString(3, encrypt(password));
            statement.setInt(4, id);
            int rowsUpd = statement.executeUpdate();
            if (rowsUpd > 0) {
                System.out.println("Row successfully edited.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
