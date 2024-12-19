package alexgr.customer_storage_service.repository;

import alexgr.customer_storage_service.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing {@link Contact} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations
 * and includes custom queries for retrieving contacts based on criteria.
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    /**
     * Finds a list of contacts for a specific client based on optional phone number
     * and email address criteria. If phone number or email is not provided (null), the
     * corresponding filter will be ignored.
     *
     * @param clientId    the ID of the client whose contacts are being retrieved.
     * @param phoneNumber the optional phone number filter (supports partial match).
     * @param email       the optional email address filter (supports partial match).
     * @return a list of {@link Contact} entities matching the given criteria.
     */

    @Query("SELECT c FROM Contact c " +
            "WHERE c.client.id = :clientId " +
            "AND (:phoneNumber IS NULL OR c.phoneNumber LIKE %:phoneNumber%) " +
            "AND (:email IS NULL OR c.emailAddress LIKE %:email%)")
    List<Contact> findByCriteria(@Param("clientId") Integer clientId,
                                 @Param("phoneNumber") String phoneNumber,
                                 @Param("email") String email);

    /**
     * Retrieves all contacts associated with a specific client.
     *
     * @param clientId the ID of the client whose contacts are being retrieved.
     * @return a list of {@link Contact} entities belonging to the given client.
     */
    @Query("SELECT c FROM Contact c WHERE c.client.id = :clientId")
    List<Contact> findAllByClientId(@Param("clientId") Integer clientId);


}
