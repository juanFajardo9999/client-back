package com.example.apiClient;

import com.example.apiClient.controller.ClientController;
import com.example.apiClient.exception.ClientNotFoundException;
import com.example.apiClient.exception.InvalidDocumentTypeException;
import com.example.apiClient.model.Client;
import com.example.apiClient.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    private final ClientService clientService = mock(ClientService.class);
    private final ClientController clientController = new ClientController(clientService);

    @Test
    public void testGetClientSuccess() {
        Client mockClient = new Client(
                "Juan", "Carlos", "Perez", "Gomez",
                "1234567890", "Calle 123", "Bogotá", "C", 23445322);
        when(clientService.getClient("C", "23445322")).thenReturn(mockClient);

        ResponseEntity<?> response = clientController.getClient("C", "23445322");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockClient, response.getBody());
    }

    @Test
    public void testGetClientNotFound() {
        when(clientService.getClient("C", "99999999"))
                .thenThrow(new ClientNotFoundException("Client not found with document C 99999999"));

        ResponseEntity<?> response = clientController.getClient("C", "99999999");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testInvalidDocumentType() {
        when(clientService.getClient("X", "23445322"))
                .thenThrow(new InvalidDocumentTypeException("Invalid document type: X"));

        ResponseEntity<?> response = clientController.getClient("X", "23445322");

        assertEquals(400, response.getStatusCodeValue());
    }
}
