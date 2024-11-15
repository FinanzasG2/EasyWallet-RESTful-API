package com.example.EasyWalletAPI.Managment.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Tasas")
public class Tasa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "letter_id", nullable = false)
    private Letter letter;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Frecuency frecuency;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Capitalizacion capitalizacion;
}
