package com.smartpack.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Classe PaquetRequestDto
 */
@Data
public class PaquetRequestDto {
    private String detalls;
    @NotNull(message = "El pes és obligatori")
    @Min(value = 1, message = "El pes ha de ser major que 0")
    private int pes;
    private String mida;
    private String nomDestinatari;
    private String adreçadestinatari;
    private String telefondestinatari;
    private String codiqr;
}
