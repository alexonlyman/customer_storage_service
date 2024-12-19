package alexgr.customer_storage_service.service.contact_service;

import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Contact;

import java.util.List;

public interface ContactService {

    void createContact(Integer clientId, ContactDTO contactDTO);

    List<ContactDTO> getAllContactsByClient(Integer id);

    List<ContactDTO> getContactsByParam(Integer clientId, String phoneNumber, String email);
}
