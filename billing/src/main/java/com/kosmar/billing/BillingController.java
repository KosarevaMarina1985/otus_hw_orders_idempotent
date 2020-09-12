package com.kosmar.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Slf4j
@AllArgsConstructor
public class BillingController {

    private final BillingService service;

    @PostMapping("/account")
    public void create(@RequestBody CreateAccountRequest request) {
        service.create(request.getUserId());
    }

    @PutMapping("/balance/add")
    public void addMoney(@RequestBody ChangeMoneyRequest request) {
        service.addMoney(request.getUserId(), request.getAmount());
    }

    @GetMapping("/balance/{id}")
    public BigDecimal balance(@PathVariable Long id) {
        return service.balance(id);
    }

    @KafkaListener(topics = "orderCreated", groupId = "group_id")
    public void consumeOrderCreated(NotificationMessage m) throws JsonProcessingException {
        log.info("orderCreated message consumed: {}", m);
        service.handleOrderCreated(m);
    }
}
