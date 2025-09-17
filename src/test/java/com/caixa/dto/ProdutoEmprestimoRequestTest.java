package com.caixa.dto;

import com.caixa.dto.ProdutoEmprestimoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoEmprestimoRequestTest {

    @Test
    @DisplayName("Deve criar request com construtor vazio")
    void deveCriarRequestComConstrutorVazio() {
        // When
        ProdutoEmprestimoRequest request = new ProdutoEmprestimoRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getNome());
        assertNull(request.getTaxaJurosAnual());
        assertNull(request.getPrazoMaximoMeses());
    }

    @Test
    @DisplayName("Deve criar request com construtor parametrizado")
    void deveCriarRequestComConstrutorParametrizado() {
        // Given
        String nome = "Empréstimo Pessoal";
        BigDecimal taxaJurosAnual = new BigDecimal("18.00");
        Integer prazoMaximoMeses = 60;

        // When
        ProdutoEmprestimoRequest request = new ProdutoEmprestimoRequest(nome, taxaJurosAnual, prazoMaximoMeses);

        // Then
        assertNotNull(request);
        assertEquals(nome, request.getNome());
        assertEquals(taxaJurosAnual, request.getTaxaJurosAnual());
        assertEquals(prazoMaximoMeses, request.getPrazoMaximoMeses());
    }

    @Test
    @DisplayName("Deve permitir definir e obter nome")
    void devePermitirDefinirEObterNome() {
        // Given
        ProdutoEmprestimoRequest request = new ProdutoEmprestimoRequest();
        String nome = "Empréstimo Consignado";

        // When
        request.setNome(nome);

        // Then
        assertEquals(nome, request.getNome());
    }

    @Test
    @DisplayName("Deve permitir definir e obter taxa de juros anual")
    void devePermitirDefinirEObterTaxaJurosAnual() {
        // Given
        ProdutoEmprestimoRequest request = new ProdutoEmprestimoRequest();
        BigDecimal taxaJurosAnual = new BigDecimal("15.75");

        // When
        request.setTaxaJurosAnual(taxaJurosAnual);

        // Then
        assertEquals(taxaJurosAnual, request.getTaxaJurosAnual());
    }

    @Test
    @DisplayName("Deve permitir definir e obter prazo máximo em meses")
    void devePermitirDefinirEObterPrazoMaximoMeses() {
        // Given
        ProdutoEmprestimoRequest request = new ProdutoEmprestimoRequest();
        Integer prazoMaximoMeses = 72;

        // When
        request.setPrazoMaximoMeses(prazoMaximoMeses);

        // Then
        assertEquals(prazoMaximoMeses, request.getPrazoMaximoMeses());
    }
}
