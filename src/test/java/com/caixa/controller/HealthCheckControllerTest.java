package com.caixa.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HealthCheckControllerTest {

    @InjectMocks
    private HealthCheckController healthCheckController;

    @Test
    void testHealthCheck() {
        var resultado = healthCheckController.healthCheck();
        
        assertNotNull(resultado);
        assertEquals(200, resultado.getStatus());
        assertNotNull(resultado.getEntity());
    }

    @Test
    @DisplayName("Deve testar HealthStatus - construtor padrão")
    void deveTestarHealthStatusConstrutorPadrao() {
        // When
        HealthCheckController.HealthStatus healthStatus = new HealthCheckController.HealthStatus();

        // Then
        assertNotNull(healthStatus);
        assertNull(healthStatus.getStatus());
        assertNull(healthStatus.getMessage());
    }

    @Test
    @DisplayName("Deve testar HealthStatus - construtor com parâmetros")
    void deveTestarHealthStatusConstrutorComParametros() {
        // Given
        String status = "UP";
        String message = "API funcionando";

        // When
        HealthCheckController.HealthStatus healthStatus = new HealthCheckController.HealthStatus(status, message);

        // Then
        assertNotNull(healthStatus);
        assertEquals(status, healthStatus.getStatus());
        assertEquals(message, healthStatus.getMessage());
    }

    @Test
    @DisplayName("Deve testar HealthStatus - getters e setters")
    void deveTestarHealthStatusGettersESetters() {
        // Given
        HealthCheckController.HealthStatus healthStatus = new HealthCheckController.HealthStatus();
        String status = "DOWN";
        String message = "Sistema indisponível";

        // When
        healthStatus.setStatus(status);
        healthStatus.setMessage(message);

        // Then
        assertEquals(status, healthStatus.getStatus());
        assertEquals(message, healthStatus.getMessage());
    }

    @Test
    @DisplayName("Deve testar HealthStatus - setStatus com null")
    void deveTestarHealthStatusSetStatusComNull() {
        // Given
        HealthCheckController.HealthStatus healthStatus = new HealthCheckController.HealthStatus();

        // When
        healthStatus.setStatus(null);

        // Then
        assertNull(healthStatus.getStatus());
    }

    @Test
    @DisplayName("Deve testar HealthStatus - setMessage com null")
    void deveTestarHealthStatusSetMessageComNull() {
        // Given
        HealthCheckController.HealthStatus healthStatus = new HealthCheckController.HealthStatus();

        // When
        healthStatus.setMessage(null);

        // Then
        assertNull(healthStatus.getMessage());
    }

    @Test
    @DisplayName("Deve testar HealthStatus - setStatus com string vazia")
    void deveTestarHealthStatusSetStatusComStringVazia() {
        // Given
        HealthCheckController.HealthStatus healthStatus = new HealthCheckController.HealthStatus();

        // When
        healthStatus.setStatus("");

        // Then
        assertEquals("", healthStatus.getStatus());
    }

    @Test
    @DisplayName("Deve testar HealthStatus - setMessage com string vazia")
    void deveTestarHealthStatusSetMessageComStringVazia() {
        // Given
        HealthCheckController.HealthStatus healthStatus = new HealthCheckController.HealthStatus();

        // When
        healthStatus.setMessage("");

        // Then
        assertEquals("", healthStatus.getMessage());
    }
}
