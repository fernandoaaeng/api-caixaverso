package com.caixa.controller;

import com.caixa.dto.ProdutoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoRequest;
import com.caixa.dto.SimulacaoEmprestimoResponse;
import com.caixa.model.ProdutoEmprestimo;
import com.caixa.service.ProdutoEmprestimoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Produtos de Empréstimo", description = "Operações para gerenciar produtos de empréstimo")
public class ProdutoEmprestimoController {

    @Inject
    ProdutoEmprestimoService produtoEmprestimoService;

    @GET
    @Operation(
        summary = "Listar todos os produtos",
        description = "Retorna uma lista com todos os produtos de empréstimo cadastrados"
    )
    @APIResponse(
        responseCode = "200",
        description = "Lista de produtos retornada com sucesso",
        content = @Content(schema = @Schema(implementation = ProdutoEmprestimo.class))
    )
    public Response listarTodos() {
        List<ProdutoEmprestimo> produtos = produtoEmprestimoService.listarTodos();
        return Response.ok(produtos).build();
    }

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Buscar produto por ID",
        description = "Retorna um produto específico baseado no ID fornecido"
    )
    @APIResponses({
        @APIResponse(
            responseCode = "200",
            description = "Produto encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoEmprestimo.class))
        ),
        @APIResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    public Response buscarPorId(@PathParam("id") Long id) {
        ProdutoEmprestimo produto = produtoEmprestimoService.buscarPorId(id);
        return Response.ok(produto).build();
    }

    @POST
    @Operation(
        summary = "Criar novo produto",
        description = "Cria um novo produto de empréstimo com as informações fornecidas"
    )
    @APIResponses({
        @APIResponse(
            responseCode = "201",
            description = "Produto criado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoEmprestimo.class))
        ),
        @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
        )
    })
    public Response criar(@Valid ProdutoEmprestimoRequest request) {
        ProdutoEmprestimo produto = produtoEmprestimoService.criar(request);
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
        summary = "Atualizar produto",
        description = "Atualiza um produto existente com as novas informações fornecidas"
    )
    @APIResponses({
        @APIResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoEmprestimo.class))
        ),
        @APIResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        ),
        @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
        )
    })
    public Response atualizar(@PathParam("id") Long id, @Valid ProdutoEmprestimoRequest request) {
        ProdutoEmprestimo produto = produtoEmprestimoService.atualizar(id, request);
        return Response.ok(produto).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Remover produto",
        description = "Remove um produto do sistema"
    )
    @APIResponses({
        @APIResponse(
            responseCode = "204",
            description = "Produto removido com sucesso"
        ),
        @APIResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    public Response remover(@PathParam("id") Long id) {
        produtoEmprestimoService.remover(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/simulacao")
    @Operation(
        summary = "Simular empréstimo",
        description = "Simula um empréstimo baseado no produto e parâmetros fornecidos, " +
                     "retornando o detalhamento mês a mês com cálculo de juros"
    )
    @APIResponses({
        @APIResponse(
            responseCode = "200",
            description = "Simulação realizada com sucesso",
            content = @Content(schema = @Schema(implementation = SimulacaoEmprestimoResponse.class))
        ),
        @APIResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        ),
        @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
        )
    })
    public Response simularEmprestimo(@Valid SimulacaoEmprestimoRequest request) {
        SimulacaoEmprestimoResponse simulacao = produtoEmprestimoService.simularEmprestimo(request);
        return Response.ok(simulacao).build();
    }
}
