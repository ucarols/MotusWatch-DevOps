package com.mottu.motuswatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "motos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Placa é obrigatória")
    @Column(unique = true)
    private String placa;

    @Enumerated(EnumType.STRING)
    private ModeloMoto modelo;

    @Enumerated(EnumType.STRING)
    private AreaPatio area;

    private String observacao;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Builder.Default
    private Boolean ativo = true;

}
