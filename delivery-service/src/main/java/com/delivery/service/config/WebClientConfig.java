package com.delivery.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.delivery.service.client.AgentClient;
import com.delivery.service.client.OrderClient;

@Configuration
public class WebClientConfig {

    @Autowired
	private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    WebClient agentWebClient() {
        return WebClient.builder()
                .baseUrl("http://agent-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    AgentClient agentClient(WebClient agentWebClient) {
        WebClientAdapter adapter = WebClientAdapter.create(agentWebClient());
        return HttpServiceProxyFactory.builderFor(adapter).build().createClient(AgentClient.class);
    }

    @Bean
    WebClient orderWebClient() {
        return WebClient.builder()
                .baseUrl("http://order-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    OrderClient orderClient(WebClient orderWebClient) {
        WebClientAdapter adapter = WebClientAdapter.create(orderWebClient());
        return HttpServiceProxyFactory.builderFor(adapter).build().createClient(OrderClient.class);
    }

}
