package com.webnc.bt.service;

import com.webnc.bt.dto.response.ProfileResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    @Value("${jwt.expiration.ms}")
    private long expirationTime;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKeyString);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(ProfileResponse profile) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                // Set the subject (e.g., user's email or unique Google ID)
                .subject(profile.getEmail())
                // Add custom claims
                .claim("name", profile.getName())
                .claim("picture", profile.getPicture())
                .claim("userId", profile.getId()) // Add Google ID as a claim
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();
    }
}