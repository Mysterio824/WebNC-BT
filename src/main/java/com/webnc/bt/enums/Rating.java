package com.webnc.bt.enums;

import java.util.stream.Stream;

public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"), // This will map to "PG-13" in the DB
    R("R"),
    NC_17("NC-17"); // This will map to "NC-17" in the DB

    private final String value;

    Rating(String value) {
        this.value = value;
    }

    // Getter for the database value
    public String getValue() {
        return value;
    }

    // Static method to find the enum by its database value
    public static Rating fromValue(String value) {
        if (value == null) {
            return null;
        }

        // Loop through all enum constants
        for (Rating rating : values()) {
            if (rating.getValue().equals(value)) {
                return rating;
            }
        }

        // Throw an exception if no match is found
        throw new IllegalArgumentException("No enum constant com.webnc.bt.enums.Rating." + value);
    }
}