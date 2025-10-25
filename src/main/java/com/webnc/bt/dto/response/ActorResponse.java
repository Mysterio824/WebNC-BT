package com.webnc.bt.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorResponse {

    private Integer actorId;
    private String firstName;
    private String lastName;
    private Instant lastUpdate;
}