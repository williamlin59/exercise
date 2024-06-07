package org.deblock.exercise.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Builder
@Value
public class FlightDetails {
    private String airline;
    private Supplier supplier;
    private BigDecimal fare;
    private String departureAirportCode;
    private String destinationAirportCode;
    private OffsetDateTime departureDate;
    private OffsetDateTime arrivalDate;

    public static FlightDetails from(org.deblock.generated.client.model.CrazyAirFlightDetailResponse response){
        return FlightDetails.builder()
                .airline(response.getAirLine())
                .supplier(Supplier.CRAZY_AIR)
                .fare(response.getPrice())
                .departureAirportCode(response.getDepartureAirportCode())
                .destinationAirportCode(response.getDestinationAirportCode())
                .departureDate(response.getDepartureDate())
                .arrivalDate(response.getArrivalDate())
                .build();
    }

    public static FlightDetails from(org.deblock.generated.client.model.ToughJetFlightDetailResponse response){
        return FlightDetails.builder()
                .airline(response.getCarrier())
                .supplier(Supplier.TOUGH_JET)
                .fare(calculateFare(response))
                .departureAirportCode(response.getDepartureAirportName())
                .destinationAirportCode(response.getArrivalAirportName())
                .departureDate(response.getOutboundDateTime())
                .arrivalDate(response.getInboundDateTime())
                .build();
    }


    private static BigDecimal calculateFare(org.deblock.generated.client.model.ToughJetFlightDetailResponse response){
        return response.getBasePrice().multiply(response.getDiscount()).add(response.getTax());
    }
}
