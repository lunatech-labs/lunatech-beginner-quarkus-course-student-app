package com.lunatech.training.quarkus;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PriceUpdateDeserializer extends ObjectMapperDeserializer<PriceUpdate> {
    public PriceUpdateDeserializer(){
        super(PriceUpdate.class);
    }
}
