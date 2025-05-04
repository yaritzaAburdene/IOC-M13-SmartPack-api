package com.smartpack.dto;

import lombok.Data;

/**
 * Classe UserRequestDto
 */
@Data
public class UserRequestDto {
    private String email;
    private String password;
    private String role;
    private String dni;
    private String nom;
    private String cognom;
    private String telefon;
    private String adreça;
    private String observacio;
    private String secret;
}