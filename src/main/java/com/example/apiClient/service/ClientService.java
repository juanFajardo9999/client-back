package com.example.apiClient.service;

import com.example.apiClient.exception.ClientNotFoundException;
import com.example.apiClient.exception.InvalidDocumentTypeException;
import com.example.apiClient.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private List<Client> mockList = new ArrayList<>(Arrays.asList(
            new Client("Juan", "Carlos", "Perez", "Gomez", "1234567890",
                    "Calle 123", "Bogotá", "C", 23445322)
    ));

    public Client getClient(String documentType, String documentNumber) {
        logger.info("Fetching client with documentType={} and documentNumber={}", documentType, documentNumber);

        validateDocumentType(documentType);

        for (Client client : mockList) {
            if (client.getDocumentType().equalsIgnoreCase(documentType) &&
                    String.valueOf(client.getDocumentNumber()).equals(documentNumber)) {
                logger.debug("Client found: {}", client);
                return client;
            }
        }

        logger.warn("Client not found for documentType={} and documentNumber={}", documentType, documentNumber);
        throw new ClientNotFoundException("Client not found with document " + documentType + " " + documentNumber);
    }

    private void validateDocumentType(String documentType) {
        if (!"C".equalsIgnoreCase(documentType) && !"P".equalsIgnoreCase(documentType)) {
            logger.warn("Invalid document type received: {}", documentType);
            throw new InvalidDocumentTypeException("Invalid document type: " + documentType);
        }
    }
}
