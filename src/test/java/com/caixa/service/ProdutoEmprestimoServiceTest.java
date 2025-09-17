package com.caixa.service;

import com.caixa.dto.ProdutoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoResponse;
import com.caixa.model.ProdutoEmprestimo;
import com.caixa.repository.ProdutoEmprestimoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoEmprestimoServiceTest {

    @Mock
    private ProdutoEmprestimoRepository produtoEmprestimoRepository;

    @Mock
    private CalculoJurosService calculoJurosService;

    @InjectMocks
    private ProdutoEmprestimoService produtoEmprestimoService;

    private ProdutoEmprestimo produto;
    private ProdutoEmprestimoRequest produtoRequest;
    private SimulacaoEmprestimoRequest simulacaoRequest;
    private SimulacaoEmprestimoResponse simulacaoResponse;

    @BeforeEach
    void setUp() {
        produto = new ProdutoEmprestimo();
        produto.id = 1L;
        produto.nome = "Empréstimo Teste";
        produto.taxaJurosAnual = new BigDecimal("15.00");
        produto.prazoMaximoMeses = 36;

        produtoRequest = new ProdutoEmprestimoRequest();
        produtoRequest.setNome("Empréstimo Teste");
        produtoRequest.setTaxaJurosAnual(new BigDecimal("15.00"));
        produtoRequest.setPrazoMaximoMeses(36);

        simulacaoRequest = new SimulacaoEmprestimoRequest();
        simulacaoRequest.setIdProduto(1L);
        simulacaoRequest.setValorSolicitado(new BigDecimal("8000.00"));
        simulacaoRequest.setPrazoMeses(18);

        simulacaoResponse = new SimulacaoEmprestimoResponse();
        simulacaoResponse.produto = produto;
        simulacaoResponse.valorSolicitado = simulacaoRequest.getValorSolicitado();
    }

    @Test
    void testCriarProduto() {
        doAnswer(invocation -> {
            ProdutoEmprestimo p = invocation.getArgument(0);
            p.id = 1L;
            return null;
        }).when(produtoEmprestimoRepository).persist(any(ProdutoEmprestimo.class));

        ProdutoEmprestimo resultado = produtoEmprestimoService.criar(produtoRequest);

        assertNotNull(resultado);
        assertEquals(produtoRequest.getNome(), resultado.nome);
        assertEquals(produtoRequest.getTaxaJurosAnual(), resultado.taxaJurosAnual);
        assertEquals(produtoRequest.getPrazoMaximoMeses(), resultado.prazoMaximoMeses);
        verify(produtoEmprestimoRepository, times(1)).persist(any(ProdutoEmprestimo.class));
    }

    @Test
    void testListarTodos() {
        List<ProdutoEmprestimo> produtos = Arrays.asList(produto);
        when(produtoEmprestimoRepository.listAll()).thenReturn(produtos);

        List<ProdutoEmprestimo> resultado = produtoEmprestimoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtoEmprestimoRepository, times(1)).listAll();
    }

    @Test
    void testBuscarPorId() {
        when(produtoEmprestimoRepository.findById(1L)).thenReturn(produto);

        ProdutoEmprestimo resultado = produtoEmprestimoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id);
        verify(produtoEmprestimoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorIdInexistente() {
        when(produtoEmprestimoRepository.findById(999L)).thenReturn(null);

        assertThrows(Exception.class, () -> produtoEmprestimoService.buscarPorId(999L));
        verify(produtoEmprestimoRepository, times(1)).findById(999L);
    }

    @Test
    void testAtualizarProduto() {
        when(produtoEmprestimoRepository.findById(1L)).thenReturn(produto);
        doNothing().when(produtoEmprestimoRepository).persist(any(ProdutoEmprestimo.class));

        ProdutoEmprestimo resultado = produtoEmprestimoService.atualizar(1L, produtoRequest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id);
        verify(produtoEmprestimoRepository, times(1)).findById(1L);
        verify(produtoEmprestimoRepository, times(1)).persist(any(ProdutoEmprestimo.class));
    }

    @Test
    void testAtualizarProdutoInexistente() {
        when(produtoEmprestimoRepository.findById(999L)).thenReturn(null);

        assertThrows(Exception.class, () -> produtoEmprestimoService.atualizar(999L, produtoRequest));
        verify(produtoEmprestimoRepository, times(1)).findById(999L);
        verify(produtoEmprestimoRepository, never()).persist(any(ProdutoEmprestimo.class));
    }

    @Test
    void testRemoverProduto() {
        when(produtoEmprestimoRepository.findById(1L)).thenReturn(produto);
        doNothing().when(produtoEmprestimoRepository).delete(any(ProdutoEmprestimo.class));

        produtoEmprestimoService.remover(1L);

        verify(produtoEmprestimoRepository, times(1)).findById(1L);
        verify(produtoEmprestimoRepository, times(1)).delete(any(ProdutoEmprestimo.class));
    }

    @Test
    void testRemoverProdutoInexistente() {
        when(produtoEmprestimoRepository.findById(999L)).thenReturn(null);

        assertThrows(Exception.class, () -> produtoEmprestimoService.remover(999L));
        verify(produtoEmprestimoRepository, times(1)).findById(999L);
        verify(produtoEmprestimoRepository, never()).deleteById(anyLong());
    }

    @Test
    void testSimularEmprestimo() {
        when(produtoEmprestimoRepository.findById(1L)).thenReturn(produto);
        when(calculoJurosService.simularEmprestimo(any(ProdutoEmprestimo.class), any(BigDecimal.class), anyInt()))
            .thenReturn(simulacaoResponse);

        SimulacaoEmprestimoResponse resultado = produtoEmprestimoService.simularEmprestimo(simulacaoRequest);

        assertNotNull(resultado);
        assertEquals(produto, resultado.produto);
        verify(produtoEmprestimoRepository, times(1)).findById(1L);
        verify(calculoJurosService, times(1)).simularEmprestimo(any(ProdutoEmprestimo.class), any(BigDecimal.class), anyInt());
    }

    @Test
    void testSimularEmprestimoComProdutoInexistente() {
        simulacaoRequest.setIdProduto(999L);
        when(produtoEmprestimoRepository.findById(999L)).thenReturn(null);

        assertThrows(Exception.class, () -> produtoEmprestimoService.simularEmprestimo(simulacaoRequest));
        verify(produtoEmprestimoRepository, times(1)).findById(999L);
        verify(calculoJurosService, never()).simularEmprestimo(any(), any(), anyInt());
    }
}
