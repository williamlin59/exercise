package org.deblock.exercise.client;

import org.deblock.exercise.domain.FlightDetails;

import java.time.LocalDate;
import java.util.List;

public interface FlightSupplierClient {
    List<FlightDetails> getFlightDetail(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Integer passengerCount);
}
