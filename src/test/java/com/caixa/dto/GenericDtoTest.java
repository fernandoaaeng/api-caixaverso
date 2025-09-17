package com.caixa.dto;

import com.caixa.model.ProdutoEmprestimo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericDtoTest {

    @Test
    void testProdutoEmprestimoRequestGettersSetters() {
        ProdutoEmprestimoRequest request = new ProdutoEmprestimoRequest();
        
        // Test setters
        request.setNome("Teste");
        request.setTaxaJurosAnual(new BigDecimal("12.00"));
        request.setPrazoMaximoMeses(24);
        
        // Test getters
        assertEquals("Teste", request.getNome());
        assertEquals(new BigDecimal("12.00"), request.getTaxaJurosAnual());
        assertEquals(24, request.getPrazoMaximoMeses());
    }

    @Test
    void testSimulacaoEmprestimoRequestGettersSetters() {
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();
        
        // Test setters
        request.setIdProduto(1L);
        request.setValorSolicitado(new BigDecimal("10000.00"));
        request.setPrazoMeses(12);
        
        // Test getters
        assertEquals(1L, request.getIdProduto());
        assertEquals(new BigDecimal("10000.00"), request.getValorSolicitado());
        assertEquals(12, request.getPrazoMeses());
    }

    @Test
    void testSimulacaoEmprestimoResponseGettersSetters() {
        SimulacaoEmprestimoResponse response = new SimulacaoEmprestimoResponse();
        
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 1L;
        produto.nome = "Teste";
        
        // Test setters
        response.setProduto(produto);
        response.setValorSolicitado(new BigDecimal("10000.00"));
        response.setTaxaJurosAnual(new BigDecimal("12.00"));
        response.setTaxaJurosEfetivaMensal(new BigDecimal("0.95"));
        response.setValorTotalComJuros(new BigDecimal("10661.88"));
        
        List<SimulacaoEmprestimoResponse.DetalhamentoMensal> detalhamento = new ArrayList<>();
        detalhamento.add(new SimulacaoEmprestimoResponse.DetalhamentoMensal(
            1, new BigDecimal("888.49"), new BigDecimal("95.00"), new BigDecimal("793.49"), new BigDecimal("9206.51")
        ));
        response.setMemoriaCalculo(detalhamento);
        
        // Test getters
        assertEquals(produto, response.getProduto());
        assertEquals(new BigDecimal("10000.00"), response.getValorSolicitado());
        assertEquals(new BigDecimal("12.00"), response.getTaxaJurosAnual());
        assertEquals(new BigDecimal("0.95"), response.getTaxaJurosEfetivaMensal());
        assertEquals(new BigDecimal("10661.88"), response.getValorTotalComJuros());
        assertNotNull(response.getMemoriaCalculo());
        assertEquals(1, response.getMemoriaCalculo().size());
    }

    @Test
    void testDetalhamentoMensalConstructor() {
        SimulacaoEmprestimoResponse.DetalhamentoMensal detalhe = 
            new SimulacaoEmprestimoResponse.DetalhamentoMensal(
                1, new BigDecimal("888.49"), new BigDecimal("95.00"), 
                new BigDecimal("793.49"), new BigDecimal("9206.51")
            );
        
        assertEquals(1, detalhe.getMes());
        assertEquals(new BigDecimal("888.49"), detalhe.getValorParcela());
        assertEquals(new BigDecimal("95.00"), detalhe.getJuros());
        assertEquals(new BigDecimal("793.49"), detalhe.getAmortizacao());
        assertEquals(new BigDecimal("9206.51"), detalhe.getSaldoDevedor());
    }

    @Test
    void testDetalhamentoMensalGettersSetters() {
        SimulacaoEmprestimoResponse.DetalhamentoMensal detalhe = 
            new SimulacaoEmprestimoResponse.DetalhamentoMensal(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        
        detalhe.setMes(1);
        detalhe.setValorParcela(new BigDecimal("888.49"));
        detalhe.setJuros(new BigDecimal("95.00"));
        detalhe.setAmortizacao(new BigDecimal("793.49"));
        detalhe.setSaldoDevedor(new BigDecimal("9206.51"));
        
        assertEquals(1, detalhe.getMes());
        assertEquals(new BigDecimal("888.49"), detalhe.getValorParcela());
        assertEquals(new BigDecimal("95.00"), detalhe.getJuros());
        assertEquals(new BigDecimal("793.49"), detalhe.getAmortizacao());
        assertEquals(new BigDecimal("9206.51"), detalhe.getSaldoDevedor());
    }

    @Test
    void testToStringMethods() {
        ProdutoEmprestimoRequest request = new ProdutoEmprestimoRequest();
        request.setNome("Teste");
        request.setTaxaJurosAnual(new BigDecimal("12.00"));
        request.setPrazoMaximoMeses(24);
        
        String toString = request.toString();
        assertNotNull(toString);
        // Verificar se o toString não está vazio
        assertFalse(toString.isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        ProdutoEmprestimoRequest request1 = new ProdutoEmprestimoRequest();
        request1.setNome("Teste");
        request1.setTaxaJurosAnual(new BigDecimal("12.00"));
        request1.setPrazoMaximoMeses(24);
        
        ProdutoEmprestimoRequest request2 = new ProdutoEmprestimoRequest();
        request2.setNome("Teste");
        request2.setTaxaJurosAnual(new BigDecimal("12.00"));
        request2.setPrazoMaximoMeses(24);
        
        // Como não implementamos equals/hashCode, vamos testar se são objetos diferentes
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }
}
