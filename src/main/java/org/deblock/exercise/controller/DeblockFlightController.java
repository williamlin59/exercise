package org.deblock.exercise.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deblock.exercise.domain.FlightDetails;
import org.deblock.exercise.service.FlightDetailService;
import org.deblock.exercise.validator.Validator;
import org.deblock.generated.api.model.DeblockFlightDetailResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeblockFlightController implements org.deblock.generated.api.DeblockApi {

    private final FlightDetailService flightDetailService;

    @Override
    public ResponseEntity<List<DeblockFlightDetailResponse>>
    getFlightsDetailFromDebLock(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Integer numberOfPassengers) {
        log.info("Received request. Origin={}, destination={}, departureDate={}, returnDate={} ,numberOfPassengers={}",origin,destination,departureDate,returnDate,numberOfPassengers);
        if(Validator.isReturnDateBeforeDepartureDate(departureDate,returnDate)){
            throw new IllegalStateException("Return date can't before departure date");
        }
        List<DeblockFlightDetailResponse> list = flightDetailService.getFlightDetails(origin, destination, departureDate, returnDate, numberOfPassengers).stream().map(DeblockFlightController::from).toList();

        return ResponseEntity.ok(list);

    }


    private static org.deblock.generated.api.model.DeblockFlightDetailResponse from(FlightDetails flightDetails){
        return new DeblockFlightDetailResponse()
                .airLine(flightDetails.getAirline())
                .arrivalDate(flightDetails.getArrivalDate())
                .departureDate(flightDetails.getDepartureDate())
                .fare(flightDetails.getFare().setScale(2))
                .supplier(flightDetails.getSupplier().name())
                .departureAirportCode(flightDetails.getDepartureAirportCode())
                .destinationAirportCode(flightDetails.getDestinationAirportCode());
    }
}
