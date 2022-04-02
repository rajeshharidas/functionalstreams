package com.bookedevents.streams.demo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BookingStatus {
    PENDING("PENDING"),
    RECEIVED("RECEIVED"),
    RESERVED("RESERVED"),
    CANCELED("CANCELED");

    private final String name;

    public String toString() {
        return this.name;
    }
}
