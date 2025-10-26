package com.webnc.bt.controller;

import com.webnc.bt.dto.response.AppApiResponse;
import com.webnc.bt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/grantcode")
    public AppApiResponse<String> grantCode(@RequestParam("code") String code,
                                            @RequestParam("scope") String scope,
                                            @RequestParam("authuser") String authUser,
                                            @RequestParam("prompt") String prompt) {
        return AppApiResponse.<String>builder()
                .code(200)
                .result(authService.processGrantCode(code, scope, authUser, prompt))
                .build();
    }
}