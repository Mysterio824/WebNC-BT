package com.webnc.bt.controller;

import com.webnc.bt.dto.request.ActorRequest;
import com.webnc.bt.dto.response.ActorResponse;
import com.webnc.bt.dto.response.AppApiResponse;
import com.webnc.bt.service.ActorService;
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
@RequestMapping("/api/actors")
@RequiredArgsConstructor
@Tag(name = "Actor", description = "Endpoints for managing actors") // Groups endpoints in Swagger UI
public class ActorController {

    private final ActorService actorService;

    @Operation(summary = "Get all actors", description = "Retrieves a complete list of all actors.", operationId = "getAllActors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actors retrieved successfully")
    })
    @GetMapping
    public AppApiResponse<List<ActorResponse>> getAllActors() {
        List<ActorResponse> actors = actorService.getAllActors();
        return AppApiResponse.<List<ActorResponse>>builder()
                .message("Actors retrieved successfully")
                .code(200)
                .result(actors)
                .build();
    }

    @Operation(summary = "Get actor detail by ID", description = "Retrieves the details of a specific actor by their ID.", operationId = "getActorById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    @GetMapping("/{id}")
    public AppApiResponse<ActorResponse> getActorById(
            @Parameter(description = "ID of the actor to retrieve", required = true, example = "1")
            @PathVariable Integer id) {

        ActorResponse actor = actorService.getActorById(id);
        return AppApiResponse.<ActorResponse>builder()
                .message("Actor retrieved successfully")
                .code(200)
                .result(actor)
                .build();
    }

    @Operation(summary = "Add a new actor", description = "Creates a new actor record in the database.", operationId = "createActor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppApiResponse<ActorResponse> createActor(@Valid @RequestBody ActorRequest actorRequest) {
        ActorResponse newActor = actorService.createActor(actorRequest);
        return AppApiResponse.<ActorResponse>builder()
                .message("Actor created successfully")
                .code(200)
                .result(newActor)
                .build();
    }

    @Operation(summary = "Update an existing actor", description = "Updates the details of an existing actor by their ID.", operationId = "updateActor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    @PutMapping("/{id}")
    public AppApiResponse<ActorResponse> updateActor(
            @Parameter(description = "ID of the actor to update", required = true, example = "1")
            @PathVariable Integer id,

            @Valid @RequestBody ActorRequest actorRequest) {

        ActorResponse updatedActor = actorService.updateActor(id, actorRequest);
        return AppApiResponse.<ActorResponse>builder()
                .message("Actor updated successfully")
                .code(200)
                .result(updatedActor)
                .build();
    }

    @Operation(summary = "Delete an actor", description = "Deletes an actor by their ID. Fails if the actor is referenced in any films.", operationId = "deleteActor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Actor not found"),
            @ApiResponse(responseCode = "409", description = "Conflict: Cannot delete actor due to existing film references")
    })
    @DeleteMapping("/{id}")
    public AppApiResponse<Void> deleteActor(
            @Parameter(description = "ID of the actor to delete", required = true, example = "1")
            @PathVariable Integer id) {

        actorService.deleteActor(id);
        return AppApiResponse.<Void>builder()
                .message("Actor deleted successfully")
                .code(200)
                .build();
    }
}