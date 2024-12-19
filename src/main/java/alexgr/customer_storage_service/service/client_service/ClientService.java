package alexgr.customer_storage_service.service.client_service;

import alexgr.customer_storage_service.DTO.ClientDTO;
import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.entity.Contact;

import java.util.List;

public interface ClientService {

    void createClient(ClientDTO clientDTO);
    List<Client> getAllClients();
    Client getClient(Integer id);


}
