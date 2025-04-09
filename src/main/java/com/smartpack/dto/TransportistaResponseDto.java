package com.smartpack.dto;

import lombok.Data;

/**
 * Classe TransportistaResponseDto
 */
@Data
public class TransportistaResponseDto {
    private Long id;
    private Long usuariId;
    private String usuariEmail;
    private String llicencia;
    private VehicleDto vehicle;
}
