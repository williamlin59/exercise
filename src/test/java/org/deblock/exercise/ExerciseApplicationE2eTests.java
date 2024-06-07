package org.deblock.exercise;

import org.deblock.exercise.domain.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ExerciseApplicationE2eTests {

	@LocalServerPort
	private int port;

	@Test
	void testE2eHappyPath() {
		List<org.deblock.generated.api.model.DeblockFlightDetailResponse> list = given().when()
				.queryParam("origin","LHA")
				.queryParam("destination","PVG")
				.queryParam("departureDate","2028-01-01")
				.queryParam("returnDate","2028-01-02")
				.queryParam("numberOfPassengers","2")
				.get(URI.create("http://localhost:"+port+"/deblock/flights"))
				.then().statusCode(200).and().extract()
				.jsonPath().getList(".", org.deblock.generated.api.model.DeblockFlightDetailResponse.class);
		assertThat(list.size()).isEqualTo(10);
		int crazyFlight =0;
		int toughJet =0;
		for(int i=0;i<list.size();i++){
			if(Supplier.CRAZY_AIR.name().equals(list.get(i).getSupplier())){
				crazyFlight++;
			}
			else if(Supplier.TOUGH_JET.name().equals(list.get(i).getSupplier())){
				toughJet++;
			}
		}
		assertThat(crazyFlight).isEqualTo(5);
		assertThat(toughJet).isEqualTo(5);
	}

}
