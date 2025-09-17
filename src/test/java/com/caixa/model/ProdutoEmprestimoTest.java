package com.caixa.model;

import com.caixa.model.ProdutoEmprestimo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoEmprestimoTest {

    @Test
    @DisplayName("Deve criar produto com construtor vazio")
    void deveCriarProdutoComConstrutorVazio() {
        // When
        ProdutoEmprestimo produto = new ProdutoEmprestimo();

        // Then
        assertNotNull(produto);
        assertNull(produto.nome);
        assertNull(produto.taxaJurosAnual);
        assertNull(produto.prazoMaximoMeses);
    }

    @Test
    @DisplayName("Deve criar produto com construtor parametrizado")
    void deveCriarProdutoComConstrutorParametrizado() {
        // Given
        String nome = "Empréstimo Pessoal";
        BigDecimal taxaJurosAnual = new BigDecimal("18.00");
        Integer prazoMaximoMeses = 60;

        // When
        ProdutoEmprestimo produto = new ProdutoEmprestimo(nome, taxaJurosAnual, prazoMaximoMeses);

        // Then
        assertNotNull(produto);
        assertEquals(nome, produto.nome);
        assertEquals(taxaJurosAnual, produto.taxaJurosAnual);
        assertEquals(prazoMaximoMeses, produto.prazoMaximoMeses);
    }

    @Test
    @DisplayName("Deve retornar toString correto")
    void deveRetornarToStringCorreto() {
        // Given
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 1L;
        produto.nome = "Empréstimo Pessoal";
        produto.taxaJurosAnual = new BigDecimal("18.00");
        produto.prazoMaximoMeses = 60;

        // When
        String toString = produto.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("ProdutoEmprestimo"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome='Empréstimo Pessoal'"));
        assertTrue(toString.contains("taxaJurosAnual=18.00"));
        assertTrue(toString.contains("prazoMaximoMeses=60"));
    }

    @Test
    @DisplayName("Deve permitir definir e obter propriedades")
    void devePermitirDefinirEObterPropriedades() {
        // Given
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        String nome = "Empréstimo Consignado";
        BigDecimal taxaJurosAnual = new BigDecimal("12.50");
        Integer prazoMaximoMeses = 84;

        // When
        produto.nome = nome;
        produto.taxaJurosAnual = taxaJurosAnual;
        produto.prazoMaximoMeses = prazoMaximoMeses;

        // Then
        assertEquals(nome, produto.nome);
        assertEquals(taxaJurosAnual, produto.taxaJurosAnual);
        assertEquals(prazoMaximoMeses, produto.prazoMaximoMeses);
    }
}
