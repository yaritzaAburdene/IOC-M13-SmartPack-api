package com.smartpack.dto;

import com.smartpack.models.Estat;

import lombok.Data;

/**
 * Classe ServeiResponseDto
 */
@Data
public class ServeiResponseDto {
    private Long id;
    private Estat estat;
    private Long usuariId;
    private Long transportistaId;
    private PaquetResponseDto paquet;
}
