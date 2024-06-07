package org.deblock.exercise.controller;

import lombok.extern.slf4j.Slf4j;
import org.deblock.generated.api.model.ToughJetFlightDetailResponse;
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
 * Mock controller mocking toughJet Api
 */
@RestController
@Slf4j
public class ToughJetController implements org.deblock.generated.api.ToughJetApi {
    @Override
    public ResponseEntity<List<ToughJetFlightDetailResponse>> getFlightsDetailFromToughJet(String from, String to, LocalDate outboundDate, LocalDate inboundDate, Integer numberOfAdults) {
        return ResponseEntity.ok(createList(outboundDate, inboundDate, from, to ));
    }

    private List<ToughJetFlightDetailResponse> createList(LocalDate departureDate, LocalDate returnDate, String origin, String destination ){
        return  IntStream.range(1,6)
                .mapToObj(i->create(OffsetDateTime.of(departureDate, LocalTime.NOON, ZoneOffset.UTC),OffsetDateTime.of(returnDate, LocalTime.NOON, ZoneOffset.UTC),origin,destination,i*100))
                .toList();
    }


    private ToughJetFlightDetailResponse create(OffsetDateTime departureDateTime, OffsetDateTime arrivalDateTime, String origin, String destination, Integer price){
        return new org.deblock.generated.api.model.ToughJetFlightDetailResponse()
                .carrier("HPA")
                .inboundDateTime(arrivalDateTime)
                .outboundDateTime(departureDateTime)
                .arrivalAirportName(destination)
                .departureAirportName(origin)
                .cabinClass("E")
                .basePrice(BigDecimal.valueOf(price))
                .discount(BigDecimal.valueOf(0.8))
                .tax(BigDecimal.valueOf(price*0.1));
    }
}
