package com.customerservice.customerservice.config.kafka;

import com.customerservice.customerservice.models.CartResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CartResponseSerializer implements Serializer<CartResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, CartResponse cartResponse) {
       if (cartResponse == null) {
           return null;
       }
       try{
            return objectMapper.writeValueAsBytes(cartResponse);
       }catch (Exception e){
           throw new SerializationException(e);
       }
    }


    @Override
    public void close() {
        Serializer.super.close();
    }
}
