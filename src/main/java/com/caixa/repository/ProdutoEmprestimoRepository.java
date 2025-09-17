package com.caixa.repository;

import com.caixa.model.ProdutoEmprestimo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoEmprestimoRepository implements PanacheRepository<ProdutoEmprestimo> {
    
    // Métodos customizados podem ser adicionados aqui se necessário
    // Por enquanto, usamos os métodos herdados do PanacheRepository:
    // - findById(Long id)
    // - findAll()
    // - persist(Entity entity)
    // - delete(Entity entity)
    // - etc.
}
