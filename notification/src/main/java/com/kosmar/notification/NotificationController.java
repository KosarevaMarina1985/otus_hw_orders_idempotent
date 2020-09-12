package com.kosmar.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService service;

    @GetMapping("/")
    public List<NotificationFront> findAll() {
        return service.findAll();
    }

    @GetMapping("/{orderId}")
    public NotificationFront findById(@PathVariable String orderId) {
        return service.findByOrderId(orderId);
    }

    @KafkaListener(topics = "notification", groupId = "group_id")
    public void consumePositive(NotificationMessage m) throws JsonProcessingException {
        log.info("notification message consumed: {}", m);
        service.save(m);
    }
}
