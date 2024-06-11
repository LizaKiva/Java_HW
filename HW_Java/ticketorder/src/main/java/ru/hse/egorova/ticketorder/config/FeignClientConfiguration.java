package ru.hse.egorova.ticketorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Configuration
@EnableFeignClients(basePackages = "ru.hse.egorova.ticketorder.client")
class FeignClientConfiguration {
}
