package com.mottu.motuswatch.service;

import com.mottu.motuswatch.dto.UsuarioDTO;
import com.mottu.motuswatch.exception.NotFoundException;
import com.mottu.motuswatch.model.Usuario;
import com.mottu.motuswatch.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO save(UsuarioDTO dto) {
        Usuario usuario;
        if (dto.id() != null) {
            usuario = repository.findById(dto.id())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            usuario.setNome(dto.nome());
            usuario.setTelefone(dto.telefone());
            usuario.setCpf(dto.cpf());
            usuario.setEmail(dto.email());
            usuario.setPerfil(dto.perfil());
            usuario.setDataAtualizacao(LocalDateTime.now());
            if (dto.senha() != null && !dto.senha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(dto.senha()));
            }
        } else {
            usuario = Usuario.builder()
                    .nome(dto.nome())
                    .telefone(dto.telefone())
                    .cpf(dto.cpf())
                    .email(dto.email())
                    .senha(passwordEncoder.encode(dto.senha()))
                    .perfil(dto.perfil() != null ? dto.perfil() : com.mottu.motuswatch.model.PerfilUsuario.USER)
                    .dataCriacao(LocalDateTime.now())
                    .ativo(true)
                    .build();
        }

        Usuario salvo = repository.save(usuario);
        return toDTO(salvo);
    }

    public Page<UsuarioDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    public UsuarioDTO findById(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return toDTO(usuario);
    }

    public void deleteById(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        repository.delete(usuario);
    }

    public long count() {
        return repository.count();
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getCpf(),
                usuario.getEmail(),
                null, 
                usuario.getPerfil(),
                usuario.getDataCriacao(),
                usuario.getDataAtualizacao(),
                usuario.getAtivo()
        );
    }
}
