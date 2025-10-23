package com.webnc.bt.controller;

import com.webnc.bt.dto.response.ActorResponse;
import com.webnc.bt.dto.response.AppApiResponse;
import com.webnc.bt.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webnc.bt.dto.request.ActorRequest;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    /**
     * 👉 View a list of all `actor`
     * GET /api/actors
     */
    @GetMapping
    public AppApiResponse<List<ActorResponse>> getAllActors() {
        List<ActorResponse> actors = actorService.getAllActors();
        return AppApiResponse.<List<ActorResponse>>builder()
                .message("Actors retrieved successfully")
                .code(200)
                .result(actors)
                .build();
    }

    /**
     * 👉 View detail of an `actor`
     * GET /api/actors/{id}
     */
    @GetMapping("/{id}")
    public AppApiResponse<ActorResponse> getActorById(@PathVariable Integer id) {
        ActorResponse actor = actorService.getActorById(id);
        return AppApiResponse.<ActorResponse>builder()
                .message("Actor retrieved successfully")
                .code(200)
                .result(actor)
                .build();
    }

    /**
     * 👉 Add new `actor`
     * POST /api/actors
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Set 201 Created status
    public AppApiResponse<ActorResponse> createActor(@Valid @RequestBody ActorRequest actorRequest) {
        ActorResponse newActor = actorService.createActor(actorRequest);
        return AppApiResponse.<ActorResponse>builder()
                .message("Actor created successfully")
                .code(200)
                .result(newActor)
                .build();
    }

    /**
     * 👉 Update an `actor`
     * PUT /api/actors/{id}
     */
    @PutMapping("/{id}")
    public AppApiResponse<ActorResponse> updateActor(@PathVariable Integer id, @Valid @RequestBody ActorRequest actorRequest) {
        ActorResponse updatedActor = actorService.updateActor(id, actorRequest);
        return AppApiResponse.<ActorResponse>builder()
                .message("Actor updated successfully")
                .code(200)
                .result(updatedActor)
                .build();
    }

    /**
     * 👉 Delete an `actor`
     * DELETE /api/actors/{id}
     */
    @DeleteMapping("/{id}")
    public AppApiResponse<Void> deleteActor(@PathVariable Integer id) {
        actorService.deleteActor(id);
        return AppApiResponse.<Void>builder()
                .message("Actor deleted successfully")
                .code(200)
                .build(); // No result to return
    }
}