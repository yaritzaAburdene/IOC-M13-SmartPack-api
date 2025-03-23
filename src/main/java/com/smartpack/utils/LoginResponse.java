package com.smartpack.utils;

/**
 * Classe LoginResponse
 */
public class LoginResponse {

    private String token;

    private String role;

    private String expiresIn;

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
     * Get Role
     * 
     * @return String
     */
    public String getRole() {
        return role;
    }

    /**
     * set Role
     * 
     * @param role String
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * set Expires IN
     * 
     * @param expiresIn String
     */
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Get Expires In
     * 
     * @return String
     */
    public String getExpiresIn() {
        return expiresIn;
    }
}
