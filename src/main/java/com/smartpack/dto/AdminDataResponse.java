package com.smartpack.dto;

import java.util.List;

import lombok.Data;

@Data
public class AdminDataResponse {
    private List<UserResponseDto> usuaris;
    private List<TransportistaResponseDto> transportistes;
    private List<VehicleDto> vehicles;
}