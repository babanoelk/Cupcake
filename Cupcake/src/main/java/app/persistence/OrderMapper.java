package app.persistence;

import app.entities.Account;
import app.entities.Order;
import app.entities.Orderline;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.List;

public class OrderMapper {


    public static Order addOrder(Account account, List<Orderline> orderlines, ConnectionPool connectionPool) throws DatabaseException {

            Order newOrder = null;

            String sql = "INSERT INTO customer_order (order_date, total_amount, account_id) VALUES (current_date, ?, ?)";

            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
                {
                    int result = 0;

                    for (Orderline orderline: orderlines) {
                        result += orderline.getAmount();
                    }

                    ps.setInt(1, result);
                    ps.setInt(2,account.getId());

                    int rowsAffected =  ps.executeUpdate();

                    if (rowsAffected == 1)
                    {
                        ResultSet rs = ps.getGeneratedKeys();
                        rs.next();
                        int newId = rs.getInt(1);
                        Date date = rs.getDate(2);
                        newOrder = new Order(newId, date, result, orderlines);
                    } else {
                        throw new DatabaseException("Fejl under indsætning af task: ");
                    }
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl i DB connection");
            }
            return newOrder;
        }

        public static void addOrderline(ConnectionPool connectionPool) throws DatabaseException{

        }
    }

