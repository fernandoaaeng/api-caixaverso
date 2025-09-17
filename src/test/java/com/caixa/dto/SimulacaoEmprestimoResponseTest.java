package com.caixa.dto;

import com.caixa.dto.SimulacaoEmprestimoResponse;
import com.caixa.model.ProdutoEmprestimo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoEmprestimoResponseTest {

    @Test
    @DisplayName("Deve criar response com construtor vazio")
    void deveCriarResponseComConstrutorVazio() {
        // When
        SimulacaoEmprestimoResponse response = new SimulacaoEmprestimoResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getProduto());
        assertNull(response.getTaxaJurosAnual());
        assertNull(response.getTaxaJurosEfetivaMensal());
        assertNull(response.getValorSolicitado());
        assertNull(response.getValorTotalComJuros());
        assertNull(response.getMemoriaCalculo());
    }

    @Test
    @DisplayName("Deve criar response com construtor parametrizado")
    void deveCriarResponseComConstrutorParametrizado() {
        // Given
        ProdutoEmprestimo produto = new ProdutoEmprestimo("Empréstimo Pessoal", new BigDecimal("18.00"), 60);
        BigDecimal taxaJurosAnual = new BigDecimal("18.00");
        BigDecimal taxaJurosEfetivaMensal = new BigDecimal("1.39");
        BigDecimal valorSolicitado = new BigDecimal("10000.00");
        BigDecimal valorTotalComJuros = new BigDecimal("12000.00");
        List<SimulacaoEmprestimoResponse.DetalhamentoMensal> memoriaCalculo = new ArrayList<>();

        // When
        SimulacaoEmprestimoResponse response = new SimulacaoEmprestimoResponse(
            produto, taxaJurosAnual, taxaJurosEfetivaMensal, valorSolicitado, valorTotalComJuros, memoriaCalculo
        );

        // Then
        assertNotNull(response);
        assertEquals(produto, response.getProduto());
        assertEquals(taxaJurosAnual, response.getTaxaJurosAnual());
        assertEquals(taxaJurosEfetivaMensal, response.getTaxaJurosEfetivaMensal());
        assertEquals(valorSolicitado, response.getValorSolicitado());
        assertEquals(valorTotalComJuros, response.getValorTotalComJuros());
        assertEquals(memoriaCalculo, response.getMemoriaCalculo());
    }

    @Test
    @DisplayName("Deve permitir definir e obter propriedades")
    void devePermitirDefinirEObterPropriedades() {
        // Given
        SimulacaoEmprestimoResponse response = new SimulacaoEmprestimoResponse();
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        BigDecimal taxaJurosAnual = new BigDecimal("20.00");
        BigDecimal taxaJurosEfetivaMensal = new BigDecimal("1.53");
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        BigDecimal valorTotalComJuros = new BigDecimal("6000.00");
        List<SimulacaoEmprestimoResponse.DetalhamentoMensal> memoriaCalculo = new ArrayList<>();

        // When
        response.setProduto(produto);
        response.setTaxaJurosAnual(taxaJurosAnual);
        response.setTaxaJurosEfetivaMensal(taxaJurosEfetivaMensal);
        response.setValorSolicitado(valorSolicitado);
        response.setValorTotalComJuros(valorTotalComJuros);
        response.setMemoriaCalculo(memoriaCalculo);

        // Then
        assertEquals(produto, response.getProduto());
        assertEquals(taxaJurosAnual, response.getTaxaJurosAnual());
        assertEquals(taxaJurosEfetivaMensal, response.getTaxaJurosEfetivaMensal());
        assertEquals(valorSolicitado, response.getValorSolicitado());
        assertEquals(valorTotalComJuros, response.getValorTotalComJuros());
        assertEquals(memoriaCalculo, response.getMemoriaCalculo());
    }

    @Test
    @DisplayName("Deve criar DetalhamentoMensal com construtor vazio")
    void deveCriarDetalhamentoMensalComConstrutorVazio() {
        // When
        SimulacaoEmprestimoResponse.DetalhamentoMensal detalhamento = new SimulacaoEmprestimoResponse.DetalhamentoMensal();

        // Then
        assertNotNull(detalhamento);
        assertNull(detalhamento.getMes());
        assertNull(detalhamento.getValorParcela());
        assertNull(detalhamento.getJuros());
        assertNull(detalhamento.getAmortizacao());
        assertNull(detalhamento.getSaldoDevedor());
    }

    @Test
    @DisplayName("Deve criar DetalhamentoMensal com construtor parametrizado")
    void deveCriarDetalhamentoMensalComConstrutorParametrizado() {
        // Given
        Integer mes = 1;
        BigDecimal valorParcela = new BigDecimal("500.00");
        BigDecimal juros = new BigDecimal("139.00");
        BigDecimal amortizacao = new BigDecimal("361.00");
        BigDecimal saldoDevedor = new BigDecimal("9639.00");

        // When
        SimulacaoEmprestimoResponse.DetalhamentoMensal detalhamento = new SimulacaoEmprestimoResponse.DetalhamentoMensal(
            mes, valorParcela, juros, amortizacao, saldoDevedor
        );

        // Then
        assertNotNull(detalhamento);
        assertEquals(mes, detalhamento.getMes());
        assertEquals(valorParcela, detalhamento.getValorParcela());
        assertEquals(juros, detalhamento.getJuros());
        assertEquals(amortizacao, detalhamento.getAmortizacao());
        assertEquals(saldoDevedor, detalhamento.getSaldoDevedor());
    }

    @Test
    @DisplayName("Deve permitir definir e obter propriedades do DetalhamentoMensal")
    void devePermitirDefinirEObterPropriedadesDoDetalhamentoMensal() {
        // Given
        SimulacaoEmprestimoResponse.DetalhamentoMensal detalhamento = new SimulacaoEmprestimoResponse.DetalhamentoMensal();
        Integer mes = 2;
        BigDecimal valorParcela = new BigDecimal("500.00");
        BigDecimal juros = new BigDecimal("133.82");
        BigDecimal amortizacao = new BigDecimal("366.18");
        BigDecimal saldoDevedor = new BigDecimal("9272.82");

        // When
        detalhamento.setMes(mes);
        detalhamento.setValorParcela(valorParcela);
        detalhamento.setJuros(juros);
        detalhamento.setAmortizacao(amortizacao);
        detalhamento.setSaldoDevedor(saldoDevedor);

        // Then
        assertEquals(mes, detalhamento.getMes());
        assertEquals(valorParcela, detalhamento.getValorParcela());
        assertEquals(juros, detalhamento.getJuros());
        assertEquals(amortizacao, detalhamento.getAmortizacao());
        assertEquals(saldoDevedor, detalhamento.getSaldoDevedor());
    }
}
