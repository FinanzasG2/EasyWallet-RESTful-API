package com.example.EasyWalletAPI.Managment.domain.model.entity;

import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import com.example.EasyWalletAPI.TCEA.domain.model.entity.TCEA;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="letters")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal valorNominal;
    private Date fechaRegistro;
    private Date fechaVencimiento;
    private Date fechaDescuento;

    private String letternumber;
    private String currency;

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CostoAdicional> costosAdicionales = new ArrayList<>();

    @OneToMany(mappedBy = "letter", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Tasa> tasas;

    @OneToOne(mappedBy = "letter", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private TCEA tcea;
}
