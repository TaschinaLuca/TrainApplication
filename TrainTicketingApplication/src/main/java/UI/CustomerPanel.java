package UI;

import Service.RoutingService;
import Service.TicketingService;
import Domain.Station;
import Repository.StationRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {
    private RoutingService routingService = new RoutingService();
    private TicketingService ticketingService = new TicketingService();
    private StationRepository stationRepo = new StationRepository();

    private JComboBox<String> fromCombo = new JComboBox<>();
    private JComboBox<String> toCombo = new JComboBox<>();

    public CustomerPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        
        refreshData();
        
        searchPanel.add(new JLabel("From:"));
        searchPanel.add(fromCombo);
        searchPanel.add(new JLabel("To:"));
        searchPanel.add(toCombo);

        JButton searchBtn = new JButton("Search Routes");
        searchPanel.add(searchBtn);
        
        add(searchPanel, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> {
            String fromStr = (String) fromCombo.getSelectedItem();
            String toStr = (String) toCombo.getSelectedItem();
            if (fromStr == null || toStr == null) return;
            
            int fromId = Integer.parseInt(fromStr.split(" - ")[0]);
            int toId = Integer.parseInt(toStr.split(" - ")[0]);

            List<List<RoutingService.RouteLeg>> routes = routingService.findRoutes(fromId, toId);
            resultArea.setText("");
            if (routes.isEmpty()) {
                resultArea.setText("No routes found between these stations.");
            } else {
                for (int i = 0; i < routes.size(); i++) {
                    resultArea.append("Option " + (i + 1) + ":\n");
                    for (RoutingService.RouteLeg leg : routes.get(i)) {
                        resultArea.append("  Route " + leg.route().id() + " (" + leg.route().name() + ")\n");
                        resultArea.append("    Depart: " + leg.from().name() + " @ " + leg.departureTime() + "\n");
                        resultArea.append("    Arrive: " + leg.to().name() + " @ " + leg.arrivalTime() + "\n");
                    }
                    resultArea.append("\n");
                }
            }
        });

        JPanel bookPanel = new JPanel();
        JTextField emailField = new JTextField(15);
        JTextField routeIdField = new JTextField(5);
        JButton bookBtn = new JButton("Book Ticket");
        
        bookPanel.add(new JLabel("Email:"));
        bookPanel.add(emailField);
        bookPanel.add(new JLabel("Route ID to book:"));
        bookPanel.add(routeIdField);
        bookPanel.add(bookBtn);

        bookBtn.addActionListener(e -> {
            String fromStr = (String) fromCombo.getSelectedItem();
            String toStr = (String) toCombo.getSelectedItem();
            if (fromStr == null || toStr == null) return;
            int fromId = Integer.parseInt(fromStr.split(" - ")[0]);
            int toId = Integer.parseInt(toStr.split(" - ")[0]);
            int rId = Integer.parseInt(routeIdField.getText());

            boolean success = ticketingService.bookTicket(emailField.getText(), rId, fromId, toId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Ticket Booked Successfully! Confirmation email simulated in console.");
            } else {
                JOptionPane.showMessageDialog(this, "Booking Failed (Overbooked or Invalid Route).");
            }
        });

        add(bookPanel, BorderLayout.SOUTH);
    }

    public void refreshData() {
        fromCombo.removeAllItems();
        toCombo.removeAllItems();
        List<Station> stations = stationRepo.getAll();
        for (Station s : stations) {
            fromCombo.addItem(s.id() + " - " + s.name());
            toCombo.addItem(s.id() + " - " + s.name());
        }
    }
}
