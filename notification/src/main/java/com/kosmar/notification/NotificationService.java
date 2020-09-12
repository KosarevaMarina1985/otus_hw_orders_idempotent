package com.kosmar.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static lombok.AccessLevel.PACKAGE;

@Service
@AllArgsConstructor(access = PACKAGE)
@Slf4j
// bad implementation, just for homework, principle is more important for this work
public class NotificationService {

    private final NotificationRepository repository;

    NotificationFront save(NotificationMessage message) throws JsonProcessingException {
        OrderPayload payload = fromPayload(message.getPayload());
        NotificationModel model = repository.save(NotificationModel.builder()
                .userId(payload.getUserId())
                .datetime(LocalDateTime.now())
                .message(payload.getStatus().equals("decline") ? "sorry..." : "congratulations")
                .orderId(message.getOrderId())
                .orderType(payload.getStatus().equals("decline") ? "negative" : "positive")
                .price(payload.getPrice()).build());

        log.info("notification saved {}",model);
        return toNotificationFront(model);

    }

    List<NotificationFront> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toNotificationFront)
                .collect(Collectors.toList());
    }

    NotificationFront findByOrderId(String id) {
        return repository.findByOrderId(id)
                .map(this::toNotificationFront)
                .orElse(null);
    }

    private NotificationFront toNotificationFront(NotificationModel entity) {
        return NotificationFront.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .datetime(entity.getDatetime())
                .orderId(entity.getOrderId())
                .orderType(entity.getOrderType())
                .price(entity.getPrice())
                .build();
    }

    private OrderPayload fromPayload(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(payload, OrderPayload.class);
    }

}
