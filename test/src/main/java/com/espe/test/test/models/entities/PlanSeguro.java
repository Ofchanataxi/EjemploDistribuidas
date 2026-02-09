package com.espe.test.test.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "planes")
public class PlanSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String tipo;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal primaBase;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal coberturaMax;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrimaBase() {
        return primaBase;
    }

    public void setPrimaBase(BigDecimal primaBase) {
        this.primaBase = primaBase;
    }

    public BigDecimal getCoberturaMax() {
        return coberturaMax;
    }

    public void setCoberturaMax(BigDecimal coberturaMax) {
        this.coberturaMax = coberturaMax;
    }
}