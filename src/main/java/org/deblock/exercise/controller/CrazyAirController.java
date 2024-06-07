package org.deblock.exercise.controller;

import lombok.extern.slf4j.Slf4j;
import org.deblock.generated.api.model.CrazyAirFlightDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Mock controller mocking crazyAir Api
 */
@RestController
@Slf4j
public class CrazyAirController implements org.deblock.generated.api.CrazyAirApi {
    @Override
    public ResponseEntity<List<CrazyAirFlightDetailResponse>> getFlightsDetailFromCrazyAir(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Integer passengerCount) {
        return ResponseEntity.ok(createList(departureDate,returnDate,origin,destination));
    }

    private List<CrazyAirFlightDetailResponse> createList(LocalDate departureDate, LocalDate returnDate,String origin, String destination ){
       return  IntStream.range(1,6)
                .mapToObj(i->create(OffsetDateTime.of(departureDate, LocalTime.NOON, ZoneOffset.UTC),OffsetDateTime.of(returnDate, LocalTime.NOON, ZoneOffset.UTC),origin,destination,i*100))
                .toList();
    }


    private CrazyAirFlightDetailResponse create(OffsetDateTime departureDateTime, OffsetDateTime arrivalDateTime,String origin, String destination,Integer price){
        return new CrazyAirFlightDetailResponse()
                .airLine("HPA")
                .arrivalDate(arrivalDateTime)
                .departureDate(departureDateTime)
                .destinationAirportCode(destination)
                .departureAirportCode(origin)
                .cabinClass("E")
                .price(BigDecimal.valueOf(price));
    }

}
