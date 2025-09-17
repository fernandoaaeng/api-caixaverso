package com.caixa.controller;

import com.caixa.dto.ProdutoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoResponse;
import com.caixa.model.ProdutoEmprestimo;
import com.caixa.service.ProdutoEmprestimoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoEmprestimoControllerTest {

    @Mock
    private ProdutoEmprestimoService produtoEmprestimoService;

    @InjectMocks
    private ProdutoEmprestimoController produtoEmprestimoController;

    private ProdutoEmprestimo produto;
    private ProdutoEmprestimoRequest produtoRequest;
    private SimulacaoEmprestimoRequest simulacaoRequest;
    private SimulacaoEmprestimoResponse simulacaoResponse;

    @BeforeEach
    void setUp() {
        produto = new ProdutoEmprestimo();
        produto.id = 1L;
        produto.nome = "Empréstimo Teste";
        produto.taxaJurosAnual = new BigDecimal("12.00");
        produto.prazoMaximoMeses = 24;

        produtoRequest = new ProdutoEmprestimoRequest();
        produtoRequest.setNome("Empréstimo Teste");
        produtoRequest.setTaxaJurosAnual(new BigDecimal("12.00"));
        produtoRequest.setPrazoMaximoMeses(24);

        simulacaoRequest = new SimulacaoEmprestimoRequest();
        simulacaoRequest.setIdProduto(1L);
        simulacaoRequest.setValorSolicitado(new BigDecimal("10000.00"));
        simulacaoRequest.setPrazoMeses(12);

        simulacaoResponse = new SimulacaoEmprestimoResponse();
        simulacaoResponse.produto = produto;
        simulacaoResponse.valorSolicitado = simulacaoRequest.getValorSolicitado();
    }

    @Test
    void testListarTodos() {
        List<ProdutoEmprestimo> produtos = Arrays.asList(produto);
        when(produtoEmprestimoService.listarTodos()).thenReturn(produtos);

        var resultado = produtoEmprestimoController.listarTodos();

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatus());
        verify(produtoEmprestimoService, times(1)).listarTodos();
    }

    @Test
    void testCriar() {
        when(produtoEmprestimoService.criar(any(ProdutoEmprestimoRequest.class))).thenReturn(produto);

        var resultado = produtoEmprestimoController.criar(produtoRequest);

        assertNotNull(resultado);
        assertEquals(201, resultado.getStatus());
        verify(produtoEmprestimoService, times(1)).criar(produtoRequest);
    }

    @Test
    void testBuscarPorId() {
        when(produtoEmprestimoService.buscarPorId(1L)).thenReturn(produto);

        var resultado = produtoEmprestimoController.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatus());
        verify(produtoEmprestimoService, times(1)).buscarPorId(1L);
    }

    @Test
    void testAtualizar() {
        when(produtoEmprestimoService.atualizar(eq(1L), any(ProdutoEmprestimoRequest.class))).thenReturn(produto);

        var resultado = produtoEmprestimoController.atualizar(1L, produtoRequest);

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatus());
        verify(produtoEmprestimoService, times(1)).atualizar(1L, produtoRequest);
    }

    @Test
    void testRemover() {
        doNothing().when(produtoEmprestimoService).remover(1L);

        produtoEmprestimoController.remover(1L);

        verify(produtoEmprestimoService, times(1)).remover(1L);
    }

    @Test
    void testSimularEmprestimo() {
        when(produtoEmprestimoService.simularEmprestimo(any(SimulacaoEmprestimoRequest.class))).thenReturn(simulacaoResponse);

        var resultado = produtoEmprestimoController.simularEmprestimo(simulacaoRequest);

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatus());
        verify(produtoEmprestimoService, times(1)).simularEmprestimo(simulacaoRequest);
    }
}
