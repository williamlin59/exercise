package org.deblock.exercise;

import org.deblock.exercise.domain.FlightDetails;
import org.deblock.exercise.domain.Supplier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TestDataHelper {
    public static FlightDetails create(){
        return FlightDetails
                .builder()
                .fare(BigDecimal.ONE)
                .departureDate(OffsetDateTime.now())
                .arrivalDate(OffsetDateTime.now().plusDays(1))
                .departureAirportCode("LHA")
                .destinationAirportCode("PVG")
                .supplier(Supplier.CRAZY_AIR)
                .airline("British").build();
    }
}
