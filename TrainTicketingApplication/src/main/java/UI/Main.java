package UI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Train Ticketing System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JTabbedPane tabbedPane = new JTabbedPane();
            CustomerPanel customerPanel = new CustomerPanel();
            tabbedPane.addTab("Customer Portal", customerPanel);
            tabbedPane.addTab("Admin Portal", new AdminPanel());

            tabbedPane.addChangeListener(e -> {
                if (tabbedPane.getSelectedComponent() == customerPanel) {
                    customerPanel.refreshData();
                }
            });

            frame.add(tabbedPane, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
