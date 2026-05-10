package Service;

import Domain.*;
import Repository.*;

import java.util.List;

public class AdminService {
    private StationRepository stationRepo = new StationRepository();
    private TrainRepository trainRepo = new TrainRepository();
    private RouteRepository routeRepo = new RouteRepository();
    private RouteStopRepository routeStopRepo = new RouteStopRepository();
    private TicketRepository ticketRepo = new TicketRepository();
    private DelayRepository delayRepo = new DelayRepository();
    private CustomerRepository customerRepo = new CustomerRepository();

    public void addStation(String name) {
        stationRepo.add(new Station(0, name));
    }

    public void addTrain(String name, int nrSeats) {
        trainRepo.add(new Train(0, name, nrSeats));
    }

    public int addRoute(String name, int trainId) {
        Train t = trainRepo.getById(trainId);
        if (t != null) {
            return routeRepo.add(new Route(0, name, t));
        }
        return -1;
    }

    public void addRouteStop(int routeId, int stationId, String arrivalTime, String departureTime, int stopOrder) {
        Station s = stationRepo.getById(stationId);
        if (s != null) {
            routeStopRepo.add(new RouteStop(0, routeId, s, arrivalTime, departureTime, stopOrder));
        }
    }

    public List<Ticket> getBookingsForRoute(int routeId) {
        return ticketRepo.getTicketsForRoute(routeId);
    }

    public void reportDelay(int routeId, int delayMinutes, String dateReported) {
        Route r = routeRepo.getById(routeId);
        if (r != null) {
            delayRepo.add(new Delay(0, r, delayMinutes, dateReported));
            notifyCustomersOfDelay(routeId, delayMinutes);
        }
    }

    private void notifyCustomersOfDelay(int routeId, int delayMinutes) {
        List<Ticket> tickets = ticketRepo.getTicketsForRoute(routeId);
        for (Ticket t : tickets) {
            System.out.println("--------------------------------------------------");
            System.out.println("[SIMULATED EMAIL]");
            System.out.println("To: " + t.customer().email());
            System.out.println("Subject: Train Delay Notification");
            System.out.println("Message: Dear Customer, please be advised that the train for your route '" + t.route().name() + "' is delayed by " + delayMinutes + " minutes.");
            System.out.println("--------------------------------------------------");
        }
    }
}
