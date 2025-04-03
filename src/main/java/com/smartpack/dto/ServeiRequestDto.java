package com.smartpack.dto;

import com.smartpack.models.Estat;
import lombok.Data;

@Data
public class ServeiRequestDto {
    private Estat estat;
    private Long usuariId;
    private Long transportistaId;
    private PaquetRequestDto paquet;
}
