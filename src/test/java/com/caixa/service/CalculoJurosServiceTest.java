package com.caixa.service;

import com.caixa.dto.SimulacaoEmprestimoResponse;
import com.caixa.model.ProdutoEmprestimo;
import com.caixa.service.CalculoJurosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculoJurosServiceTest {

    private CalculoJurosService calculoJurosService;

    private ProdutoEmprestimo produto;

    @BeforeEach
    void setUp() {
        calculoJurosService = new CalculoJurosService();
        produto = new ProdutoEmprestimo();
        produto.nome = "Empréstimo Pessoal";
        produto.taxaJurosAnual = new BigDecimal("12.00");
        produto.prazoMaximoMeses = 24;
    }

    @Test
    @DisplayName("Deve calcular taxa de juros efetiva mensal corretamente")
    void deveCalcularTaxaJurosEfetivaMensal() {
        // Given
        BigDecimal taxaAnual = new BigDecimal("12.00");

        // When
        BigDecimal taxaMensal = calculoJurosService.calcularTaxaJurosEfetivaMensal(taxaAnual);

        // Then
        assertNotNull(taxaMensal);
        assertTrue(taxaMensal.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(taxaMensal.compareTo(taxaAnual) < 0); // Taxa mensal deve ser menor que anual
        assertEquals(2, taxaMensal.scale());
    }

    @Test
    @DisplayName("Deve calcular taxa de juros efetiva mensal para taxa zero")
    void deveCalcularTaxaJurosEfetivaMensalParaTaxaZero() {
        // Given
        BigDecimal taxaAnual = BigDecimal.ZERO;

        // When
        BigDecimal taxaMensal = calculoJurosService.calcularTaxaJurosEfetivaMensal(taxaAnual);

        // Then
        assertNotNull(taxaMensal);
        assertEquals(0, taxaMensal.compareTo(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Deve lançar exceção para taxa de juros negativa")
    void deveLancarExcecaoParaTaxaJurosNegativa() {
        // Given
        BigDecimal taxaAnual = new BigDecimal("-5.00");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            calculoJurosService.calcularTaxaJurosEfetivaMensal(taxaAnual);
        });
    }

    @Test
    @DisplayName("Deve calcular valor da parcela corretamente")
    void deveCalcularValorParcela() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        BigDecimal taxaJurosMensal = new BigDecimal("1.00"); // Aproximadamente 12% ao ano
        int prazoMeses = 12;

        // When
        BigDecimal valorParcela = calculoJurosService.calcularValorParcela(valorSolicitado, taxaJurosMensal, prazoMeses);

        // Then
        assertNotNull(valorParcela);
        assertTrue(valorParcela.compareTo(BigDecimal.ZERO) > 0);
        assertEquals(2, valorParcela.scale());
    }

    @Test
    @DisplayName("Deve calcular valor da parcela para taxa zero")
    void deveCalcularValorParcelaParaTaxaZero() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        BigDecimal taxaJurosMensal = BigDecimal.ZERO;
        int prazoMeses = 24;

        // When
        BigDecimal valorParcela = calculoJurosService.calcularValorParcela(valorSolicitado, taxaJurosMensal, prazoMeses);

        // Then
        assertNotNull(valorParcela);
        assertEquals(new BigDecimal("208.33"), valorParcela); // 5000 / 24
    }

    @Test
    @DisplayName("Deve lançar exceção para valor solicitado inválido")
    void deveLancarExcecaoParaValorSolicitadoInvalido() {
        // Given
        BigDecimal valorSolicitado = BigDecimal.ZERO;
        BigDecimal taxaJurosMensal = new BigDecimal("1.39");
        int prazoMeses = 24;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            calculoJurosService.calcularValorParcela(valorSolicitado, taxaJurosMensal, prazoMeses);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção para prazo inválido")
    void deveLancarExcecaoParaPrazoInvalido() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        BigDecimal taxaJurosMensal = new BigDecimal("1.39");
        int prazoMeses = 0;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            calculoJurosService.calcularValorParcela(valorSolicitado, taxaJurosMensal, prazoMeses);
        });
    }

    @Test
    @DisplayName("Deve simular empréstimo completo corretamente")
    void deveSimularEmprestimoCompleto() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        int prazoMeses = 24;

        // When
        SimulacaoEmprestimoResponse simulacao = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);

        // Then
        assertNotNull(simulacao);
        assertEquals(produto, simulacao.getProduto());
        assertEquals(valorSolicitado, simulacao.getValorSolicitado());
        assertNotNull(simulacao.getTaxaJurosAnual());
        assertNotNull(simulacao.getTaxaJurosEfetivaMensal());
        assertNotNull(simulacao.getValorTotalComJuros());
        assertNotNull(simulacao.getMemoriaCalculo());
        assertEquals(prazoMeses, simulacao.getMemoriaCalculo().size());
    }

    @Test
    @DisplayName("Deve gerar detalhamento mensal correto")
    void deveGerarDetalhamentoMensalCorreto() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        int prazoMeses = 3;

        // When
        SimulacaoEmprestimoResponse simulacao = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);

        // Then
        List<SimulacaoEmprestimoResponse.DetalhamentoMensal> detalhamento = simulacao.getMemoriaCalculo();
        assertEquals(prazoMeses, detalhamento.size());

        // Verifica se o primeiro mês tem o saldo devedor inicial
        SimulacaoEmprestimoResponse.DetalhamentoMensal primeiroMes = detalhamento.get(0);
        assertEquals(1, primeiroMes.getMes());
        assertTrue(primeiroMes.getJuros().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(primeiroMes.getAmortizacao().compareTo(BigDecimal.ZERO) > 0);

        // Verifica se o último mês tem saldo devedor zero
        SimulacaoEmprestimoResponse.DetalhamentoMensal ultimoMes = detalhamento.get(prazoMeses - 1);
        assertEquals(prazoMeses, ultimoMes.getMes());
        assertTrue(ultimoMes.getSaldoDevedor().compareTo(new BigDecimal("0.01")) <= 0);
    }

    @Test
    @DisplayName("Deve lançar exceção para produto nulo")
    void deveLancarExcecaoParaProdutoNulo() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        int prazoMeses = 24;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            calculoJurosService.simularEmprestimo(null, valorSolicitado, prazoMeses);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção para prazo excedendo limite do produto")
    void deveLancarExcecaoParaPrazoExcedendoLimite() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        int prazoMeses = 100; // Excede o prazo máximo de 60 meses

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);
        });
    }

    @Test
    @DisplayName("Deve calcular valor total com juros corretamente")
    void deveCalcularValorTotalComJurosCorretamente() {
        // Given
        BigDecimal valorSolicitado = new BigDecimal("5000.00");
        int prazoMeses = 24;

        // When
        SimulacaoEmprestimoResponse simulacao = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);

        // Then
        BigDecimal valorParcela = simulacao.getMemoriaCalculo().get(0).getValorParcela();
        BigDecimal valorTotalEsperado = valorParcela.multiply(BigDecimal.valueOf(prazoMeses));
        assertEquals(valorTotalEsperado, simulacao.getValorTotalComJuros());
        assertTrue(simulacao.getValorTotalComJuros().compareTo(valorSolicitado) > 0);
    }

    @Test
    @DisplayName("Deve lançar exceção para taxa de juros anual nula")
    void deveLancarExcecaoParaTaxaJurosAnualNula() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.calcularTaxaJurosEfetivaMensal(null));
    }

    @Test
    @DisplayName("Deve lançar exceção para taxa de juros anual negativa")
    void deveLancarExcecaoParaTaxaJurosAnualNegativa() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.calcularTaxaJurosEfetivaMensal(new BigDecimal("-5.0")));
    }

    @Test
    @DisplayName("Deve lançar exceção para prazo zero")
    void deveLancarExcecaoParaPrazoZero() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.calcularValorParcela(new BigDecimal("10000.00"), new BigDecimal("1.0"), 0));
    }

    @Test
    @DisplayName("Deve lançar exceção para prazo negativo")
    void deveLancarExcecaoParaPrazoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.calcularValorParcela(new BigDecimal("10000.00"), new BigDecimal("1.0"), -1));
    }

    @Test
    @DisplayName("Deve lançar exceção para taxa mensal nula")
    void deveLancarExcecaoParaTaxaMensalNula() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.calcularValorParcela(new BigDecimal("10000.00"), null, 12));
    }

    @Test
    @DisplayName("Deve lançar exceção para valor solicitado nulo")
    void deveLancarExcecaoParaValorSolicitadoNulo() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.calcularValorParcela(null, new BigDecimal("1.0"), 12));
    }

    @Test
    @DisplayName("Deve lançar exceção para produto nulo na simulação")
    void deveLancarExcecaoParaProdutoNuloNaSimulacao() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.simularEmprestimo(null, new BigDecimal("10000.00"), 12));
    }

    @Test
    @DisplayName("Deve lançar exceção para valor nulo na simulação")
    void deveLancarExcecaoParaValorNuloNaSimulacao() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.simularEmprestimo(produto, null, 12));
    }

    @Test
    @DisplayName("Deve lançar exceção para prazo zero na simulação")
    void deveLancarExcecaoParaPrazoZeroNaSimulacao() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.simularEmprestimo(produto, new BigDecimal("10000.00"), 0));
    }

    @Test
    @DisplayName("Deve lançar exceção para prazo negativo na simulação")
    void deveLancarExcecaoParaPrazoNegativoNaSimulacao() {
        assertThrows(IllegalArgumentException.class, () -> 
            calculoJurosService.simularEmprestimo(produto, new BigDecimal("10000.00"), -1));
    }

    @Test
    @DisplayName("Deve testar potencia com expoente zero")
    void deveTestarPotenciaComExpoenteZero() {
        // Este teste força a execução do método potencia com expoente 0
        // através do cálculo de taxa de juros efetiva mensal
        BigDecimal taxaAnual = new BigDecimal("0.01"); // Taxa muito baixa para forçar cálculos internos
        BigDecimal taxaMensal = calculoJurosService.calcularTaxaJurosEfetivaMensal(taxaAnual);
        
        assertNotNull(taxaMensal);
        assertTrue(taxaMensal.compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    @DisplayName("Deve testar cenários complexos de cálculo")
    void deveTestarCenariosComplexosDeCalculo() {
        // Teste com valores que forçam execução de caminhos internos
        BigDecimal valorSolicitado = new BigDecimal("1.00"); // Valor mínimo
        int prazoMeses = 1; // Prazo mínimo
        
        SimulacaoEmprestimoResponse response = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);
        
        assertNotNull(response);
        assertEquals(1, response.getMemoriaCalculo().size());
        assertTrue(response.getValorTotalComJuros().compareTo(valorSolicitado) >= 0);
    }

    @Test
    @DisplayName("Deve testar cálculo com taxa muito alta")
    void deveTestarCalculoComTaxaMuitoAlta() {
        // Cria um produto com taxa muito alta para forçar cálculos complexos
        ProdutoEmprestimo produtoAltaTaxa = new ProdutoEmprestimo();
        produtoAltaTaxa.nome = "Produto Alta Taxa";
        produtoAltaTaxa.taxaJurosAnual = new BigDecimal("999.99"); // Taxa extremamente alta
        produtoAltaTaxa.prazoMaximoMeses = 12;
        
        BigDecimal valorSolicitado = new BigDecimal("2000.00");
        int prazoMeses = 6;
        
        SimulacaoEmprestimoResponse response = calculoJurosService.simularEmprestimo(produtoAltaTaxa, valorSolicitado, prazoMeses);
        
        assertNotNull(response);
        assertEquals(6, response.getMemoriaCalculo().size());
        assertTrue(response.getValorTotalComJuros().compareTo(valorSolicitado) > 0);
    }

    @Test
    @DisplayName("Deve testar cálculo com valores decimais complexos")
    void deveTestarCalculoComValoresDecimaisComplexos() {
        BigDecimal valorSolicitado = new BigDecimal("1234.56789");
        int prazoMeses = 13; // Número primo para forçar cálculos específicos
        
        SimulacaoEmprestimoResponse response = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);
        
        assertNotNull(response);
        assertEquals(13, response.getMemoriaCalculo().size());
        
        // Verifica se todos os meses têm valores válidos
        for (SimulacaoEmprestimoResponse.DetalhamentoMensal detalhe : response.getMemoriaCalculo()) {
            assertNotNull(detalhe.getValorParcela());
            assertNotNull(detalhe.getJuros());
            assertNotNull(detalhe.getAmortizacao());
            assertTrue(detalhe.getValorParcela().compareTo(BigDecimal.ZERO) > 0);
        }
    }

    @Test
    @DisplayName("Deve testar edge case com prazo muito longo")
    void deveTestarEdgeCaseComPrazoMuitoLongo() {
        // Given - prazo muito longo para testar edge cases
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.taxaJurosAnual = new BigDecimal("15.0");
        produto.prazoMaximoMeses = 480; // 40 anos
        
        BigDecimal valorSolicitado = new BigDecimal("100000.00");
        int prazoMeses = 480;

        // When
        SimulacaoEmprestimoResponse response = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);

        // Then
        assertNotNull(response);
        assertNotNull(response.taxaJurosEfetivaMensal);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(produto.taxaJurosAnual) < 0);
    }

    @Test
    @DisplayName("Deve testar edge case com taxa muito alta")
    void deveTestarEdgeCaseComTaxaMuitoAlta() {
        // Given - taxa muito alta para testar edge cases
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.taxaJurosAnual = new BigDecimal("1000.0"); // 1000% ao ano
        produto.prazoMaximoMeses = 12;
        
        BigDecimal valorSolicitado = new BigDecimal("2000.00");
        int prazoMeses = 12;

        // When
        SimulacaoEmprestimoResponse response = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);

        // Then
        assertNotNull(response);
        assertNotNull(response.taxaJurosEfetivaMensal);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(produto.taxaJurosAnual) < 0);
    }

    @Test
    @DisplayName("Deve testar edge case com valor muito pequeno")
    void deveTestarEdgeCaseComValorMuitoPequeno() {
        // Given - valor muito pequeno para testar edge cases
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.taxaJurosAnual = new BigDecimal("15.0");
        produto.prazoMaximoMeses = 12;
        
        BigDecimal valorSolicitado = new BigDecimal("1.00"); // R$ 1,00
        int prazoMeses = 12;

        // When
        SimulacaoEmprestimoResponse response = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);

        // Then
        assertNotNull(response);
        assertNotNull(response.taxaJurosEfetivaMensal);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(produto.taxaJurosAnual) < 0);
    }

    @Test
    @DisplayName("Deve testar edge case com valor muito grande")
    void deveTestarEdgeCaseComValorMuitoGrande() {
        // Given - valor muito grande para testar edge cases
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.taxaJurosAnual = new BigDecimal("15.0");
        produto.prazoMaximoMeses = 12;
        
        BigDecimal valorSolicitado = new BigDecimal("10000000.00"); // R$ 10 milhões
        int prazoMeses = 12;

        // When
        SimulacaoEmprestimoResponse response = calculoJurosService.simularEmprestimo(produto, valorSolicitado, prazoMeses);

        // Then
        assertNotNull(response);
        assertNotNull(response.taxaJurosEfetivaMensal);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(response.taxaJurosEfetivaMensal.compareTo(produto.taxaJurosAnual) < 0);
    }

}
