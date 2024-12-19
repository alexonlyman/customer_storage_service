package alexgr.customer_storage_service.service.client_service.impl;

import alexgr.customer_storage_service.DTO.ClientDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.mapper.ClientMapper;
import alexgr.customer_storage_service.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createClient_ShouldSaveClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Test Client");

        Client client = new Client();
        client.setName("Test Client");

        when(mapper.toEntity(clientDTO)).thenReturn(client);

        clientService.createClient(clientDTO);

        verify(mapper, times(1)).toEntity(clientDTO);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void getAllClients_ShouldReturnClientList() {
        Client client1 = new Client();
        client1.setName("Client 1");

        Client client2 = new Client();
        client2.setName("Client 2");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> clients = clientService.getAllClients();

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals("Client 1", clients.get(0).getName());
        assertEquals("Client 2", clients.get(1).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClient_ShouldReturnClient_WhenClientExists() {
        Integer clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");

        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));

        Client foundClient = clientService.getClient(clientId);

        assertNotNull(foundClient);
        assertEquals(clientId, foundClient.getId());
        assertEquals("Test Client", foundClient.getName());
        verify(clientRepository, times(1)).findClientById(clientId);
    }

    @Test
    void getClient_ShouldThrowException_WhenClientDoesNotExist() {
        Integer clientId = 1;
        when(clientRepository.findClientById(clientId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.getClient(clientId));
        assertEquals("Client with ID " + clientId + " not found", exception.getMessage());
        verify(clientRepository, times(1)).findClientById(clientId);
    }

}