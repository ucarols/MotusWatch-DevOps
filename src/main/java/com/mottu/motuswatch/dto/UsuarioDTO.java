package com.mottu.motuswatch.dto;

import com.mottu.motuswatch.model.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UsuarioDTO(
        Long id,
        @NotBlank String nome,
        String telefone,
        @NotBlank String cpf,
        @Email String email,
        String senha,
        PerfilUsuario perfil,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        Boolean ativo
) {
    public UsuarioDTO() {
        this(null, "", null, "", null, null, null, null, null, null);
    }
}
