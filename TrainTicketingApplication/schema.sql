DROP TABLE IF EXISTS ScheduleToTickets;
DROP TABLE IF EXISTS Tickets;
DROP TABLE IF EXISTS Delays;
DROP TABLE IF EXISTS RouteToStation;
DROP TABLE IF EXISTS Routes;
DROP TABLE IF EXISTS Schedules;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Trains;
DROP TABLE IF EXISTS Stations;

CREATE TABLE Stations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE Trains (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    nr_seats INTEGER NOT NULL
);

CREATE TABLE Routes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    train_id INTEGER NOT NULL,
    FOREIGN KEY(train_id) REFERENCES Trains(id)
);

CREATE TABLE RouteStops (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    route_id INTEGER NOT NULL,
    station_id INTEGER NOT NULL,
    arrival_time TEXT,
    departure_time TEXT,
    stop_order INTEGER NOT NULL,
    FOREIGN KEY(route_id) REFERENCES Routes(id),
    FOREIGN KEY(station_id) REFERENCES Stations(id)
);

CREATE TABLE Customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    email TEXT NOT NULL UNIQUE
);

CREATE TABLE Tickets (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    route_id INTEGER NOT NULL,
    start_station_id INTEGER NOT NULL,
    end_station_id INTEGER NOT NULL,
    FOREIGN KEY(customer_id) REFERENCES Customers(id),
    FOREIGN KEY(route_id) REFERENCES Routes(id),
    FOREIGN KEY(start_station_id) REFERENCES Stations(id),
    FOREIGN KEY(end_station_id) REFERENCES Stations(id)
);

CREATE TABLE Delays (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    route_id INTEGER NOT NULL,
    delay_minutes INTEGER NOT NULL,
    date_reported TEXT NOT NULL,
    FOREIGN KEY(route_id) REFERENCES Routes(id)
);
