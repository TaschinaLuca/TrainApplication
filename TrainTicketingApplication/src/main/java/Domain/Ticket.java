package Domain;

public record Ticket(int id, Customer customer, Route route, Station startStation, Station endStation) {
}
