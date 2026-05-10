package Repository;

import Domain.RouteStop;
import Domain.Station;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteStopRepository implements Repository<RouteStop> {
    private StationRepository stationRepo = new StationRepository();

    @Override
    public int add(RouteStop entity) {
        String sql = "INSERT INTO RouteStops(route_id, station_id, arrival_time, departure_time, stop_order) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, entity.routeId());
            pstmt.setInt(2, entity.station().id());
            pstmt.setString(3, entity.arrivalTime());
            pstmt.setString(4, entity.departureTime());
            pstmt.setInt(5, entity.stopOrder());
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
        String sql = "DELETE FROM RouteStops WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RouteStop entity) {
        String sql = "UPDATE RouteStops SET route_id=?, station_id=?, arrival_time=?, departure_time=?, stop_order=? WHERE id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entity.routeId());
            pstmt.setInt(2, entity.station().id());
            pstmt.setString(3, entity.arrivalTime());
            pstmt.setString(4, entity.departureTime());
            pstmt.setInt(5, entity.stopOrder());
            pstmt.setInt(6, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RouteStop getById(int id) {
        String sql = "SELECT * FROM RouteStops WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Station s = stationRepo.getById(rs.getInt("station_id"));
                return new RouteStop(rs.getInt("id"), rs.getInt("route_id"), s, rs.getString("arrival_time"), rs.getString("departure_time"), rs.getInt("stop_order"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RouteStop> getAll() {
        List<RouteStop> stops = new ArrayList<>();
        String sql = "SELECT * FROM RouteStops ORDER BY route_id, stop_order";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Station s = stationRepo.getById(rs.getInt("station_id"));
                stops.add(new RouteStop(rs.getInt("id"), rs.getInt("route_id"), s, rs.getString("arrival_time"), rs.getString("departure_time"), rs.getInt("stop_order")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stops;
    }

    public List<RouteStop> getByRouteId(int routeId) {
        List<RouteStop> stops = new ArrayList<>();
        String sql = "SELECT * FROM RouteStops WHERE route_id = ? ORDER BY stop_order";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, routeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Station s = stationRepo.getById(rs.getInt("station_id"));
                stops.add(new RouteStop(rs.getInt("id"), rs.getInt("route_id"), s, rs.getString("arrival_time"), rs.getString("departure_time"), rs.getInt("stop_order")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stops;
    }
}
