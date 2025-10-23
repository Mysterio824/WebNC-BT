package com.webnc.bt.service;

import com.webnc.bt.dto.request.ActorRequest;
import com.webnc.bt.dto.response.ActorResponse;
import com.webnc.bt.entity.Actor;
import com.webnc.bt.exception.AppException;
import com.webnc.bt.exception.ErrorCode;
import com.webnc.bt.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    @Transactional(readOnly = true)
    public List<ActorResponse> getAllActors() {
        return actorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ActorResponse getActorById(Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_FOUND));
        return convertToDto(actor);
    }

    @Transactional
    public ActorResponse createActor(ActorRequest actorRequest) {
        Actor actor = new Actor();
        actor.setFirstName(actorRequest.getFirstName());
        actor.setLastName(actorRequest.getLastName());

        Actor savedActor = actorRepository.save(actor);
        return convertToDto(savedActor);
    }

    @Transactional
    public ActorResponse updateActor(Integer id, ActorRequest actorRequest) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_FOUND));

        actor.setFirstName(actorRequest.getFirstName());
        actor.setLastName(actorRequest.getLastName());

        Actor updatedActor = actorRepository.save(actor);
        return convertToDto(updatedActor);
    }

    @Transactional
    public void deleteActor(Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_FOUND));
        actorRepository.delete(actor);
    }

    private ActorResponse convertToDto(Actor actor) {
        return ActorResponse.builder()
                .actorId(actor.getActorId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .lastUpdate(actor.getLastUpdate())
                .build();
    }
}