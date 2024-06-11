package ru.hse.egorova.ticketorder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-client", url = "http://host.docker.internal:8888/auth")
public interface AuthClient {
    @GetMapping("/myIdImpl")
    public Long getIdImpl(
            @RequestParam String token
    );
}
