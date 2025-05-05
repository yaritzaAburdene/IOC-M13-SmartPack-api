package com.smartpack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Classe VehicleDto
 */
@Data
public class VehicleDto {
    private Long id;
    private String marca;
    private String model;
    @NotBlank
    private String matricula;
    private String color;
    private String tipus;
}