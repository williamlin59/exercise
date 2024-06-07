package org.deblock.exercise.service;

import lombok.RequiredArgsConstructor;
import org.deblock.exercise.client.FlightSupplierClient;
import org.deblock.exercise.domain.FlightDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class FlightDetailService {

    private final Set<FlightSupplierClient> clients;

    private final ExecutorService executorService;

    public List<FlightDetails> getFlightDetails(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Integer numberOfPassengers){

        List<CompletableFuture<List<FlightDetails>>> list = clients.stream().map(client -> CompletableFuture
                .supplyAsync(() -> client.getFlightDetail(origin, destination, departureDate, returnDate, numberOfPassengers),executorService)).toList();
            CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
       return list.stream().map(CompletableFuture::join).flatMap(Collection::stream).sorted(Comparator.comparing(FlightDetails::getFare)).toList();

    }
}
