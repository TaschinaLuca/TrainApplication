package UI;

import Service.AdminService;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    private AdminService adminService = new AdminService();

    public AdminPanel() {
        setLayout(new GridLayout(6, 1));
        
        JButton addStationBtn = new JButton("Add Station");
        addStationBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Station Name:");
            if (name != null) {
                adminService.addStation(name);
                JOptionPane.showMessageDialog(this, "Station added!");
            }
        });
        add(addStationBtn);

        JButton addTrainBtn = new JButton("Add Train");
        addTrainBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Train Name:");
            String seats = JOptionPane.showInputDialog("Number of Seats:");
            if (name != null && seats != null) {
                adminService.addTrain(name, Integer.parseInt(seats));
                JOptionPane.showMessageDialog(this, "Train added!");
            }
        });
        add(addTrainBtn);

        JButton addRouteBtn = new JButton("Add Route");
        addRouteBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Route Name:");
            String trainId = JOptionPane.showInputDialog("Train ID:");
            if (name != null && trainId != null) {
                adminService.addRoute(name, Integer.parseInt(trainId));
                JOptionPane.showMessageDialog(this, "Route added!");
            }
        });
        add(addRouteBtn);

        JButton addRouteStopBtn = new JButton("Add Route Stop");
        addRouteStopBtn.addActionListener(e -> {
            String routeId = JOptionPane.showInputDialog("Route ID:");
            String stationId = JOptionPane.showInputDialog("Station ID:");
            String arr = JOptionPane.showInputDialog("Arrival Time (YYYY-MM-DD HH:MM):");
            String dep = JOptionPane.showInputDialog("Departure Time (YYYY-MM-DD HH:MM):");
            String order = JOptionPane.showInputDialog("Stop Order:");
            if (routeId != null && stationId != null) {
                adminService.addRouteStop(Integer.parseInt(routeId), Integer.parseInt(stationId), arr, dep, Integer.parseInt(order));
                JOptionPane.showMessageDialog(this, "Route Stop added!");
            }
        });
        add(addRouteStopBtn);
        
        JButton reportDelayBtn = new JButton("Report Delay");
        reportDelayBtn.addActionListener(e -> {
            String routeId = JOptionPane.showInputDialog("Route ID:");
            String mins = JOptionPane.showInputDialog("Delay Minutes:");
            String date = JOptionPane.showInputDialog("Date Reported:");
            if (routeId != null) {
                adminService.reportDelay(Integer.parseInt(routeId), Integer.parseInt(mins), date);
                JOptionPane.showMessageDialog(this, "Delay reported and customers notified!");
            }
        });
        add(reportDelayBtn);
    }
}
