package com.smartpack.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String nom;
    private String cognom;
    private String telefon;
    private String adreça;
    private String observacio;
}
