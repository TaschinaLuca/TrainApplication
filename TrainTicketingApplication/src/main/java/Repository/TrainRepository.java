package Repository;

import Domain.Train;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository implements Repository<Train> {

    @Override
    public int add(Train entity) {
        String sql = "INSERT INTO Trains(name, nr_seats) VALUES(?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.name());
            pstmt.setInt(2, entity.nrSeats());
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
        String sql = "DELETE FROM Trains WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Train entity) {
        String sql = "UPDATE Trains SET name = ?, nr_seats = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.name());
            pstmt.setInt(2, entity.nrSeats());
            pstmt.setInt(3, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Train getById(int id) {
        String sql = "SELECT * FROM Trains WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Train(rs.getInt("id"), rs.getString("name"), rs.getInt("nr_seats"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Train> getAll() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM Trains";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trains.add(new Train(rs.getInt("id"), rs.getString("name"), rs.getInt("nr_seats")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }
}
