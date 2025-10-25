package com.webnc.bt.mapper;

import com.webnc.bt.dto.request.ActorRequest;
import com.webnc.bt.dto.response.ActorResponse;
import com.webnc.bt.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorResponse toDto(Actor actor);

    List<ActorResponse> toDtoList(List<Actor> actors);

    Actor toEntity(ActorRequest actorRequest);

    void updateEntityFromDto(ActorRequest actorRequest, @MappingTarget Actor actor);
}
