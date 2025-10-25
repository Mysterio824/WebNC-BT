package com.webnc.bt.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FilmRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @PositiveOrZero(message = "Release year must be positive")
    private Integer releaseYear;

    @NotNull(message = "Language ID is required")
    private Short languageId;

    private Short originalLanguageId;

    @Min(value = 1, message = "Rental duration must be >= 1")
    private Short rentalDuration = 3;

    @DecimalMin(value = "0.0", inclusive = false, message = "Rental rate must be positive")
    private BigDecimal rentalRate = new BigDecimal("4.99");

    @PositiveOrZero(message = "Length must be >= 0")
    private Integer length;

    @DecimalMin(value = "0.0", inclusive = false, message = "Replacement cost must be positive")
    private BigDecimal replacementCost = new BigDecimal("19.99");

    private String rating;

    private String specialFeatures;
}
