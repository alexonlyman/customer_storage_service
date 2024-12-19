package alexgr.customer_storage_service.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ContactDTO {
    private String name;
    private String phoneNumber;
    private String emailAddress;
}
