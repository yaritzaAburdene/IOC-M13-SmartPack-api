package com.smartpack.dto;

import lombok.Data;

/**
 * Classe AssignarUsuariEmpresaRequest
 */
@Data
public class AssignarUsuariEmpresaRequest {
    private Long usuariId;
    private Long empresaId;
}
