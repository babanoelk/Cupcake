package app.persistence;

import app.entities.Account;
import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
            throw new DatabaseException("" + e);
        }
    }

    public static void createAccount(String name, String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into account (name, email, password, admin, balance) values (?,?,?, false, 500)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Der opstod en fejl under oprettelsen af brugeren");
                }
            }

        } catch (SQLException e) {
            String msg = "Der er opstod en fejl. Prøv igen tak";
            if (e.getMessage().startsWith("Fejl: Allerede oprettet. ")) {
                msg = "Email'en er allerede registreret. Prøv igen eller kontakt kundeservice.";
            }
            throw new DatabaseException(msg);
        }
    }

    public static List<Order> getAllOrdersByID(int accountID, ConnectionPool connectionPool) throws DatabaseException {

        List<Order> orders = new ArrayList<>();


        String sql = "select * from customer_order where account_id = ? order by order_id";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, accountID);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    int orderID = rs.getInt("order_id");
                    Date orderDate = rs.getDate("order_date");
                    int totalAmount = rs.getInt("total_amount");
                    orders.add(new Order(orderID, orderDate, totalAmount, accountID));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl!!!!");
        }
        return orders;
    }

    public static List<Account> getAllCustomers(ConnectionPool connectionPool) throws DatabaseException {

        List<Account> accountList = new ArrayList<>();

        String sql = "SELECT * from account order by account_id";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int accountID = rs.getInt("account_id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    boolean isAdmin = rs.getBoolean("admin");
                    int balance = rs.getInt("balance");

                    accountList.add(new Account(accountID, name, email, password, isAdmin, balance));

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl!!!!");
        }
        return accountList;
    }

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {

        List<Order> orders = new ArrayList<>();

        String sql = "select * from customer_order order by order_id";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int orderID = rs.getInt("order_id");
                    Date orderDate = rs.getDate("order_date");
                    int totalAmount = rs.getInt("total_amount");
                    int accountID = rs.getInt("account_id");
                    orders.add(new Order(orderID, orderDate, totalAmount, accountID));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl!!!!");
        }
        return orders;
    }

    public static void adjustBalance(int newBalance, Account account, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, newBalance);
                ps.setInt(2, account.getId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Der opstod en fejl under ved rettelse af balance");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


