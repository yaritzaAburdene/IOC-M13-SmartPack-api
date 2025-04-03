package com.smartpack.dto;

import lombok.Data;

@Data
public class PaquetResponseDto {
    private Long id;
    private String detalls;
    private int pes;
    private String mida;
    private String nomDestinatari;
    private String adreçadestinatari;
    private String telefondestinatari;
    private String codiqr;
}
