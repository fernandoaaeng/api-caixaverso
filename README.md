# API CaixaVerso - Produtos de Empréstimo

Sistema backend para gerenciamento de produtos de empréstimo com cálculo de juros, desenvolvido com Quarkus.

## Funcionalidades

- CRUD de Produtos de Empréstimo
- Simulação de Empréstimos com cálculo detalhado mês a mês
- Sistema Price para cálculo de parcelas fixas
- Documentação interativa com Swagger
- Banco H2 em memória para desenvolvimento

## Tecnologias

- Java 17
- Quarkus 3.6.0
- H2 Database
- JPA/Hibernate com Panache
- RESTEasy Reactive
- Swagger/OpenAPI
- JUnit 5 + Mockito
- Maven

## Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Executando a aplicação

```bash
# Clonar o repositório
git clone <repository-url>
cd api-caixaverso

# Executar em modo desenvolvimento
mvn compile quarkus:dev

# Executar os testes
mvn test

# Executar com cobertura de testes
mvn test jacoco:report
```

A aplicação estará disponível em: `http://localhost:8080`

## Documentação da API

- Swagger UI: http://localhost:8080/q/swagger-ui
- OpenAPI Spec: http://localhost:8080/q/openapi
- Health Check: http://localhost:8080/health

## Endpoints Principais

### Produtos de Empréstimo

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/produtos` | Lista todos os produtos |
| GET | `/api/produtos/{id}` | Busca produto por ID |
| POST | `/api/produtos` | Cria novo produto |
| PUT | `/api/produtos/{id}` | Atualiza produto |
| DELETE | `/api/produtos/{id}` | Remove produto |

### Simulação de Empréstimo

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/produtos/simulacao` | Simula empréstimo |

## Exemplo de Uso

### Criar um Produto

```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Empréstimo Pessoal",
    "taxaJurosAnual": 18.00,
    "prazoMaximoMeses": 60
  }'
```

### Simular Empréstimo

```bash
curl -X POST http://localhost:8080/api/produtos/simulacao \
  -H "Content-Type: application/json" \
  -d '{
    "idProduto": 1,
    "valorSolicitado": 10000.00,
    "prazoMeses": 24
  }'
```

## Fórmulas de Cálculo

### Taxa de Juros Efetiva Mensal
```
i = (1 + i_anual)^(1/12) - 1
```

### Sistema Price (Parcelas Fixas)
```
PMT = PV × [i × (1 + i)^n] / [(1 + i)^n - 1]
```

Onde:
- PMT: Valor da parcela
- PV: Valor presente (solicitado)
- i: Taxa de juros mensal
- n: Número de parcelas

## Testes

O projeto possui cobertura de testes de 99%:

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura
mvn test jacoco:report

```


## Decisões de Arquitetura

### Estrutura em Camadas
- **Controllers**: Endpoints REST com validação de entrada
- **Services**: Lógica de negócio e orquestração
- **Repositories**: Acesso a dados com Panache
- **DTOs**: Transferência de dados entre camadas
- **Models**: Entidades JPA

### Validações
- Bean Validation para validação de entrada
- Validação de negócio nos services
- Tratamento de exceções centralizado

### Testes
- Testes unitários com Mockito para isolamento
- Cobertura de 99% focada em lógica de negócio
- Testes de integração para endpoints

### Banco de Dados
- H2 em memória para desenvolvimento
- Dados pré-carregados via import.sql
- Transações gerenciadas pelo Quarkus

## Estrutura do Projeto

```
src/
├── main/java/com/caixa/
│   ├── controller/              # Endpoints REST
│   ├── dto/                     # Data Transfer Objects
│   ├── model/                   # Entidades JPA
│   ├── repository/              # Acesso a dados
│   └── service/                 # Lógica de negócio
├── main/resources/
│   ├── application.properties   # Configurações
│   └── import.sql              # Dados iniciais
└── test/java/com/caixa/         # Testes unitários
```

## Exemplo de Resposta da Simulação

```json
{
  "produto": {
    "id": 1,
    "nome": "Empréstimo Pessoal",
    "taxaJurosAnual": 18.00,
    "prazoMaximoMeses": 60
  },
  "taxaJurosAnual": 18.00,
  "taxaJurosEfetivaMensal": 1.39,
  "valorSolicitado": 10000.00,
  "valorTotalComJuros": 12000.00,
  "memoriaCalculo": [
    {
      "mes": 1,
      "valorParcela": 500.00,
      "juros": 139.00,
      "amortizacao": 361.00,
      "saldoDevedor": 9639.00
    }
  ]
}
```

## Licença

Este projeto está sob a licença MIT.