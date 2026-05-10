import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC"); // Force load driver
            Connection conn = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            System.out.println("Connection successful!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
