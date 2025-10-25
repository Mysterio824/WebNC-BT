package com.webnc.bt.mapper;

import com.webnc.bt.dto.request.FilmRequest;
import com.webnc.bt.dto.response.FilmResponse;
import com.webnc.bt.entity.Film;
import com.webnc.bt.enums.Rating;
import org.mapstruct.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.Year;
import java.util.List;

// --- UPDATE THIS LINE ---
@Mapper(componentModel = "spring",
        uses = { LanguageMapper.class }, // Tell FilmMapper to use LanguageMapper
        imports = {Year.class, Timestamp.class, Instant.class, Rating.class})
public interface FilmMapper {

    // =================================================================
    //  ENTITY -> DTO (Film to FilmResponse)
    // =================================================================

    /**
     * Maps a Film Entity to a FilmResponse DTO.
     */
    // --- UPDATE THIS MAPPING ---
    // No more "language.languageId". MapStruct will see film.getLanguage()
    // and automatically call LanguageMapper.toDto(film.getLanguage()).
    @Mapping(source = "releaseYear", target = "releaseYear", qualifiedByName = "yearToInteger")
    FilmResponse toDto(Film film);

    List<FilmResponse> toDtoList(List<Film> films);

    // =================================================================
    //  DTO -> ENTITY (FilmRequest to Film)
    // =================================================================

    // This part remains the same, as FilmRequest still uses IDs.
    // Your service logic for handling relations is still correct.

    @Mapping(target = "filmId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "originalLanguage", ignore = true)
    @Mapping(source = "releaseYear", target = "releaseYear", qualifiedByName = "integerToYear")
    @Mapping(source = "rating", target = "rating", qualifiedByName = "stringToRating")
    Film toEntity(FilmRequest filmRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "filmId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "originalLanguage", ignore = true)
    @Mapping(source = "releaseYear", target = "releaseYear", qualifiedByName = "integerToYear")
    @Mapping(source = "rating", target = "rating", qualifiedByName = "stringToRating")
    void updateEntityFromDto(FilmRequest filmRequest, @MappingTarget Film film);

    // =================================================================
    //  CUSTOM HELPER METHODS
    // =================================================================
    // (These all stay the same)

    @Named("yearToInteger")
    default Integer yearToInteger(Year year) {
        return (year == null) ? null : year.getValue();
    }

    @Named("integerToYear")
    default Year integerToYear(Integer year) {
        return (year == null || year <= 0) ? null : Year.of(year);
    }

    @Named("stringToRating")
    default Rating stringToRating(String rating) {
        if (rating == null || rating.isBlank()) {
            return null;
        }
        try {
            return Rating.valueOf(rating.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}