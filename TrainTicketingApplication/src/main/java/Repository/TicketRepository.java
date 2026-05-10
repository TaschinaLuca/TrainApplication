package Repository;

import Domain.Customer;
import Domain.Route;
import Domain.Station;
import Domain.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements Repository<Ticket> {
    private CustomerRepository customerRepo = new CustomerRepository();
    private RouteRepository routeRepo = new RouteRepository();
    private StationRepository stationRepo = new StationRepository();

    @Override
    public int add(Ticket entity) {
        String sql = "INSERT INTO Tickets(customer_id, route_id, start_station_id, end_station_id) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, entity.customer().id());
            pstmt.setInt(2, entity.route().id());
            pstmt.setInt(3, entity.startStation().id());
            pstmt.setInt(4, entity.endStation().id());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Ticket> getTicketsForRoute(int routeId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets WHERE route_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, routeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Customer c = customerRepo.getById(rs.getInt("customer_id"));
                Route r = routeRepo.getById(rs.getInt("route_id"));
                Station start = stationRepo.getById(rs.getInt("start_station_id"));
                Station end = stationRepo.getById(rs.getInt("end_station_id"));
                tickets.add(new Ticket(rs.getInt("id"), c, r, start, end));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void remove(int id) {}
    @Override
    public void update(Ticket entity) {}
    @Override
    public Ticket getById(int id) { return null; }
    @Override
    public List<Ticket> getAll() { return new ArrayList<>(); }
}
