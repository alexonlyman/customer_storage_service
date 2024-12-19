package alexgr.customer_storage_service.service.client_service.impl;

import alexgr.customer_storage_service.DTO.ClientDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.mapper.ClientMapper;
import alexgr.customer_storage_service.repository.ClientRepository;
import alexgr.customer_storage_service.service.client_service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of the ClientService interface, responsible for managing client-related operations.
 * This service provides functionality for creating clients, retrieving all clients, and fetching a client by ID.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper mapper;


    /**
     * Creates a new client based on the provided ClientDTO.
     *
     * @param clientDTO the data transfer object containing client details.
     */
    @Override
    public void createClient(ClientDTO clientDTO) {
        log.info("ClientDTO received in service: " + clientDTO);
        Client client = mapper.toEntity(clientDTO);
        log.info("Mapped Client entity: " + client);
        clientRepository.save(client);
    }

    /**
     * Retrieves a list of all clients.
     *
     * @return a list of {@link Client} entities representing all clients in the system.
     */
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * Retrieves a specific client by their ID.
     *
     * @param id the ID of the client to retrieve.
     * @return the {@link Client} entity if found.
     * @throws RuntimeException if the client with the specified ID is not found.
     */
    @Override
    public Client getClient(Integer id) {
        return clientRepository.findClientById(id)
                .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));
    }
}
