package com.example.room.manager.guests;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Repository
public class GuestStorage {

    private final AtomicReference<List<Double>> guests = new AtomicReference<>();

    public GuestStorage() {
        reset();
    }

    public List<Double> guests() {
        return guests.get();
    }

    public void add(List<Double> toAdd) {
        guests.accumulateAndGet(toAdd, (g1, g2) -> Stream.concat(g1.stream(), g2.stream()).toList());
    }

    public void reset() {
        guests.set(new ArrayList<>());
    }
}
