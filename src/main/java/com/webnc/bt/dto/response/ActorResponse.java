package com.webnc.bt.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ActorResponse {

    private Integer actorId;
    private String firstName;
    private String lastName;
    private LocalDateTime lastUpdate;
}