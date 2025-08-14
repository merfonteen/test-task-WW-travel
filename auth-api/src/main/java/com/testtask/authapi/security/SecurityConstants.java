package com.testtask.authapi.security;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String[] PERMIT_ALL = {
            "/api/auth/register",
            "/api/auth/login",
    };
}
