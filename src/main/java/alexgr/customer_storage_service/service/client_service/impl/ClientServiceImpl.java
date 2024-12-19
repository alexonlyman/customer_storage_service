package alexgr.customer_storage_service.service.impl;

import alexgr.customer_storage_service.DTO.ClientDTO;
import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.entity.Contact;
import alexgr.customer_storage_service.mapper.ClientMapper;
import alexgr.customer_storage_service.repository.ClientRepository;
import alexgr.customer_storage_service.repository.ContactRepository;
import alexgr.customer_storage_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ContactRepository contactRepository;
    private final ClientMapper mapper;

    @Override
    public void creatingClient(ClientDTO clientDTO) {
        Client client = mapper.toEntity(clientDTO);
        clientRepository.save(client);
    }

    @Override
    public void creatingContact(ContactDTO contactDTO) {
        Contact contact = mapper.toEntity(contactDTO);
        contactRepository.save(contact);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(Integer id) {
        return clientRepository.findClientById(id)
                .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));
    }

    @Override
    public List<Contact> getAllContactsByClient(Integer id, String name) {
        Client client = getClientByIdOrName(id, name);
        return contactRepository.findAllByClientId(client.getId())
                .orElseThrow(() -> new RuntimeException("No contacts found for client ID " + client.getId()));
    }

    @Override
    public List<Contact> getContactsByParam(String clientId, String clientName, String phoneNumber, String email) {
        Client client = getClientByIdOrName(
                clientId != null ? Integer.parseInt(clientId) : null,
                clientName
        );

        if (phoneNumber != null) {
            return contactRepository.findByClientIdAndPhoneNumber(client.getId(), phoneNumber)
                    .orElseThrow(() -> new RuntimeException("No contact with phone number " + phoneNumber + " found for client"));
        }

        if (email != null) {
            return contactRepository.findByClientIdAndEmailAddress(client.getId(), email)
                    .orElseThrow(() -> new RuntimeException("No contact with email address " + email + " found for client"));
        }

        return contactRepository.findAllByClientId(client.getId())
                .orElseThrow(() -> new RuntimeException("No contacts found for client ID " + client.getId()));
    }

    // Вспомогательный метод для поиска клиента по ID или имени
    private Client getClientByIdOrName(Integer id, String name) {
        if (id != null) {
            return clientRepository.findClientById(id)
                    .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));
        }

        if (name != null) {
            return clientRepository.findClientByName(name)
                    .orElseThrow(() -> new RuntimeException("Client with name " + name + " not found"));
        }

        throw new IllegalArgumentException("Either ID or name must be provided");
    }
}
