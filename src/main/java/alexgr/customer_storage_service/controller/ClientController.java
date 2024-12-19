package alexgr.customer_storage_service.controller;

import alexgr.customer_storage_service.DTO.ClientDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.service.client_service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for managing client-related operations.
 * Provides endpoints for creating, retrieving, and listing clients.
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    /**
     * Endpoint for creating a new client.
     *
     * @param clientDTO the client data transfer object containing client information.
     * @return a {@link ResponseEntity} with a success message.
     */
    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody ClientDTO clientDTO) {
        log.info("client data " + clientDTO);
        clientService.createClient(clientDTO);
        return ResponseEntity.ok("Client created successfully");
    }
    /**
     * Endpoint for retrieving all clients.
     *
     * @return a {@link ResponseEntity} containing a list of all clients.
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Endpoint for retrieving a client by their ID.
     *
     * @param id the ID of the client to retrieve.
     * @return a {@link ResponseEntity} containing the client with the given ID.
     * @throws RuntimeException if the client with the specified ID is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }
}
