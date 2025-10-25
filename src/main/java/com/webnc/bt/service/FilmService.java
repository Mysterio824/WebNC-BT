package com.webnc.bt.service;

import com.webnc.bt.dto.request.FilmRequest;
import com.webnc.bt.dto.response.FilmResponse;
import com.webnc.bt.entity.*;
import com.webnc.bt.exception.AppException;
import com.webnc.bt.exception.ErrorCode;
import com.webnc.bt.mapper.FilmMapper;
import com.webnc.bt.repository.FilmRepository;
import com.webnc.bt.repository.LanguageRepository; // ADD THIS IMPORT
import com.webnc.bt.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant; // ADD THIS IMPORT
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository; // ADD THIS
    private final FilmMapper filmMapper;
    private final PaymentRepository paymentRepository;


    @Transactional(readOnly = true)
    public List<FilmResponse> getAllFilms() {
        return filmMapper.toDtoList(filmRepository.findAll());
    }

    @Transactional(readOnly = true)
    public FilmResponse getFilmById(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FILM_NOT_FOUND));
        return filmMapper.toDto(film);
    }

    @Transactional
    public FilmResponse createFilm(FilmRequest filmRequest) {
        // 1. Map simple fields from DTO
        Film film = filmMapper.toEntity(filmRequest);

        // 2. FIX: Manually resolve and set relationships
        setFilmRelations(film, filmRequest);

        // 3. FIX: Manually set lastUpdate (since mapper ignores it)
        film.setLastUpdate(Instant.now());

        // 4. Save and return DTO
        Film savedFilm = filmRepository.save(film);
        return filmMapper.toDto(savedFilm);
    }

    @Transactional
    public FilmResponse updateFilm(Integer id, FilmRequest filmRequest) {
        // 1. Find existing entity
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FILM_NOT_FOUND));

        // 2. Map non-null simple fields from DTO
        filmMapper.updateEntityFromDto(filmRequest, film);

        // 3. FIX: Manually resolve and set relationships
        setFilmRelations(film, filmRequest);

        // 4. FIX: Manually set lastUpdate
        film.setLastUpdate(Instant.now());

        // 5. Save and return DTO
        Film updatedFilm = filmRepository.save(film);
        return filmMapper.toDto(updatedFilm);
    }

    @Transactional
    public void deleteFilm(Integer id) {
        // 1. Find the film
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FILM_NOT_FOUND));

        // 2. Clear Many-to-Many join tables (fast)
        film.getActors().clear();
        film.getCategories().clear();

        // 3. Manually delete the dependency chain
        //    (film -> inventory -> rental -> payment)

        // For each copy of the film in inventory...
        for (Inventory inventoryItem : film.getInventory()) {

            // For each rental of that inventory item...
            for (Rental rental : inventoryItem.getRentals()) {

                // Find all payments for that rental
                List<Payment> payments = paymentRepository.findByRental(rental);

                // Delete the payments first
                paymentRepository.deleteAll(payments);
            }
            // Now delete the rentals
            // (This is handled by cascade from Inventory)
        }

        filmRepository.delete(film);
    }

    /**
     * Helper method to find and set Language relations from IDs.
     */
    private void setFilmRelations(Film film, FilmRequest filmRequest) {
        // 1. Set required Language
        // (languageId is @NotNull in FilmRequest, so we don't need to check for null)
        Language language = languageRepository.findById(filmRequest.getLanguageId())
                .orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
        film.setLanguage(language);

        // 2. Set optional Original Language
        if (filmRequest.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(filmRequest.getOriginalLanguageId())
                    .orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
            film.setOriginalLanguage(originalLanguage);
        } else {
            // This handles the case where an update explicitly removes the original language
            film.setOriginalLanguage(null);
        }
    }
}