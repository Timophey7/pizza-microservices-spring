package com.menuservice.menuservice.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menuservice.menuservice.model.CartResponse;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CartResponseDeserializer implements Deserializer<CartResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public CartResponse deserialize(String s, byte[] bytes) {
        if (bytes == null){
            return null;
        }
        try{
            return objectMapper.readValue(bytes, CartResponse.class);
        }catch (Exception e){
            throw new SerializationException(e.getMessage());
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
