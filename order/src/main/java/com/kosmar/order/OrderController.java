package com.kosmar.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService service;

    @GetMapping("/")
    public List<OrderFront> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderFront findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping("/create")
    @Transactional
    public OrderFront save(@RequestBody OrderRequest request) throws JsonProcessingException {
        OrderFront order = service.save(request);
        log.info("order created {}", order);
        return order;
    }

    @KafkaListener(topics = "updateOrderStatus", groupId = "group_id")
    public void updateOrderStatus(NotificationMessage m) throws JsonProcessingException {
        log.info("updateOrderStatus consumed: {}", m);
        service.updateStatus(m);
    }
}
