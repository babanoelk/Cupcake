package app.persistence;

import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ToppingMapper {
    public static List<Topping> getAllToppings(ConnectionPool connectionPool) throws DatabaseException
    {
        List<Topping> toppings = new ArrayList<>();

        String sql = "select * from topping order by topping_id ASC";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();

                while (rs.next())
                {
                    int id = rs.getInt("topping_id");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    toppings.add(new Topping(id, name, price));
                }
            }

        }
        catch (SQLException e) {
            throw new DatabaseException("Fejl!!!!");
        }
        return toppings;
    }

    public static Topping getToppingById(int toppingId, ConnectionPool connectionPool) throws DatabaseException {

            Topping topping = null;

            String sql = "select * from topping where topping_id = ?";
            try (Connection connection = connectionPool.getConnection())
            {
                try (PreparedStatement ps = connection.prepareStatement(sql))
                {
                    ps.setInt(1, toppingId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next())
                    {
                        int id = rs.getInt("topping_id");
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        topping = new Topping(id, name, price);
                    }
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl ved hentning af topping med id = " + toppingId);
            }
            return topping;
        }
    }

