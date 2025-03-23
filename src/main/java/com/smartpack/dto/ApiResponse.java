package com.smartpack.dto;

/**
 * Classe ApiResponse
 * per respostes generiques
 */
public class ApiResponse {
    private String message;

    /**
     * Constructor
     * 
     * @param message string
     */
    public ApiResponse(String message) {
        this.message = message;
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
}
