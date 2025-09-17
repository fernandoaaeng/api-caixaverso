package com.caixa.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GenericEntityTest {

    @Test
    void testProdutoEmprestimoGettersSetters() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        
        // Test setters
        produto.id = 1L;
        produto.nome = "Teste";
        produto.taxaJurosAnual = new BigDecimal("12.00");
        produto.prazoMaximoMeses = 24;
        
        // Test getters
        assertEquals(1L, produto.id);
        assertEquals("Teste", produto.nome);
        assertEquals(new BigDecimal("12.00"), produto.taxaJurosAnual);
        assertEquals(24, produto.prazoMaximoMeses);
    }

    @Test
    void testProdutoEmprestimoConstructors() {
        // Test default constructor
        ProdutoEmprestimo produto1 = new ProdutoEmprestimo();
        assertNotNull(produto1);
        
        // Test constructor with parameters
        ProdutoEmprestimo produto2 = new ProdutoEmprestimo("Teste", new BigDecimal("12.00"), 24);
        assertEquals("Teste", produto2.nome);
        assertEquals(new BigDecimal("12.00"), produto2.taxaJurosAnual);
        assertEquals(24, produto2.prazoMaximoMeses);
    }

    @Test
    void testProdutoEmprestimoToString() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 1L;
        produto.nome = "Teste";
        produto.taxaJurosAnual = new BigDecimal("12.00");
        produto.prazoMaximoMeses = 24;
        
        String toString = produto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Teste"));
        assertTrue(toString.contains("12.00"));
        assertTrue(toString.contains("24"));
    }

    @Test
    void testProdutoEmprestimoEqualsAndHashCode() {
        ProdutoEmprestimo produto1 = new ProdutoEmprestimo();
        produto1.id = 1L;
        produto1.nome = "Teste";
        produto1.taxaJurosAnual = new BigDecimal("12.00");
        produto1.prazoMaximoMeses = 24;
        
        ProdutoEmprestimo produto2 = new ProdutoEmprestimo();
        produto2.id = 1L;
        produto2.nome = "Teste";
        produto2.taxaJurosAnual = new BigDecimal("12.00");
        produto2.prazoMaximoMeses = 24;
        
        // Como não implementamos equals/hashCode, vamos testar se são objetos diferentes
        assertNotEquals(produto1, produto2);
        assertNotEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    void testProdutoEmprestimoNotEquals() {
        ProdutoEmprestimo produto1 = new ProdutoEmprestimo();
        produto1.id = 1L;
        produto1.nome = "Teste1";
        
        ProdutoEmprestimo produto2 = new ProdutoEmprestimo();
        produto2.id = 2L;
        produto2.nome = "Teste2";
        
        assertNotEquals(produto1, produto2);
        assertNotEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    void testProdutoEmprestimoWithNullValues() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 1L;
        produto.nome = null;
        produto.taxaJurosAnual = null;
        produto.prazoMaximoMeses = 0;
        
        assertNull(produto.nome);
        assertNull(produto.taxaJurosAnual);
        assertEquals(0, produto.prazoMaximoMeses);
    }

    @Test
    void testProdutoEmprestimoEdgeCases() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        
        // Test with zero values
        produto.taxaJurosAnual = BigDecimal.ZERO;
        produto.prazoMaximoMeses = 0;
        assertEquals(BigDecimal.ZERO, produto.taxaJurosAnual);
        assertEquals(0, produto.prazoMaximoMeses);
        
        // Test with negative values
        produto.taxaJurosAnual = new BigDecimal("-5.00");
        produto.prazoMaximoMeses = -1;
        assertEquals(new BigDecimal("-5.00"), produto.taxaJurosAnual);
        assertEquals(-1, produto.prazoMaximoMeses);
    }
}
