package com.caixa.service;

import com.caixa.dto.ProdutoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoResponse;
import com.caixa.model.ProdutoEmprestimo;
import com.caixa.repository.ProdutoEmprestimoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProdutoEmprestimoService {

    @Inject
    ProdutoEmprestimoRepository produtoEmprestimoRepository;

    @Inject
    CalculoJurosService calculoJurosService;

    /**
     * Lista todos os produtos de empréstimo
     */
    public List<ProdutoEmprestimo> listarTodos() {
        return produtoEmprestimoRepository.listAll();
    }

    /**
     * Busca um produto por ID
     */
    public ProdutoEmprestimo buscarPorId(Long id) {
        ProdutoEmprestimo produto = produtoEmprestimoRepository.findById(id);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado com ID: " + id);
        }
        return produto;
    }

    /**
     * Cria um novo produto de empréstimo
     */
    @Transactional
    public ProdutoEmprestimo criar(ProdutoEmprestimoRequest request) {
        ProdutoEmprestimo produto = new ProdutoEmprestimo(
            request.getNome(),
            request.getTaxaJurosAnual(),
            request.getPrazoMaximoMeses()
        );
        produtoEmprestimoRepository.persist(produto);
        return produto;
    }

    /**
     * Atualiza um produto existente
     */
    @Transactional
    public ProdutoEmprestimo atualizar(Long id, ProdutoEmprestimoRequest request) {
        ProdutoEmprestimo produto = buscarPorId(id);
        produto.nome = request.getNome();
        produto.taxaJurosAnual = request.getTaxaJurosAnual();
        produto.prazoMaximoMeses = request.getPrazoMaximoMeses();
        produtoEmprestimoRepository.persist(produto);
        return produto;
    }

    /**
     * Remove um produto
     */
    @Transactional
    public void remover(Long id) {
        ProdutoEmprestimo produto = buscarPorId(id);
        produtoEmprestimoRepository.delete(produto);
    }

    /**
     * Simula um empréstimo baseado no produto e parâmetros fornecidos
     */
    public SimulacaoEmprestimoResponse simularEmprestimo(SimulacaoEmprestimoRequest request) {
        ProdutoEmprestimo produto = buscarPorId(request.getIdProduto());
        return calculoJurosService.simularEmprestimo(produto, request.getValorSolicitado(), request.getPrazoMeses());
    }
}
