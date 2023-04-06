package com.base.redis;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.SerializationException;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.util.Log;

import reactor.core.publisher.Mono;

public class CacheCustomSerializer implements RedisSerializer<Object> {

    private final ObjectMapper om;
    private byte[] dataToSerialze;

    public CacheCustomSerializer() {
        this.om = new ObjectMapper();
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        try {
            Mono obj = (Mono) t;
            obj.subscribe(res -> { 
                try {
                    this.dataToSerialze = om.writeValueAsBytes(res);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                    Log.getLogger().debug(ex.getMessage());
                }
            });
            return dataToSerialze;
        } catch (Exception e) {
            try {
                return om.writeValueAsBytes(t);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                Log.getLogger().debug(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }

        try {
            String str = new String(bytes, StandardCharsets.UTF_8);
            Object obj = om.readValue(str, Object.class);
            return Mono.just(obj);
        } catch (Exception e) {
            e.printStackTrace();
            Log.getLogger().debug(e.getMessage());
        }
        return null;
    }

}
