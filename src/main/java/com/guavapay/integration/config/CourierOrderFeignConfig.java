package com.guavapay.integration.config;

import com.guavapay.integration.client.CourierOrderClient;
import com.guavapay.integration.error.CourierOrderErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {CourierOrderClient.class})
public class CourierOrderFeignConfig {

    @Bean
    public ErrorDecoder parcelOrderFeignErrorDecoder() {
        return new CourierOrderErrorDecoder();
    }
}
