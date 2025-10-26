package com.webnc.bt.service;

import com.webnc.bt.dto.response.GoogleTokenResponse;
import com.webnc.bt.dto.response.ProfileResponse;
import com.webnc.bt.repository.httpclient.OauthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final OauthClient oauthClient;
    private final JwtTokenProvider jwtTokenProvider;

    public String processGrantCode(String code, String scope, String authUser, String prompt) {
        GoogleTokenResponse googleTokenResponse = oauthClient.getOauthAccessTokenGoogle(code);
        String accessToken  = googleTokenResponse.getAccessToken();
        ProfileResponse profile = oauthClient.getProfileDetailsGoogle(accessToken);
        log.info("Profile Details: {}", profile);

        String appToken = jwtTokenProvider.generateToken(profile);
        log.info("Generated App Token: {}", appToken);

        return appToken;
    }
}
