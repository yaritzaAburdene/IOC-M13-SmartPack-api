package com.smartpack.dto;

import lombok.Data;

@Data
public class VehicleDto {
    private Long id;
    private String marca;
    private String model;
    private String matricula;
}