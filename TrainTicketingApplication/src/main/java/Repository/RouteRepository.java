package Repository;

import Domain.Route;
import Domain.Train;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository implements Repository<Route> {
    private TrainRepository trainRepo = new TrainRepository();

    @Override
    public int add(Route entity) {
        String sql = "INSERT INTO Routes(name, train_id) VALUES(?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.name());
            pstmt.setInt(2, entity.train().id());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM Routes WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Route entity) {
        String sql = "UPDATE Routes SET name = ?, train_id = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.name());
            pstmt.setInt(2, entity.train().id());
            pstmt.setInt(3, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Route getById(int id) {
        String sql = "SELECT * FROM Routes WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Train t = trainRepo.getById(rs.getInt("train_id"));
                return new Route(rs.getInt("id"), rs.getString("name"), t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Route> getAll() {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM Routes";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Train t = trainRepo.getById(rs.getInt("train_id"));
                routes.add(new Route(rs.getInt("id"), rs.getString("name"), t));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }
}
