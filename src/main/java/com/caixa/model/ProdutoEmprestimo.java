package com.caixa.model;

import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "produto_emprestimo")
public class ProdutoEmprestimo extends PanacheEntity {

    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    public String nome;

    @NotNull(message = "Taxa de juros anual é obrigatória")
    @DecimalMin(value = "0.0", message = "Taxa de juros anual deve ser maior ou igual a 0")
    @DecimalMax(value = "100.0", message = "Taxa de juros anual deve ser menor ou igual a 100")
    @Column(name = "taxa_juros_anual", nullable = false, precision = 5, scale = 2)
    public BigDecimal taxaJurosAnual;

    @NotNull(message = "Prazo máximo em meses é obrigatório")
    @Positive(message = "Prazo máximo em meses deve ser positivo")
    @Column(name = "prazo_maximo_meses", nullable = false)
    public Integer prazoMaximoMeses;

    public ProdutoEmprestimo() {
    }

    public ProdutoEmprestimo(String nome, BigDecimal taxaJurosAnual, Integer prazoMaximoMeses) {
        this.nome = nome;
        this.taxaJurosAnual = taxaJurosAnual;
        this.prazoMaximoMeses = prazoMaximoMeses;
    }

    @Override
    public String toString() {
        return "ProdutoEmprestimo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", taxaJurosAnual=" + taxaJurosAnual +
                ", prazoMaximoMeses=" + prazoMaximoMeses +
                '}';
    }
}
