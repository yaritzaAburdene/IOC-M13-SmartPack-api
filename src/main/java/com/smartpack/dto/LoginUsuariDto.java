package com.smartpack.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe LoginUsuariDto
 */
public class LoginUsuariDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    /**
     * Get Email
     * 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set Email
     * 
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get Password
     * 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set Password
     * 
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
