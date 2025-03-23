package com.smartpack.dto;

import lombok.Data;

/**
 * Classe EmpresaResponseDto
 */
@Data
public class EmpresaResponseDto {
    private Long id;
    private String email;
    private String nif;
    private String nom;
    private String cognom;
    private String telefon;
    private String adreça;
}
