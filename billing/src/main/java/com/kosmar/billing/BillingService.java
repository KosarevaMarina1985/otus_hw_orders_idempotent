package com.kosmar.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static lombok.AccessLevel.PACKAGE;

@Service
@AllArgsConstructor(access = PACKAGE)
@Slf4j
// bad implementation, just for homework, principle is more important for this work
public class BillingService {

    private final AccountRepository repository;
    private final ProcessedMessageRepository processedMessageRepository;
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    void create(Long userId) {
        repository.save(Account.builder()
                .userId(userId)
                .balance(BigDecimal.ZERO)
                .build());
    }

    BigDecimal balance(Long userId) {
        return repository.findByUserId(userId)
                .map(Account::getBalance)
                .orElse(null);
    }

    @Transactional
    void addMoney(Long userId, BigDecimal amount) {
        Account current = repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("not found"));
        current.setBalance(current.getBalance().add(amount));
        repository.save(current);
    }

    @Transactional
    public void handleOrderCreated(NotificationMessage message) throws JsonProcessingException {
        processedMessageRepository.save(ProcessedMessage.builder().id(message.getMessageId()).build());
        OrderPayload payload = fromPayload(message.getPayload());
        log.info("orderCreated message consumed  {}", message);

        Account current = repository.findByUserId(payload.getUserId())
                .orElseThrow(() -> new RuntimeException("not found"));
        BigDecimal sum = current.getBalance().subtract(payload.getPrice());
        String status = "decline";
        if (sum.compareTo(BigDecimal.ZERO) >= 0) {
            current.setBalance(sum);
            repository.save(current);
            status = "confirmed";
        }
        kafkaTemplate.send("updateOrderStatus", NotificationMessage.builder()
                .messageId(message.getMessageId())
                .payload(payload(payload, status))
                .type("notification")
                .orderId(message.getOrderId())
                .build());
    }

    private String payload(OrderPayload current, String status) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OrderPayload payload = OrderPayload.builder()
                .price(current.getPrice())
                .status(status)
                .userId(current.getUserId())
                .build();
        return mapper.writeValueAsString(payload);
    }

    private OrderPayload fromPayload(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(payload, OrderPayload.class);
    }
}
