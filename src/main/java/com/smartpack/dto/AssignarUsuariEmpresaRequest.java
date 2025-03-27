package com.smartpack.dto;

import lombok.Data;

@Data
public class AssignarUsuariEmpresaRequest {
    private Long usuariId;
    private Long empresaId;
}
