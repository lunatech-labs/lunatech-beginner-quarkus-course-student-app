package com.lunatech.training.quarkus;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.pgclient.PgConnection;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.pgclient.PgNotification;
import org.jboss.resteasy.reactive.RestSseElementType;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/channel")
public class ListenNotifyResource {

    @Inject
    PgPool client;

    @Path("{channel}")
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    public Multi<JsonObject> listen(@PathParam("channel") String channel) {
        return client
                .getConnection()
                .toMulti()
                .flatMap(connection -> {
                    Multi<PgNotification> notifications = Multi.createFrom().
                            emitter(c -> toPgConnection(connection).notificationHandler(c::emit));

                    return connection.query("LISTEN " + channel)
                            .execute()
                            .toMulti()
                            .flatMap(__ -> notifications);
                })
                .map(PgNotification::toJson);
    }

    @Path("{channel}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.WILDCARD)
    public Uni<String> notif(@PathParam("channel") String channel, String stuff) {
        return client.preparedQuery("NOTIFY " + channel +  ", $$" + stuff + "$$")
                .execute()
                .map(rs -> "Posted to " + channel + " channel");
    }

    // We have to do some type juggling here. Solved in the mutiny client v2.
    PgConnection toPgConnection(SqlConnection sqlConnection) {
        return new PgConnection((io.vertx.pgclient.PgConnection) sqlConnection.getDelegate());
    }
}
