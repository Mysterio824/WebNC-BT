package com.webnc.bt.converter;

import com.webnc.bt.enums.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * This converter automatically applies to all fields of type 'Rating'.
 * It correctly maps between the Java enum (e.g., NC_17)
 * and the database string (e.g., "NC-17").
 */
@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    /**
     * Called when saving to the database.
     * Converts the enum (e.g., Rating.NC_17) to the DB string (e.g., "NC-17").
     */
    @Override
    public String convertToDatabaseColumn(Rating rating) {
        if (rating == null) {
            return null;
        }
        return rating.getValue();
    }

    /**
     * Called when loading from the database.
     * Converts the DB string (e.g., "NC-17") to the enum (e.g., Rating.NC_17).
     */
    @Override
    public Rating convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Rating.fromValue(dbData);
    }
}