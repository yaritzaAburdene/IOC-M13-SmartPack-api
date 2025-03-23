package com.smartpack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Classe EmpresaRequestDto
 */
@Data
public class EmpresaRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String nif;
    private String nom;
    private String cognom;
    private String telefon;
    private String adreça;
}