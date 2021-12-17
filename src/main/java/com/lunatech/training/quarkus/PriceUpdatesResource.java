package com.lunatech.training.quarkus;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestSseElementType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Path("/prices")
public class PriceUpdatesResource {

    @Inject
    EventBus bus;

    private final Random random = new Random();

    @Channel("price-updates-in")
    Multi<PriceUpdate> priceUpdates;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    public Multi<PriceUpdate> prices() {
        return priceUpdates;
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Multi<PriceUpdate> pricesForProduct(@PathParam("id") Long id) {
        return priceUpdates.filter(priceUpdate -> priceUpdate.productId.equals(id));
    }

    @ConsumeEvent(value = "price")
    Uni<PriceUpdate> priceConsume(PriceUpdate price) {
        return Uni.createFrom().item(() -> price).onFailure().recoverWithItem(price);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("eventbus-price")
    public Uni<PriceUpdate> price() {

        PriceUpdate price = new PriceUpdate(1L, new BigDecimal(random.nextInt(100)));

        if (price.price.intValue() >30 ){
            return bus.<PriceUpdate>request("price", price )
                    .onItem().transform(Message::body);
        }else {
            return Uni.createFrom().failure(new RuntimeException("Price is less than 30"));
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("eventbus-prices")
    public Uni<PriceUpdate> sendPrices() {

        List<PriceUpdate> prices = new ArrayList<>();

        for (int i = 1; i <=7; i++ ){
            prices.add(new PriceUpdate((long) i, new BigDecimal(random.nextInt(100))));
        }

        return bus.<PriceUpdate>request("prices", PriceUpdateList.wrap(prices.stream().filter(price -> price.price.intValue()>=30).collect(Collectors.toList())))
                .onItem().transform(Message::body);
    }

    @ConsumeEvent("prices")
    Uni<PriceUpdateList> consumePrices(PriceUpdateList prices) {
        return Uni.createFrom().item(() -> prices);
    }

}
