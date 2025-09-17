package com.caixa.dto;

import com.caixa.dto.SimulacaoEmprestimoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoEmprestimoRequestTest {

    @Test
    @DisplayName("Deve criar request com construtor vazio")
    void deveCriarRequestComConstrutorVazio() {
        // When
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getIdProduto());
        assertNull(request.getValorSolicitado());
        assertNull(request.getPrazoMeses());
    }

    @Test
    @DisplayName("Deve criar request com construtor parametrizado")
    void deveCriarRequestComConstrutorParametrizado() {
        // Given
        Long idProduto = 1L;
        BigDecimal valorSolicitado = new BigDecimal("10000.00");
        Integer prazoMeses = 24;

        // When
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest(idProduto, valorSolicitado, prazoMeses);

        // Then
        assertNotNull(request);
        assertEquals(idProduto, request.getIdProduto());
        assertEquals(valorSolicitado, request.getValorSolicitado());
        assertEquals(prazoMeses, request.getPrazoMeses());
    }

    @Test
    @DisplayName("Deve permitir definir e obter ID do produto")
    void devePermitirDefinirEObterIdProduto() {
        // Given
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();
        Long idProduto = 2L;

        // When
        request.setIdProduto(idProduto);

        // Then
        assertEquals(idProduto, request.getIdProduto());
    }

    @Test
    @DisplayName("Deve permitir definir e obter valor solicitado")
    void devePermitirDefinirEObterValorSolicitado() {
        // Given
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();
        BigDecimal valorSolicitado = new BigDecimal("15000.50");

        // When
        request.setValorSolicitado(valorSolicitado);

        // Then
        assertEquals(valorSolicitado, request.getValorSolicitado());
    }

    @Test
    @DisplayName("Deve permitir definir e obter prazo em meses")
    void devePermitirDefinirEObterPrazoMeses() {
        // Given
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();
        Integer prazoMeses = 36;

        // When
        request.setPrazoMeses(prazoMeses);

        // Then
        assertEquals(prazoMeses, request.getPrazoMeses());
    }
}
