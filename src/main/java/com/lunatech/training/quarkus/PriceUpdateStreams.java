package com.lunatech.training.quarkus;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;

public class PriceUpdateStreams {

    private final static BigDecimal MINIMUM_PRICE = new BigDecimal("30");

    private final Random random = new Random();

    @Outgoing("raw-price-updates-out")
    public Multi<PriceUpdate> generate() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
                .onOverflow().drop()
                .flatMap(tick ->
                        Multi.createFrom().range(1, 8).map(productId ->
                                new PriceUpdate(productId.longValue(), new BigDecimal(random.nextInt(100)))));
    }

    @Incoming("raw-price-updates-in")
    @Outgoing("price-updates-out")
    public PriceUpdate process(PriceUpdate update) {
        if(update.price.compareTo(MINIMUM_PRICE) < 0) {
            throw new RuntimeException("Invalid price");
        }
        return update;
    }

}
