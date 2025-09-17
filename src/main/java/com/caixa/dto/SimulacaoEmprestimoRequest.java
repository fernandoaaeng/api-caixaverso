package com.caixa.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SimulacaoEmprestimoRequest {

    @NotNull(message = "ID do produto é obrigatório")
    @Positive(message = "ID do produto deve ser positivo")
    public Long idProduto;

    @NotNull(message = "Valor solicitado é obrigatório")
    @Positive(message = "Valor solicitado deve ser positivo")
    public BigDecimal valorSolicitado;

    @NotNull(message = "Prazo em meses é obrigatório")
    @Positive(message = "Prazo em meses deve ser positivo")
    public Integer prazoMeses;

    public SimulacaoEmprestimoRequest() {
    }

    public SimulacaoEmprestimoRequest(Long idProduto, BigDecimal valorSolicitado, Integer prazoMeses) {
        this.idProduto = idProduto;
        this.valorSolicitado = valorSolicitado;
        this.prazoMeses = prazoMeses;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }
}
