package com.leave.service.impl;

import com.leave.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private Key key;
    private static volatile JwtServiceImpl instance;

    @PostConstruct
    private void init() {
//        this.key = MacProvider.generateKey();
        // TODO remettre la generation dynamique
        this.key = new SecretKeySpec("test".getBytes(), SignatureAlgorithm.HS256.getValue());
    }

    @Override
    public String generateJWT(String email, Long userId, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setIssuedAt(now);
        if (email != null) {
            builder.setIssuer(email);
        }
        if (userId != null) {
            builder.setSubject(String.valueOf(userId));
        }
        builder.signWith(SignatureAlgorithm.HS256, key);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    @Override
    public Long parseJWT(String jwt) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();

/*      System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());*/
        return StringUtils.isNotBlank(claims.getSubject()) ? Long.valueOf(claims.getSubject()) : null;
    }
}