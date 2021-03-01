package com.lunatech.training.quarkus;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.Random;

public class PriceUpdateStreams {

    private final Random random = new Random();

    @Outgoing("raw-price-updates")
    public Multi<PriceUpdate> generate() {
        // To be implemented!
    }

    @Incoming("raw-price-updates")
    public void print(PriceUpdate update) {
        System.out.println("Observed price update: " + update);
    }
}
