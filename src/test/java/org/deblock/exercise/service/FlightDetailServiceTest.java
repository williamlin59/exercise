package org.deblock.exercise.service;

import org.deblock.exercise.TestDataHelper;
import org.deblock.exercise.client.CrazyAirClient;
import org.deblock.exercise.client.ToughJetClient;
import org.deblock.exercise.domain.FlightDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FlightDetailServiceTest {
    @Mock
    private CrazyAirClient crazyAirClient;

    @Mock
    private ToughJetClient toughJetClient;

    private FlightDetailService service;

    @BeforeEach
    void setUp(){
        initMocks(this);
        service = new FlightDetailService(Set.of(crazyAirClient,toughJetClient), Executors.newVirtualThreadPerTaskExecutor());
    }

    @Test
    void getFlightDetails() {
        when(crazyAirClient.getFlightDetail(anyString(),anyString(),any(LocalDate.class),any(LocalDate.class),anyInt()))
                .thenReturn(List.of(TestDataHelper.create()));

        when(toughJetClient.getFlightDetail(anyString(),anyString(),any(LocalDate.class),any(LocalDate.class),anyInt()))
                .thenReturn(List.of(TestDataHelper.create()));

        List<FlightDetails> flightDetails = service.getFlightDetails("HBC", "PVG", LocalDate.now(), LocalDate.now(), 5);
        assertThat(flightDetails.size()).isEqualTo(2);

    }
}