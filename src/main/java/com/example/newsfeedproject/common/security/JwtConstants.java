package com.example.newsfeedproject.common.security;

public class JwtConstants {

    public static final String Secret = "64K07J2867Cw7JuA7Lqg7ZSEMTDsobDribTsiqTtlLzrk5ztlITroZzsoJ3tirg";
    public static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;
    public static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;
    public static final String REFRESH_TOKEN = "refreshToken";
}
