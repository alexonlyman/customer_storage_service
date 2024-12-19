package alexgr.customer_storage_service.controller;

import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Contact;
import alexgr.customer_storage_service.mapper.ClientMapper;
import alexgr.customer_storage_service.service.contact_service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing contact-related operations.
 * Provides endpoints for creating and retrieving contacts for clients.
 */
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    /**
     * Endpoint for creating a new contact for a specific client.
     *
     * @param clientId   the ID of the client to associate the contact with.
     * @param contactDTO the contact data transfer object containing contact information.
     * @return a {@link ResponseEntity} with a success message.
     */
    @PostMapping("/{clientId}")
    public ResponseEntity<String> createContact(@PathVariable Integer clientId, @RequestBody ContactDTO contactDTO) {
        contactService.createContact(clientId, contactDTO);
        return ResponseEntity.ok("Contact created successfully for client ID " + clientId);
    }

    /**
     * Endpoint for retrieving all contacts associated with a specific client.
     *
     * @param clientId the ID of the client whose contacts are to be retrieved.
     * @return a {@link ResponseEntity} containing a list of {@link ContactDTO} objects.
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContactDTO>> getAllContactsByClient(@PathVariable Integer clientId) {
        return ResponseEntity.ok(contactService.getAllContactsByClient(clientId));
    }

    /**
     * Endpoint for searching contacts based on client ID, phone number, and/or email address.
     *
     * @param clientId    (optional) the ID of the client whose contacts are to be searched.
     * @param phoneNumber (optional) the phone number to search for.
     * @param email       (optional) the email address to search for.
     * @return a {@link ResponseEntity} containing a list of {@link ContactDTO} objects that match the search criteria.
     * @throws RuntimeException if no contacts match the provided criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ContactDTO>> getContactsByParam(
            @RequestParam(required = false) Integer clientId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email
    ) {
        return ResponseEntity.ok(contactService.getContactsByParam(clientId, phoneNumber, email));
    }

}
