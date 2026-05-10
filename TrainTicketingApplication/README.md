# Java Train Ticketing Application

A comprehensive Java-based Train Ticketing Application utilizing a SQLite database. The application features a Java Swing Graphical User Interface (GUI) and follows an MVC/Layered architecture (Domain, Repository, Service, UI).

## Features

### Customer Functionality
- **Route Search**: Find possible routes between a start station and an end station. The system supports finding direct paths and 1-stop changeovers.
- **Ticket Booking**: Book tickets for a specified route and stations. Prevents overbooking by verifying the train capacity.
- **Email Confirmation**: A simulated email confirmation is generated and printed to the console upon successful booking.

### Administrator Functionality
- **Manage Stations**: Add new stations to the network.
- **Manage Trains**: Add new trains with specified seating capacities.
- **Manage Routes & Stops**: Define routes and add ordered stops (stations) with specific arrival and departure times.
- **Delay Reporting**: Specify delays for a train route. Affected customers will receive a simulated email notification about the delay.

## Project Structure & Architecture
- **Domain Layer**: Contains the data records (`Customer`, `Train`, `Station`, `Route`, `RouteStop`, `Ticket`, `Delay`).
- **Repository Layer**: Handles database interaction via JDBC (SQLite). Includes generic interfaces and specific implementations (`StationRepository`, `TrainRepository`, etc.).
- **Service Layer**: Contains business logic.
  - `TicketingService`: Handles booking, capacity validation, and email simulation.
  - `RoutingService`: Uses an algorithm to find direct and changeover paths.
  - `AdminService`: Orchestrates admin actions and delay notifications.
- **UI Layer**: Java Swing UI (`Main`, `CustomerPanel`, `AdminPanel`).

## Setup & Running

This project uses **Maven** for dependency management. To run the application:

### Prerequisites
- JDK 21+ installed
- IntelliJ IDEA (recommended, as it bundles Maven and helps run Java apps seamlessly)

### How to Run
1. Open the `TrainTicketingApplication` folder in IntelliJ IDEA.
2. Ensure IntelliJ detects the `pom.xml` and downloads the `sqlite-jdbc` dependency.
3. Open `src/main/java/UI/Main.java`.
4. Click the green "Run" arrow next to the `main` method.
5. The SQLite database `identifier.sqlite` is included and pre-configured with the necessary schema.

## Usage Examples

### 1. Admin: Building a Route
- Go to the **Admin Portal** tab.
- Click **Add Station** to add "New York" and "Boston".
- Click **Add Train** to add "Express-1" with capacity "100".
- Click **Add Route** to add "NY-BOS" mapped to the newly created Train ID.
- Click **Add Route Stop** to attach the stations to the route with their arrival/departure times and stop orders (e.g., Order 1 for NY, Order 2 for Boston).

### 2. Customer: Searching & Booking
- Go to the **Customer Portal** tab.
- Select your starting and destination stations from the dropdowns and click **Search Routes**.
- The result pane will display possible options, including schedules.
- Enter your email, the desired `Route ID`, and click **Book Ticket**.
- Check your console output in IntelliJ to see the simulated email confirmation.

### 3. Admin: Reporting a Delay
- Go to the **Admin Portal** tab.
- Click **Report Delay**. Enter the `Route ID` and delay minutes.
- Check your console output to see the simulated delay notification emails being sent to all customers who booked that route.

## Error Handling
- Attempting to book a ticket on a full train will result in a GUI error popup indicating overbooking.
- Searching for a route where no direct or changeover link exists will show "No routes found between these stations."
