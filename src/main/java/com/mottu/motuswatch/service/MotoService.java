package com.mottu.motuswatch.service;

import com.mottu.motuswatch.dto.MotoDTO;
import com.mottu.motuswatch.exception.NotFoundException;
import com.mottu.motuswatch.model.Moto;
import com.mottu.motuswatch.model.Usuario;
import com.mottu.motuswatch.repository.MotoRepository;
import com.mottu.motuswatch.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public MotoDTO save(MotoDTO dto) {
        // Verifica se a placa já existe
        if (dto.id() == null) {
            // Nova moto - verifica se placa já existe
            boolean placaExiste = repository.existsByPlaca(dto.placa());
            if (placaExiste) {
                throw new IllegalArgumentException("Já existe uma moto com a placa: " + dto.placa());
            }
        } else {
            // Edição - verifica se placa foi alterada e se já existe
            Moto motoExistente = repository.findById(dto.id())
                    .orElseThrow(() -> new NotFoundException("Moto não encontrada"));
            
            if (!motoExistente.getPlaca().equals(dto.placa())) {
                boolean placaExiste = repository.existsByPlaca(dto.placa());
                if (placaExiste) {
                    throw new IllegalArgumentException("Já existe uma moto com a placa: " + dto.placa());
                }
            }
        }
        
        // Pega automaticamente o usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        // Para usuários em memória (admin/user), cria um usuário temporário
        Usuario usuario;
        if ("admin".equals(username)) {
            usuario = usuarioRepository.findByEmail("admin@motuswatch.com")
                    .orElseGet(() -> {
                        Usuario novoUsuario = Usuario.builder()
                                .nome("Administrador")
                                .email("admin@motuswatch.com")
                                .cpf("00000000000")
                                .senha("$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVqLx6/3j8J8J8J8J8J8J8J8J8J")
                                .perfil(com.mottu.motuswatch.model.PerfilUsuario.ADMIN)
                                .dataCriacao(LocalDateTime.now())
                                .ativo(true)
                                .build();
                        return usuarioRepository.save(novoUsuario);
                    });
        } else if ("user".equals(username)) {
            usuario = usuarioRepository.findByEmail("user@motuswatch.com")
                    .orElseGet(() -> {
                        Usuario novoUsuario = Usuario.builder()
                                .nome("Usuário")
                                .email("user@motuswatch.com")
                                .cpf("11111111111")
                                .senha("$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVqLx6/3j8J8J8J8J8J8J8J8J8J")
                                .perfil(com.mottu.motuswatch.model.PerfilUsuario.USER)
                                .dataCriacao(LocalDateTime.now())
                                .ativo(true)
                                .build();
                        return usuarioRepository.save(novoUsuario);
                    });
        } else {
            usuario = usuarioRepository.findByEmail(username)
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        }

        Moto moto;
        if (dto.id() != null) {
            moto = repository.findById(dto.id())
                    .orElseThrow(() -> new NotFoundException("Moto não encontrada"));
            moto.setPlaca(dto.placa());
            moto.setModelo(dto.modelo());
            moto.setArea(dto.area());
            moto.setObservacao(dto.observacao());
            moto.setDataAtualizacao(LocalDateTime.now());
        } else {
            moto = Moto.builder()
                    .placa(dto.placa())
                    .modelo(dto.modelo())
                    .area(dto.area())
                    .observacao(dto.observacao())
                    .dataEntrada(LocalDateTime.now())
                    .usuario(usuario)
                    .dataCriacao(LocalDateTime.now())
                    .ativo(true)
                    .build();
        }

        Moto saved = repository.save(moto);
        return toDTO(saved);
    }

    public Page<MotoDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    @Cacheable("motosPorArea")
    public Page<MotoDTO> buscarPorArea(String area, Pageable pageable) {
        try {
            var areaEnum = com.mottu.motuswatch.model.AreaPatio.valueOf(area.toUpperCase());
            return repository.findByArea(areaEnum, pageable).map(this::toDTO);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Área inválida: " + area);
        }
    }

    public Page<MotoDTO> buscarPorPlaca(String placa, Pageable pageable) {
        return repository.findByPlacaContainingIgnoreCase(placa, pageable).map(this::toDTO);
    }

    public MotoDTO findById(Long id) {
        Moto moto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Moto não encontrada"));
        return toDTO(moto);
    }

    public void deleteById(Long id) {
        Moto moto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Moto não encontrada"));
        repository.delete(moto);
    }

    public long count() {
        return repository.count();
    }

    private MotoDTO toDTO(Moto moto) {
        return new MotoDTO(
                moto.getId(),
                moto.getPlaca(),
                moto.getModelo(),
                moto.getArea(),
                moto.getObservacao(),
                moto.getDataEntrada(),
                moto.getUsuario() != null ? moto.getUsuario().getId() : null,
                moto.getDataCriacao(),
                moto.getDataAtualizacao(),
                moto.getAtivo()
        );
    }
}
