package com.caixa.dto;

import java.math.BigDecimal;
import java.util.List;

import com.caixa.model.ProdutoEmprestimo;

public class SimulacaoEmprestimoResponse {

    public ProdutoEmprestimo produto;
    public BigDecimal taxaJurosAnual;
    public BigDecimal taxaJurosEfetivaMensal;
    public BigDecimal valorSolicitado;
    public BigDecimal valorTotalComJuros;
    public List<DetalhamentoMensal> memoriaCalculo;

    public SimulacaoEmprestimoResponse() {
    }

    public SimulacaoEmprestimoResponse(ProdutoEmprestimo produto, BigDecimal taxaJurosAnual, 
                                     BigDecimal taxaJurosEfetivaMensal, BigDecimal valorSolicitado, 
                                     BigDecimal valorTotalComJuros, List<DetalhamentoMensal> memoriaCalculo) {
        this.produto = produto;
        this.taxaJurosAnual = taxaJurosAnual;
        this.taxaJurosEfetivaMensal = taxaJurosEfetivaMensal;
        this.valorSolicitado = valorSolicitado;
        this.valorTotalComJuros = valorTotalComJuros;
        this.memoriaCalculo = memoriaCalculo;
    }

    public static class DetalhamentoMensal {
        public Integer mes;
        public BigDecimal valorParcela;
        public BigDecimal juros;
        public BigDecimal amortizacao;
        public BigDecimal saldoDevedor;

        public DetalhamentoMensal() {
        }

        public DetalhamentoMensal(Integer mes, BigDecimal valorParcela, BigDecimal juros, 
                                BigDecimal amortizacao, BigDecimal saldoDevedor) {
            this.mes = mes;
            this.valorParcela = valorParcela;
            this.juros = juros;
            this.amortizacao = amortizacao;
            this.saldoDevedor = saldoDevedor;
        }

        public Integer getMes() {
            return mes;
        }

        public void setMes(Integer mes) {
            this.mes = mes;
        }

        public BigDecimal getValorParcela() {
            return valorParcela;
        }

        public void setValorParcela(BigDecimal valorParcela) {
            this.valorParcela = valorParcela;
        }

        public BigDecimal getJuros() {
            return juros;
        }

        public void setJuros(BigDecimal juros) {
            this.juros = juros;
        }

        public BigDecimal getAmortizacao() {
            return amortizacao;
        }

        public void setAmortizacao(BigDecimal amortizacao) {
            this.amortizacao = amortizacao;
        }

        public BigDecimal getSaldoDevedor() {
            return saldoDevedor;
        }

        public void setSaldoDevedor(BigDecimal saldoDevedor) {
            this.saldoDevedor = saldoDevedor;
        }
    }

    public ProdutoEmprestimo getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEmprestimo produto) {
        this.produto = produto;
    }

    public BigDecimal getTaxaJurosAnual() {
        return taxaJurosAnual;
    }

    public void setTaxaJurosAnual(BigDecimal taxaJurosAnual) {
        this.taxaJurosAnual = taxaJurosAnual;
    }

    public BigDecimal getTaxaJurosEfetivaMensal() {
        return taxaJurosEfetivaMensal;
    }

    public void setTaxaJurosEfetivaMensal(BigDecimal taxaJurosEfetivaMensal) {
        this.taxaJurosEfetivaMensal = taxaJurosEfetivaMensal;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public BigDecimal getValorTotalComJuros() {
        return valorTotalComJuros;
    }

    public void setValorTotalComJuros(BigDecimal valorTotalComJuros) {
        this.valorTotalComJuros = valorTotalComJuros;
    }

    public List<DetalhamentoMensal> getMemoriaCalculo() {
        return memoriaCalculo;
    }

    public void setMemoriaCalculo(List<DetalhamentoMensal> memoriaCalculo) {
        this.memoriaCalculo = memoriaCalculo;
    }
}
