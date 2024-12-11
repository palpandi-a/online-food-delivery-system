package com.order.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.order.service.client.RestaurantClient;
import com.order.service.client.UserClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    @Bean
    UserClient userClient(WebClient userWebClient) {
        WebClientAdapter adapter = WebClientAdapter.create(userWebClient());
        return HttpServiceProxyFactory.builderFor(adapter).build().createClient(UserClient.class);
    }

    @Bean
    WebClient restaurantWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }

    @Bean
    RestaurantClient restaurantClient(WebClient restaurantWebClient) {
        WebClientAdapter adapter = WebClientAdapter.create(restaurantWebClient());
        return HttpServiceProxyFactory.builderFor(adapter).build().createClient(RestaurantClient.class);
    }

}
