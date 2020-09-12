package com.kosmar.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static lombok.AccessLevel.PACKAGE;

@Service
@AllArgsConstructor(access = PACKAGE)
@Slf4j
// bad implementation, just for homework, principle is more important for this work
public class OrderService {

    private final OrderRepository repository;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    @Transactional
    OrderFront save(OrderRequest request) throws JsonProcessingException {
        OrderModel order = repository.save(OrderModel.builder()
                .id(request.getOrderId())
                .userId(request.getUserId())
                .price(request.getPrice())
                .status("not confirmed")
                .build());
        outboxRepository.save(OutboxModel.builder()
                .aggregateId(request.getOrderId())
                .type("orderCreated")
                .payload(payload(request))
                .build());
        return toOrderFront(order);
    }

    List<OrderFront> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toOrderFront)
                .collect(Collectors.toList());
    }

    OrderFront findById(String id) {
        return repository.findById(id)
                .map(this::toOrderFront)
                .orElse(null);
    }

    @Transactional
    public void sendEvent() {
        Optional<OutboxModel> outboxOpt = StreamSupport.stream(outboxRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparingLong(OutboxModel::getId))
                .findFirst();
        if (!outboxOpt.isPresent()) {
            return;
        }
        OutboxModel outboxModel = outboxOpt.get();
        log.info("sendEvent: {}", outboxModel);

        kafkaTemplate.send(outboxModel.getType(), NotificationMessage.builder()
                .messageId(outboxModel.getId())
                .payload(outboxModel.getPayload())
                .type(outboxModel.getType())
                .orderId(outboxModel.getAggregateId())
                .build());
        outboxRepository.delete(outboxModel);
    }

    @Transactional
    public void updateStatus(NotificationMessage m) throws JsonProcessingException {
        Optional<OrderModel> orderOpt = repository.findById(m.getOrderId());
        if (!orderOpt.isPresent()) {
            return;
        }
        OrderModel order = orderOpt.get();
        order.setStatus(fromPayload(m.getPayload()).getStatus());
        repository.save(order);
        outboxRepository.save(OutboxModel.builder()
                .aggregateId(m.getOrderId())
                .type(m.getType())
                .payload(m.getPayload())
                .build());
    }

    private OrderFront toOrderFront(OrderModel entity) {
        return OrderFront.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .version(entity.getVersion())
                .build();
    }

    private String payload(OrderRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OrderPayload payload = OrderPayload.builder()
                .price(request.getPrice())
                .status("not confirmed")
                .userId(request.getUserId()).build();
        return mapper.writeValueAsString(payload);
    }

    private OrderPayload fromPayload(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(payload, OrderPayload.class);
    }
}
