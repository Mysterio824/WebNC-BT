package com.webnc.bt.service;

import com.webnc.bt.dto.request.ActorRequest;
import com.webnc.bt.dto.response.ActorResponse;
import com.webnc.bt.entity.Actor;
import com.webnc.bt.exception.AppException;
import com.webnc.bt.exception.ErrorCode;
import com.webnc.bt.mapper.ActorMapper;
import com.webnc.bt.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Transactional(readOnly = true)
    public List<ActorResponse> getAllActors() {
        return actorMapper.toDtoList(actorRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ActorResponse getActorById(Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_FOUND));
        return actorMapper.toDto(actor);
    }

    @Transactional
    public ActorResponse createActor(ActorRequest actorRequest) {
        Actor actor = actorMapper.toEntity(actorRequest);
        Actor savedActor = actorRepository.save(actor);
        return actorMapper.toDto(savedActor);
    }

    @Transactional
    public ActorResponse updateActor(Integer id, ActorRequest actorRequest) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_FOUND));

        actorMapper.updateEntityFromDto(actorRequest, actor);
        Actor updatedActor = actorRepository.save(actor);
        return actorMapper.toDto(updatedActor);
    }

    @Transactional
    public void deleteActor(Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_FOUND));
        actorRepository.delete(actor);
    }
}
