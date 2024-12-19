package alexgr.customer_storage_service.service.contact_service.impl;

import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.entity.Contact;
import alexgr.customer_storage_service.mapper.ClientMapper;
import alexgr.customer_storage_service.repository.ClientRepository;
import alexgr.customer_storage_service.repository.ContactRepository;
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

class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ContactServiceImpl contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createContact_ShouldSaveContact() {
        Integer clientId = 1;

        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("Test Contact");
        contactDTO.setPhoneNumber("1234567890");
        contactDTO.setEmailAddress("test@example.com");

        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setEmailAddress(contactDTO.getEmailAddress());

        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));
        when(mapper.toEntity(contactDTO)).thenReturn(contact);

        contactService.createContact(clientId, contactDTO);

        verify(clientRepository, times(1)).findClientById(clientId);
        verify(mapper, times(1)).toEntity(contactDTO);
        verify(contactRepository, times(1)).save(contact);
        assertEquals(client, contact.getClient());
    }

    @Test
    void getAllContactsByClient_ShouldReturnContactDTOList() {
        Integer clientId = 1;

        Contact contact1 = new Contact();
        contact1.setName("Contact 1");

        Contact contact2 = new Contact();
        contact2.setName("Contact 2");

        when(contactRepository.findAllByClientId(clientId)).thenReturn(Arrays.asList(contact1, contact2));

        ContactDTO contactDTO1 = new ContactDTO();
        contactDTO1.setName("Contact 1");

        ContactDTO contactDTO2 = new ContactDTO();
        contactDTO2.setName("Contact 2");

        when(mapper.toDTOList(anyList())).thenReturn(Arrays.asList(contactDTO1, contactDTO2));

        List<ContactDTO> contacts = contactService.getAllContactsByClient(clientId);

        assertNotNull(contacts);
        assertEquals(2, contacts.size());
        assertEquals("Contact 1", contacts.get(0).getName());
        assertEquals("Contact 2", contacts.get(1).getName());
        verify(contactRepository, times(1)).findAllByClientId(clientId);
    }

    @Test
    void getContactsByParam_ShouldReturnFilteredContacts() {
        Integer clientId = 1;
        String phoneNumber = "1234567890";
        String email = "test@example.com";

        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");

        Contact contact = new Contact();
        contact.setName("Test Contact");
        contact.setPhoneNumber(phoneNumber);
        contact.setEmailAddress(email);

        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));
        when(contactRepository.findByCriteria(clientId, phoneNumber, email)).thenReturn(List.of(contact));

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("Test Contact");
        contactDTO.setPhoneNumber(phoneNumber);
        contactDTO.setEmailAddress(email);

        when(mapper.toDTOList(anyList())).thenReturn(List.of(contactDTO));

        List<ContactDTO> contacts = contactService.getContactsByParam(clientId, phoneNumber, email);

        assertNotNull(contacts);
        assertEquals(1, contacts.size());
        assertEquals("Test Contact", contacts.get(0).getName());
        verify(clientRepository, times(1)).findClientById(clientId);
        verify(contactRepository, times(1)).findByCriteria(clientId, phoneNumber, email);
    }

    @Test
    void getContactsByParam_ShouldThrowException_WhenNoContactsFound() {
        Integer clientId = 1;
        String phoneNumber = "1234567890";
        String email = "test@example.com";

        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");

        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));
        when(contactRepository.findByCriteria(clientId, phoneNumber, email)).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> contactService.getContactsByParam(clientId, phoneNumber, email));
        assertEquals("No contacts found for client 'Test Client' with given criteria", exception.getMessage());
        verify(clientRepository, times(1)).findClientById(clientId);
        verify(contactRepository, times(1)).findByCriteria(clientId, phoneNumber, email);
    }

}