package Domain;

public record Delay(int id, Route route, int delayMinutes, String dateReported) {
}
