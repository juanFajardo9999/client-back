package com.example.apiClient.controller;

import com.example.apiClient.exception.ClientNotFoundException;
import com.example.apiClient.exception.InvalidDocumentTypeException;
import com.example.apiClient.model.Client;
import com.example.apiClient.model.ErrorResponse;
import com.example.apiClient.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{documentType}/{documentNumber}")
    public ResponseEntity<?> getClient(
            @PathVariable String documentType,
            @PathVariable String documentNumber) {
        logger.info("Received request: GET /api/clients/{}/{}", documentType, documentNumber);

        try {
            Client client = clientService.getClient(documentType, documentNumber);
            logger.info("Client found: {}", client);
            return ResponseEntity.ok(client);
        } catch (ClientNotFoundException e) {
            logger.warn("Client not found: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(
                    "Client not found",
                    "No client found with documentType=" + documentType + " and documentNumber=" + documentNumber
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (InvalidDocumentTypeException e) {
            logger.warn("Invalid document type: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(
                    "Invalid document type",
                    "Allowed types are 'C' for Cedula and 'P' for Passport."
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            ErrorResponse errorResponse = new ErrorResponse(
                    "Internal server error",
                    "An unexpected error occurred. Please try again later."
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
