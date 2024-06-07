package org.deblock.exercise.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deblock.exercise.domain.FlightDetails;
import org.deblock.exercise.validator.Validator;
import org.deblock.generated.client.model.CrazyAirFlightDetailResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrazyAirClient implements FlightSupplierClient{

    private final org.deblock.generated.client.CrazyAirFlightApi api;

    @Override
    public List<FlightDetails> getFlightDetail(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Integer passengerCount) {
        log.info("Request flight details from Crazy air. Origin={}, destination={} , departureDate={}, returnDate={}, passengerCount={}",origin,destination,departureDate,returnDate,passengerCount);

        List<CrazyAirFlightDetailResponse> flightsDetailFromCrazyAir = api.getFlightsDetailFromCrazyAir(origin, destination, departureDate, returnDate, passengerCount).stream()
               .filter(response->!Validator.isReturnDateBeforeDepartureDate(response.getDepartureDate(),response.getArrivalDate()))
                .toList();



        log.info("Received flight details from Crazy air. Size={}",flightsDetailFromCrazyAir.size());

        return flightsDetailFromCrazyAir.stream().map(FlightDetails::from).toList();
    }


}
