package com.smartpack.dto;

/**
 * Classe ForgotPasswordResponse
 */
public class ForgotPasswordResponse {

    private String message;
    private String tokerecovery;

    /**
     * Constructor ForgotPasswordResponse
     * 
     * @param message      String
     * @param tokerecovery String
     */
    public ForgotPasswordResponse(String message, String tokerecovery) {
        this.message = message;
        this.tokerecovery = tokerecovery;
    }

    /**
     * Get Message
     * Obtenir mesanje
     * 
     * @return string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get Email
     * Obtenir email
     * 
     * @return String
     */
    public String getTokenRecovery() {
        return tokerecovery;
    }

}
