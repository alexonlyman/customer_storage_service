package alexgr.customer_storage_service.service;

import alexgr.customer_storage_service.DTO.ClientDTO;
import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.entity.Contact;

import java.util.List;

public interface ClientService {
    public void creatingClient(ClientDTO clientDTO);

    public void creatingContact(ContactDTO contactDTO);

    public List<Client> getAllClients();

    public Client getClient(Integer id);

    public List<Contact> getAllContactsByClient(Integer id, String name);

    public List<Contact> getContactsByParam(String clientId, String clientName, String phoneNumber, String email);


}
