package Service;

import Domain.Route;
import Domain.RouteStop;
import Domain.Station;
import Repository.RouteRepository;
import Repository.RouteStopRepository;

import java.util.*;

public class RoutingService {
    private RouteRepository routeRepo = new RouteRepository();
    private RouteStopRepository routeStopRepo = new RouteStopRepository();

    public record RouteLeg(Route route, Station from, Station to, String departureTime, String arrivalTime) {
    }

    public List<List<RouteLeg>> findRoutes(int startStationId, int endStationId) {
        List<List<RouteLeg>> results = new ArrayList<>();
        List<Route> allRoutes = routeRepo.getAll();
        Map<Integer, List<RouteStop>> routeStopsMap = new HashMap<>();

        for (Route r : allRoutes) {
            routeStopsMap.put(r.id(), routeStopRepo.getByRouteId(r.id()));
        }

        for (Route r : allRoutes) {
            List<RouteStop> stops = routeStopsMap.get(r.id());
            RouteStop start = null;
            RouteStop end = null;
            for (RouteStop rs : stops) {
                if (rs.station().id() == startStationId)
                    start = rs;
                if (rs.station().id() == endStationId)
                    end = rs;
            }
            if (start != null && end != null && start.stopOrder() < end.stopOrder()) {
                results.add(List
                        .of(new RouteLeg(r, start.station(), end.station(), start.departureTime(), end.arrivalTime())));
            }
        }

        for (Route r1 : allRoutes) {
            List<RouteStop> stops1 = routeStopsMap.get(r1.id());
            RouteStop start1 = stops1.stream().filter(s -> s.station().id() == startStationId).findFirst().orElse(null);
            if (start1 == null)
                continue;

            for (RouteStop end1 : stops1) {
                if (end1.stopOrder() <= start1.stopOrder())
                    continue;
                for (Route r2 : allRoutes) {
                    if (r1.id() == r2.id())
                        continue;
                    List<RouteStop> stops2 = routeStopsMap.get(r2.id());
                    RouteStop start2 = stops2.stream().filter(s -> s.station().id() == end1.station().id()).findFirst()
                            .orElse(null);
                    if (start2 == null)
                        continue;

                    if (start2.departureTime().compareTo(end1.arrivalTime()) >= 0) {
                        RouteStop finalEnd = stops2.stream()
                                .filter(s -> s.station().id() == endStationId && s.stopOrder() > start2.stopOrder())
                                .findFirst().orElse(null);
                        if (finalEnd != null) {
                            results.add(List.of(
                                    new RouteLeg(r1, start1.station(), end1.station(), start1.departureTime(),
                                            end1.arrivalTime()),
                                    new RouteLeg(r2, start2.station(), finalEnd.station(), start2.departureTime(),
                                            finalEnd.arrivalTime())));
                        }
                    }
                }
            }
        }

        return results;
    }
}
