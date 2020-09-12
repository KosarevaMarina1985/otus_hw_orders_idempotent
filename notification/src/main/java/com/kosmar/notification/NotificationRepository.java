package com.kosmar.notification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationModel, Long> {

    Optional<NotificationModel> findByOrderId(String orderId);
}