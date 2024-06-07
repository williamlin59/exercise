package org.deblock.exercise.controller;

import io.restassured.RestAssured;
import net.bytebuddy.asm.Advice;
import org.deblock.exercise.TestDataHelper;
import org.deblock.exercise.service.FlightDetailService;
import org.deblock.generated.api.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeblockFlightControllerTest {
    @MockBean
    private FlightDetailService flightDetailService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(){
        RestAssured.reset();
    }

    @Test
    void getFlightDetail(){
        when(flightDetailService.getFlightDetails(anyString(),anyString(), any(LocalDate.class),any(LocalDate.class),anyInt()))
                .thenReturn(List.of(TestDataHelper.create()));
        List<org.deblock.generated.api.model.DeblockFlightDetailResponse> list = given().when()
                .queryParam("origin","LHA")
                .queryParam("destination","PVG")
                .queryParam("departureDate","2028-01-01")
                .queryParam("returnDate","2028-01-02")
                .queryParam("numberOfPassengers","2")
                .get(URI.create("http://localhost:"+port+"/deblock/flights"))
                .then().statusCode(200).and().extract()
                .jsonPath().getList(".", org.deblock.generated.api.model.DeblockFlightDetailResponse.class);

        assertThat(list.size()).isOne();
       var result= list.get(0);
       assertThat(result.getAirLine()).isEqualTo("British");
       assertThat(result.getSupplier()).isEqualTo("CRAZY_AIR");
       assertThat(result.getFare()).isEqualTo(BigDecimal.valueOf(1.0));
       assertThat(result.getDepartureAirportCode()).isEqualTo("LHA");
       assertThat(result.getDestinationAirportCode()).isEqualTo("PVG");
       assertThat(result.getDepartureDate()).isNotNull();
       assertThat(result.getArrivalDate()).isNotNull();
    }

    @Test
    void missingRequiredParameter(){

        given().when()
                .queryParam("origin","LHA")
                .queryParam("destination","PVG")
                .queryParam("departureDate","2028-01-01")
                .queryParam("returnDate","2028-01-02")
                .get(URI.create("http://localhost:" + port + "/deblock/flights")).then().statusCode(400);

    }

    @Test
    void invalidParameter(){

        given().when()
                .queryParam("origin","LHA")
                .queryParam("destination","PVG")
                .queryParam("departureDate","2028-01-01")
                .queryParam("returnDate","2028-")
                .queryParam("numberOfPassengers","2")
                .get(URI.create("http://localhost:" + port + "/deblock/flights")).then().statusCode(400);

    }

    @Test
    void arrivalDateIsBeforeDepartureDate(){
        ErrorResponse errorResponse = given().when()
                .queryParam("origin", "LHA")
                .queryParam("destination", "PVG")
                .queryParam("departureDate", "2028-01-03")
                .queryParam("returnDate", "2028-01-02")
                .queryParam("numberOfPassengers", "2")
                .get(URI.create("http://localhost:" + port + "/deblock/flights"))
                .then().statusCode(400).and().extract()
                .as(ErrorResponse.class);
        assertThat(errorResponse.getCode()).isEqualTo("CP400");
        assertThat(errorResponse.getDescription()).isNotNull();
    }

    @Test
    void testRuntimeException(){
        when(flightDetailService.getFlightDetails(anyString(),anyString(), any(LocalDate.class),any(LocalDate.class),anyInt()))
                .thenThrow(new RuntimeException());
        ErrorResponse errorResponse = given().when()
                .queryParam("origin","LHA")
                .queryParam("destination","PVG")
                .queryParam("departureDate","2028-01-01")
                .queryParam("returnDate","2028-01-02")
                .queryParam("numberOfPassengers","2")
                .get(URI.create("http://localhost:" + port + "/deblock/flights"))
                .then().statusCode(500).and().extract()
                .as(ErrorResponse.class);
        assertThat(errorResponse.getCode()).isEqualTo("CP500");
    }


}