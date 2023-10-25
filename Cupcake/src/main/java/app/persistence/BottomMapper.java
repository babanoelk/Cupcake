package app.persistence;

import app.entities.Bottom;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BottomMapper {
    public static List<Bottom> getAllBottoms(ConnectionPool connectionPool) throws DatabaseException {
        List<Bottom> bottoms = new ArrayList<>();

        String sql = "select * from bottom order by bottom_id ASC";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("bottom_id");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    bottoms.add(new Bottom(id, name, price));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl!!!!");
        }
        return bottoms;
    }

    public static Bottom getBottomById(int bottomId, ConnectionPool connectionPool) throws DatabaseException {

        Bottom bottom = null;

        String sql = "select * from bottom where bottom_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, bottomId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("bottom_id");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    bottom = new Bottom(id, name, price);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af task med id = " + bottomId);
        }
        return bottom;
    }
}

