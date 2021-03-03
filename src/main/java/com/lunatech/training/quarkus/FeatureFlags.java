package com.lunatech.training.quarkus;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Singleton;

@Singleton
public class FeatureFlags {

    @ConfigProperty(name = "feature-flags.product-details")
    public Boolean productDetails;

    @ConfigProperty(name = "feature-flags.product-search")
    public Boolean productSearch;

    @ConfigProperty(name = "feature-flags.product-update")
    public Boolean productUpdate;

    @ConfigProperty(name = "feature-flags.reactive-prices")
    public Boolean reactivePrices;

}
