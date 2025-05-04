package com.smartpack.dto;

import lombok.Data;

/**
 * Classe UserResponseDto
 */
@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String dni;
    private String nom;
    private String cognom;
    private String telefon;
    private String adreça;
    private String observacio;
    private Long empresaId;
}
