package Service;

import Domain.*;
import Repository.*;

import java.util.List;

public class TicketingService {
    private CustomerRepository customerRepo = new CustomerRepository();
    private TicketRepository ticketRepo = new TicketRepository();
    private RouteRepository routeRepo = new RouteRepository();
    private StationRepository stationRepo = new StationRepository();

    public boolean bookTicket(String email, int routeId, int startStationId, int endStationId) {
        Route r = routeRepo.getById(routeId);
        if (r == null) return false;

        // Overbooking check
        List<Ticket> existingTickets = ticketRepo.getTicketsForRoute(routeId);
        if (existingTickets.size() >= r.train().nrSeats()) {
            System.out.println("Error: Train capacity reached. Cannot overbook.");
            return false;
        }

        Customer c = customerRepo.getByEmail(email);
        if (c == null) {
            int cid = customerRepo.add(new Customer(0, email));
            c = customerRepo.getById(cid);
        }

        Station start = stationRepo.getById(startStationId);
        Station end = stationRepo.getById(endStationId);

        Ticket t = new Ticket(0, c, r, start, end);
        ticketRepo.add(t);

        sendConfirmationEmail(c, r, start, end);
        return true;
    }

    private void sendConfirmationEmail(Customer c, Route r, Station start, Station end) {
        System.out.println("--------------------------------------------------");
        System.out.println("[SIMULATED EMAIL]");
        System.out.println("To: " + c.email());
        System.out.println("Subject: Ticket Booking Confirmation");
        System.out.println("Message: Your ticket has been booked successfully.");
        System.out.println("Route: " + r.name() + " | Train: " + r.train().name());
        System.out.println("From: " + start.name() + " To: " + end.name());
        System.out.println("--------------------------------------------------");
    }
}
