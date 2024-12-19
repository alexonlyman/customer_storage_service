package alexgr.customer_storage_service.service.contact_service.impl;

import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.entity.Contact;
import alexgr.customer_storage_service.mapper.ClientMapper;
import alexgr.customer_storage_service.repository.ClientRepository;
import alexgr.customer_storage_service.repository.ContactRepository;
import alexgr.customer_storage_service.service.contact_service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of the ContactService interface, responsible for managing contacts associated with clients.
 * This service handles the creation of contacts, fetching contacts by client ID, and retrieving contacts by specific parameters.
 */

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper mapper;

    /**
     * Creates a new contact for a specified client.
     *
     * @param clientId   the ID of the client to whom the contact is associated.
     * @param contactDTO the data transfer object containing contact details.
     * @throws RuntimeException if the client with the specified ID is not found.
     */
    @Override
    public void createContact(Integer clientId, ContactDTO contactDTO) {
        Client client = getClientById(clientId);
        Contact contact = mapper.toEntity(contactDTO);
        contact.setClient(client);
        contactRepository.save(contact);
    }

    /**
     * Retrieves all contacts associated with a specific client.
     *
     * @param id the ID of the client whose contacts are to be retrieved.
     * @return a list of {@link ContactDTO} representing the client's contacts.
     */
    @Override
    public List<ContactDTO> getAllContactsByClient(Integer id) {
        List<Contact> contactList = contactRepository.findAllByClientId(id);
        return mapper.toDTOList(contactList);
    }

    /**
     * Retrieves contacts associated with a specific client based on provided parameters.
     *
     * @param clientId    the ID of the client.
     * @param phoneNumber the phone number to filter contacts (can be null).
     * @param email       the email address to filter contacts (can be null).
     * @return a list of {@link ContactDTO} matching the provided criteria.
     * @throws RuntimeException if no contacts matching the criteria are found.
     */

    @Override
    public List<ContactDTO> getContactsByParam(Integer clientId, String phoneNumber, String email) {
        Client client = getClientById(clientId);
        List<Contact> contacts = contactRepository.findByCriteria(client.getId(), phoneNumber, email);
        if (contacts.isEmpty()) {
            throw new RuntimeException(
                    String.format("No contacts found for client '%s' with given criteria", client.getName()));
        }
        return mapper.toDTOList(contacts);
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param id the ID of the client to retrieve.
     * @return the {@link Client} entity if found.
     * @throws RuntimeException if the client with the specified ID is not found.
     */
    private Client getClientById(Integer id) {
        return clientRepository.findClientById(id).orElseThrow(() -> new RuntimeException("client was not found"));
    }
}
