package com.kosmar.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
// just for homework. it is instead of message-relay
public class Scheduler {

    private final OrderService service;

    @Scheduled(fixedDelay = 500, initialDelay = 3000)
    public void fixedDelaySch() {
        service.sendEvent();
    }
}
