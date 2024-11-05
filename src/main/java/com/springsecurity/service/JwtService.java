package com.springsecurity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springsecurity.entity.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String issuer="gurunath";
    private final String algorithmKey="dhediegdhebdh-edjoeoue54-edhbejhgduh";
    private final int expiredAt=840000;

    private Algorithm algorithm;

    private final static String USER_NAME="username";
    private final static String ROLE="role";

    @PostConstruct
    public void PostConstruct(){
       algorithm= Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(Users users){
       return JWT.create()
                .withIssuer(issuer)
                .withClaim(USER_NAME,users.getEmail())
                .withClaim(ROLE,users.getRole())
               .withExpiresAt(new Date(System.currentTimeMillis()+expiredAt))
                .sign(algorithm);
    }

    public String getUsername(String token){
        DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return verify.getClaim(USER_NAME).asString();
    }
}
