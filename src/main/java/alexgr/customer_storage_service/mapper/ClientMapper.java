package alexgr.customer_storage_service.mapper;

import alexgr.customer_storage_service.DTO.ClientDTO;
import alexgr.customer_storage_service.DTO.ContactDTO;
import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    List<ContactDTO> toDTOList(List<Contact> contacts);

    Client toEntity(ClientDTO clientDTO);

    Contact toEntity(ContactDTO contactDTO);
}
