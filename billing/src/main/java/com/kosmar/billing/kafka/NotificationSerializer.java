package com.kosmar.billing.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosmar.billing.NotificationMessage;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class NotificationSerializer implements Serializer<NotificationMessage> {

    private String encoding = "UTF8";

    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.serializer.encoding" : "value.serializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null) {
            encodingValue = configs.get("serializer.encoding");
        }

        if (encodingValue instanceof String) {
            this.encoding = (String) encodingValue;
        }

    }

    public byte[] serialize(String topic, NotificationMessage data) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes(this.encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
