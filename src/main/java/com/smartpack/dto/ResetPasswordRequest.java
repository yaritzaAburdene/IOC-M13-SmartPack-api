package com.smartpack.dto;

/**
 * Classe ResetPasswordRequest
 */
public class ResetPasswordRequest {

    private String token;
    private String newPassword;

    /**
     * Get Token
     * 
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     * Set Token
     * 
     * @param token String
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get New Password
     * 
     * @return String
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * set New Password
     * 
     * @param newPassword String
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
