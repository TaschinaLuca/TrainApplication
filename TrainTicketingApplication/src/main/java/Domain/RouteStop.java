package Domain;

public record RouteStop(int id, int routeId, Station station, String arrivalTime, String departureTime, int stopOrder) {
}
