package com.caixa.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Health Check", description = "Verificação de saúde da API")
public class HealthCheckController {

    @GET
    @Operation(
        summary = "Verificar saúde da API",
        description = "Retorna o status de saúde da API"
    )
    @APIResponse(
        responseCode = "200",
        description = "API funcionando corretamente"
    )
    public Response healthCheck() {
        return Response.ok(new HealthStatus("UP", "API funcionando corretamente")).build();
    }

    public static class HealthStatus {
        public String status;
        public String message;

        public HealthStatus() {
        }

        public HealthStatus(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
