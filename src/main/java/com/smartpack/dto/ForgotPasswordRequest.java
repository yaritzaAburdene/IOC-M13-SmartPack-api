package com.smartpack.dto;

/**
 * Classe ForgotPasswordRequest
 */
public class ForgotPasswordRequest {
    private String email;

    private String secret;

    /**
     * Get Email
     * Obtenir email
     * 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set Email
     * modificar email
     * 
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get Secret
     * Obtenir Secret
     * 
     * @return String
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Set Secret
     * modificar Secret
     * 
     * @param secret String
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
