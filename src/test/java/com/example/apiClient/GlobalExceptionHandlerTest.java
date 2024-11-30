package com.example.apiClient;

import com.example.apiClient.controller.GlobalExceptionHandler;
import com.example.apiClient.exception.ClientNotFoundException;
import com.example.apiClient.exception.InvalidDocumentTypeException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleClientNotFound() {
        ResponseEntity<String> response = globalExceptionHandler.handleClientNotFound(
                new ClientNotFoundException("Client not found"));

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Client not found", response.getBody());
    }

    @Test
    public void testHandleInvalidDocumentType() {
        ResponseEntity<String> response = globalExceptionHandler.handleInvalidDocumentType(
                new InvalidDocumentTypeException("Invalid document type"));

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid document type", response.getBody());
    }

    @Test
    public void testHandleGeneralException() {
        ResponseEntity<String> response = globalExceptionHandler.handleGeneralException(
                new RuntimeException("Unexpected error"));

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected error", response.getBody());
    }
}
