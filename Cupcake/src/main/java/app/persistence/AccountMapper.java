package app.persistence;

import app.entities.Account;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {

    public static Account login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "SELECT * from account where email = ? and password = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int accountID = rs.getInt("account_id");
                    String name = rs.getString("name");
                    boolean isAdmin = rs.getBoolean("admin");
                    int balance = rs.getInt("balance");
                    return new Account(accountID, name, email, password, isAdmin, balance);
                } else {
                    throw new DatabaseException("Fejl i login. Prøv igen");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(""+e);
        }
    }
    public static void createAccount(String name, String email, String password, ConnectionPool connectionPool) throws DatabaseException{
        String sql = "insert into account (name, email, password, admin, balance) values (?,?,?, false, 500)";
        try (Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Der opstod en fejl under oprettelsen af brugeren");
                }
            }

        } catch (SQLException e){
            String msg = "Der er opstod en fejl. Prøv igen tak";
            if (e.getMessage().startsWith("Fejl: Allerede oprettet. ")){
                msg = "Email'en er allerede registreret. Prøv igen eller kontakt kundeservice.";
            }
            throw new DatabaseException(msg);
        }


    }
}

