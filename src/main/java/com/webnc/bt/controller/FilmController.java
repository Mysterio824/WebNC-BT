package com.webnc.bt.controller;

import com.webnc.bt.dto.request.FilmRequest;
import com.webnc.bt.dto.response.AppApiResponse;
import com.webnc.bt.dto.response.FilmResponse;
import com.webnc.bt.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
@Tag(name = "Film", description = "Endpoints for managing films") // Groups endpoints in Swagger UI
public class FilmController {

    private final FilmService filmService;

    @Operation(summary = "Get all films", description = "Retrieves a complete list of all active films.", operationId = "getAllFilms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Films retrieved successfully")
    })
    @GetMapping
    public AppApiResponse<List<FilmResponse>> getAllFilms() {
        List<FilmResponse> films = filmService.getAllFilms();
        return AppApiResponse.<List<FilmResponse>>builder()
                .message("Films retrieved successfully")
                .code(200)
                .result(films)
                .build();
    }

    @Operation(summary = "Get film detail by ID", description = "Retrieves the details of a specific film by its ID.", operationId = "getFilmById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    @GetMapping("/{id}")
    public AppApiResponse<FilmResponse> getFilmById(
            @Parameter(description = "ID of the film to retrieve", required = true, example = "1")
            @PathVariable Integer id) {

        FilmResponse film = filmService.getFilmById(id);
        return AppApiResponse.<FilmResponse>builder()
                .message("Film retrieved successfully")
                .code(200)
                .result(film)
                .build();
    }

    @Operation(summary = "Add a new film", description = "Creates a new film record in the database.", operationId = "createFilm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Film created successfully"), // Matches @ResponseStatus
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppApiResponse<FilmResponse> createFilm(@Valid @RequestBody FilmRequest filmRequest) {
        FilmResponse newFilm = filmService.createFilm(filmRequest);
        // Note: Your HTTP status is 201, but your custom body code is 200. This is fine.
        return AppApiResponse.<FilmResponse>builder()
                .message("Film created successfully")
                .code(200)
                .result(newFilm)
                .build();
    }

    @Operation(summary = "Update an existing film", description = "Updates the details of an existing film by its ID.", operationId = "updateFilm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    @PutMapping("/{id}")
    public AppApiResponse<FilmResponse> updateFilm(
            @Parameter(description = "ID of the film to update", required = true, example = "1")
            @PathVariable Integer id,

            @Valid @RequestBody FilmRequest filmRequest) {

        FilmResponse updatedFilm = filmService.updateFilm(id, filmRequest);
        return AppApiResponse.<FilmResponse>builder()
                .message("Film updated successfully")
                .code(200)
                .result(updatedFilm)
                .build();
    }

    @Operation(summary = "Delete a film", description = "Deletes a film by its ID. Fails if the film is referenced by other records (e.g., inventory).", operationId = "deleteFilm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Film not found"),
            @ApiResponse(responseCode = "409", description = "Conflict: Cannot delete film due to existing references (inventory, actors, etc.)")
    })
    @DeleteMapping("/{id}")
    public AppApiResponse<Void> deleteFilm(
            @Parameter(description = "ID of the film to delete", required = true, example = "1")
            @PathVariable Integer id) {

        filmService.deleteFilm(id);
        return AppApiResponse.<Void>builder()
                .message("Film deleted successfully")
                .code(200)
                .build();
    }
}