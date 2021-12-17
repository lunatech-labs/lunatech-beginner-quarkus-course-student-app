package com.lunatech.training.quarkus;

import java.util.AbstractList;
import java.util.List;

public class PriceUpdateList extends AbstractList<PriceUpdate> {

    private List<PriceUpdate> wrapped;

    private PriceUpdateList(List<PriceUpdate> wrapped) {
        this.wrapped = wrapped;
    }

    public static PriceUpdateList wrap(List<PriceUpdate> list) {
        return new PriceUpdateList(list);
    }

    @Override
    public PriceUpdate get(int index) {
        return wrapped.get(index);
    }

    @Override
    public int size() {
        return wrapped.size();
    }
}