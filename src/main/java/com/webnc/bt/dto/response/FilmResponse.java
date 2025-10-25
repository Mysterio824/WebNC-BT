package com.webnc.bt.dto.response;

import com.webnc.bt.enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponse {
    private Integer filmId;
    private String title;
    private String description;
    private Integer releaseYear;

    private LanguageResponse language;
    private LanguageResponse originalLanguage;

    private Short rentalDuration;
    private BigDecimal rentalRate;
    private Integer length;
    private BigDecimal replacementCost;
    private Rating rating;
    private String specialFeatures;
    private Instant lastUpdate;
}