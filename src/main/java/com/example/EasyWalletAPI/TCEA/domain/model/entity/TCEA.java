package com.example.EasyWalletAPI.TCEA.domain.model.entity;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TCEA")
public class TCEA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "letter_id", nullable = false)
    private Letter letter;

    private BigDecimal valorEntregado;
    private BigDecimal valorRecibido;
    private BigDecimal tcea;
}
