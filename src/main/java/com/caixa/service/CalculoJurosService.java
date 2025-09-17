package com.caixa.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.caixa.dto.SimulacaoEmprestimoResponse;
import com.caixa.model.ProdutoEmprestimo;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalculoJurosService {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * Calcula a taxa de juros efetiva mensal baseada na taxa anual
     * Fórmula: i = (1 + i_anual)^(1/12) - 1
     */
    public BigDecimal calcularTaxaJurosEfetivaMensal(BigDecimal taxaJurosAnual) {
        if (taxaJurosAnual == null || taxaJurosAnual.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de juros anual deve ser não negativa");
        }

        // Converte a taxa anual de percentual para decimal
        BigDecimal taxaAnualDecimal = taxaJurosAnual.divide(BigDecimal.valueOf(100), 10, ROUNDING_MODE);
        
        // Calcula (1 + i_anual)
        BigDecimal umMaisTaxaAnual = BigDecimal.ONE.add(taxaAnualDecimal);
        
        // Usa Math.pow para calcular a raiz 12ª de forma mais precisa
        double umMaisTaxaAnualDouble = umMaisTaxaAnual.doubleValue();
        double raizDozeDouble = Math.pow(umMaisTaxaAnualDouble, 1.0/12.0);
        BigDecimal raizDoze = BigDecimal.valueOf(raizDozeDouble);
        
        // Calcula (1 + i_anual)^(1/12) - 1
        BigDecimal taxaMensalDecimal = raizDoze.subtract(BigDecimal.ONE);
        
        // Converte de volta para percentual
        return taxaMensalDecimal.multiply(BigDecimal.valueOf(100)).setScale(SCALE, ROUNDING_MODE);
    }


    /**
     * Calcula x^n usando multiplicação iterativa
     */
    private BigDecimal potencia(BigDecimal base, int expoente) {
        BigDecimal resultado = base;
        for (int i = 1; i < expoente; i++) {
            resultado = resultado.multiply(base);
        }
        
        return resultado;
    }

    /**
     * Calcula o valor da parcela usando o sistema Price (parcelas fixas)
     * Fórmula: PMT = PV * [i * (1 + i)^n] / [(1 + i)^n - 1]
     */
    public BigDecimal calcularValorParcela(BigDecimal valorSolicitado, BigDecimal taxaJurosMensal, int prazoMeses) {
        if (valorSolicitado == null || valorSolicitado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor solicitado deve ser positivo");
        }
        
        if (taxaJurosMensal == null || taxaJurosMensal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de juros mensal deve ser não negativa");
        }
        
        if (prazoMeses <= 0) {
            throw new IllegalArgumentException("Prazo em meses deve ser positivo");
        }

        // Converte a taxa mensal de percentual para decimal
        BigDecimal taxaMensalDecimal = taxaJurosMensal.divide(BigDecimal.valueOf(100), 10, ROUNDING_MODE);
        
        if (taxaMensalDecimal.compareTo(BigDecimal.ZERO) == 0) {
            // Se taxa é zero, parcela é simplesmente valor / prazo
            return valorSolicitado.divide(BigDecimal.valueOf(prazoMeses), SCALE, ROUNDING_MODE);
        }
        
        // Calcula (1 + i)^n
        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaMensalDecimal);
        BigDecimal umMaisTaxaElevadoN = potencia(umMaisTaxa, prazoMeses);
        
        // Calcula o numerador: i * (1 + i)^n
        BigDecimal numerador = taxaMensalDecimal.multiply(umMaisTaxaElevadoN);
        
        // Calcula o denominador: (1 + i)^n - 1
        BigDecimal denominador = umMaisTaxaElevadoN.subtract(BigDecimal.ONE);
        
        // Calcula o fator: [i * (1 + i)^n] / [(1 + i)^n - 1]
        BigDecimal fator = numerador.divide(denominador, 10, ROUNDING_MODE);
        
        // Calcula PMT = PV * fator
        return valorSolicitado.multiply(fator).setScale(SCALE, ROUNDING_MODE);
    }

    /**
     * Gera a simulação completa do empréstimo com detalhamento mês a mês
     */
    public SimulacaoEmprestimoResponse simularEmprestimo(ProdutoEmprestimo produto, BigDecimal valorSolicitado, int prazoMeses) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        
        if (prazoMeses > produto.prazoMaximoMeses) {
            throw new IllegalArgumentException("Prazo solicitado excede o prazo máximo do produto");
        }

        // Calcula taxa efetiva mensal
        BigDecimal taxaJurosEfetivaMensal = calcularTaxaJurosEfetivaMensal(produto.taxaJurosAnual);
        
        // Calcula valor da parcela
        BigDecimal valorParcela = calcularValorParcela(valorSolicitado, taxaJurosEfetivaMensal, prazoMeses);
        
        // Calcula valor total com juros
        BigDecimal valorTotalComJuros = valorParcela.multiply(BigDecimal.valueOf(prazoMeses));
        
        // Gera detalhamento mês a mês
        List<SimulacaoEmprestimoResponse.DetalhamentoMensal> memoriaCalculo = 
            gerarDetalhamentoMensal(valorSolicitado, valorParcela, taxaJurosEfetivaMensal, prazoMeses);

        return new SimulacaoEmprestimoResponse(
            produto,
            produto.taxaJurosAnual,
            taxaJurosEfetivaMensal,
            valorSolicitado,
            valorTotalComJuros,
            memoriaCalculo
        );
    }

    /**
     * Gera o detalhamento mês a mês do empréstimo
     */
    private List<SimulacaoEmprestimoResponse.DetalhamentoMensal> gerarDetalhamentoMensal(
            BigDecimal valorSolicitado, BigDecimal valorParcela, BigDecimal taxaJurosMensal, int prazoMeses) {
        
        List<SimulacaoEmprestimoResponse.DetalhamentoMensal> detalhamento = new ArrayList<>();
        BigDecimal taxaMensalDecimal = taxaJurosMensal.divide(BigDecimal.valueOf(100), 10, ROUNDING_MODE);
        BigDecimal saldoDevedor = valorSolicitado;
        
        for (int mes = 1; mes <= prazoMeses; mes++) {
            // Calcula juros do mês
            BigDecimal juros = saldoDevedor.multiply(taxaMensalDecimal).setScale(SCALE, ROUNDING_MODE);
            
            // Calcula amortização
            BigDecimal amortizacao = valorParcela.subtract(juros);
            
            // Atualiza saldo devedor
            saldoDevedor = saldoDevedor.subtract(amortizacao);
            
            // Garante que o saldo não fique negativo
            if (saldoDevedor.compareTo(BigDecimal.ZERO) < 0) {
                saldoDevedor = BigDecimal.ZERO;
            }
            
            detalhamento.add(new SimulacaoEmprestimoResponse.DetalhamentoMensal(
                mes,
                valorParcela,
                juros,
                amortizacao,
                saldoDevedor
            ));
        }
        
        return detalhamento;
    }
}
