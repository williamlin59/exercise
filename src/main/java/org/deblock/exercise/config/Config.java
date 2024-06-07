package org.deblock.exercise.config;

import lombok.extern.slf4j.Slf4j;
import org.deblock.generated.client.CrazyAirFlightApi;
import org.deblock.generated.client.ToughJetFlightApi;
import org.deblock.generated.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class Config {

    @Bean
    public org.deblock.generated.client.CrazyAirFlightApi crazyAirClientApi(@Value("${crazyAir.url}")String url,
                                                                            @Value("${server.port}")Integer port){
        ApiClient apiClient = new ApiClient(restClient(url+":"+port));
        apiClient.setBasePath(url+":"+port);
        log.info("CrazyAir basepath={}",apiClient.getBasePath());
        return new CrazyAirFlightApi(apiClient);
    }



    @Bean
    public org.deblock.generated.client.ToughJetFlightApi toughJetFlightApi(@Value("${toughJet.url}")String url,
                                                                            @Value("${server.port}")Integer port){
        ApiClient apiClient = new ApiClient(restClient(url+":"+port));
        apiClient.setBasePath(url+":"+port);
        log.info("ToughJet basepath={}",apiClient.getBasePath());
        return new ToughJetFlightApi(apiClient);
    }

    @Bean
    public ExecutorService virtualThreadExecutorService(){
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    private RestClient restClient(String baseUrl){
        return RestClient.builder()
                .baseUrl(baseUrl).build();
    }
}
