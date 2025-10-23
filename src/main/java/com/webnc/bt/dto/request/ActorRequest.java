package com.webnc.bt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActorRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 45, message = "First name cannot be longer than 45 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 45, message = "Last name cannot be longer than 45 characters")
    private String lastName;
}