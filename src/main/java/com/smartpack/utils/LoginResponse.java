package com.smartpack.utils;

import java.util.Date;

/**
 * Classe LoginResponse
 */
public class LoginResponse {
    private String token;

    private Date expiresIn;

    /**
     * Get Token
     * 
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     * set Token
     * 
     * @param token String
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * set Expires IN
     * 
     * @param expiresIn Date
     */
    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Get Expires In
     * 
     * @return Date
     */
    public Date getExpiresIn() {
        return expiresIn;
    }
}
