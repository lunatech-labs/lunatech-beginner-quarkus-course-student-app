package com.lunatech.training.quarkus;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/feature-flags")
@Produces(MediaType.APPLICATION_JSON)
public class FeatureFlagsResource {

    @Inject
    FeatureFlags featureFlags;

    @GET
    public FeatureFlags featureFlags() {
        return featureFlags;
    }


}
