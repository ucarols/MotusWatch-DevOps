package com.mottu.motuswatch.dto;

import com.mottu.motuswatch.model.AreaPatio;
import com.mottu.motuswatch.model.ModeloMoto;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record MotoDTO(
        Long id,
        @NotBlank String placa,
        ModeloMoto modelo,
        AreaPatio area,
        String observacao,
        LocalDateTime dataEntrada,
        Long usuarioId,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        Boolean ativo
) {
    public MotoDTO() {
        this(null, "", null, null, null, null, null, null, null, null);
    }
}
