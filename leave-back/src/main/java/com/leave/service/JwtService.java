package com.leave.service;

public interface JwtService {
    /**
     * @param email
     * @param userId
     * @param ttlMillis
     * @return
     */
    String generateJWT(String email, Long userId, long ttlMillis);

    Long parseJWT(String jwt);
}