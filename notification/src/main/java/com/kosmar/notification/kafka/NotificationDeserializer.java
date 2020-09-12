package com.kosmar.notification.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosmar.notification.NotificationMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class NotificationDeserializer implements Deserializer<NotificationMessage> {

    @Override
    public NotificationMessage deserialize(String topic, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        NotificationMessage message = null;
        try {
            message = mapper.readValue(bytes, NotificationMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

}
