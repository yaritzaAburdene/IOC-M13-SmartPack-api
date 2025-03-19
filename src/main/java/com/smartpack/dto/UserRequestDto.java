package com.smartpack.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String email;
    private String password;
    private String role;
    private String nom;
    private String cognom;
    private String telefon;
    private String adreça;
    private String observacio;
}