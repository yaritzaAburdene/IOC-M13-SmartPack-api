package com.smartpack.dto;

import lombok.Data;

@Data
public class PaquetRequestDto {
    private String detalls;
    private int pes;
    private String mida;
    private String nomDestinatari;
    private String adreçadestinatari;
    private String telefondestinatari;
    private String codiqr;
}
