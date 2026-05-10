package Repository;

import Domain.Delay;
import Domain.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DelayRepository implements Repository<Delay> {
    private RouteRepository routeRepo = new RouteRepository();

    @Override
    public int add(Delay entity) {
        String sql = "INSERT INTO Delays(route_id, delay_minutes, date_reported) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, entity.route().id());
            pstmt.setInt(2, entity.delayMinutes());
            pstmt.setString(3, entity.dateReported());
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
    public void remove(int id) {}
    @Override
    public void update(Delay entity) {}
    @Override
    public Delay getById(int id) { return null; }
    
    @Override
    public List<Delay> getAll() {
        List<Delay> delays = new ArrayList<>();
        String sql = "SELECT * FROM Delays";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Route r = routeRepo.getById(rs.getInt("route_id"));
                delays.add(new Delay(rs.getInt("id"), r, rs.getInt("delay_minutes"), rs.getString("date_reported")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delays;
    }
}
