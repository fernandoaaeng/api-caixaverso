package com.caixa.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProdutoEmprestimoRequest {

    @NotBlank(message = "Nome do produto é obrigatório")
    public String nome;

    @NotNull(message = "Taxa de juros anual é obrigatória")
    @DecimalMin(value = "0.0", message = "Taxa de juros anual deve ser maior ou igual a 0")
    @DecimalMax(value = "100.0", message = "Taxa de juros anual deve ser menor ou igual a 100")
    public BigDecimal taxaJurosAnual;

    @NotNull(message = "Prazo máximo em meses é obrigatório")
    @Positive(message = "Prazo máximo em meses deve ser positivo")
    public Integer prazoMaximoMeses;

    public ProdutoEmprestimoRequest() {
    }

    public ProdutoEmprestimoRequest(String nome, BigDecimal taxaJurosAnual, Integer prazoMaximoMeses) {
        this.nome = nome;
        this.taxaJurosAnual = taxaJurosAnual;
        this.prazoMaximoMeses = prazoMaximoMeses;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTaxaJurosAnual() {
        return taxaJurosAnual;
    }

    public void setTaxaJurosAnual(BigDecimal taxaJurosAnual) {
        this.taxaJurosAnual = taxaJurosAnual;
    }

    public Integer getPrazoMaximoMeses() {
        return prazoMaximoMeses;
    }

    public void setPrazoMaximoMeses(Integer prazoMaximoMeses) {
        this.prazoMaximoMeses = prazoMaximoMeses;
    }
}
