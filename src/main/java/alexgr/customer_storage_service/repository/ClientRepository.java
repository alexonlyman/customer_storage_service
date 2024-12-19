package alexgr.customer_storage_service.repository;

import alexgr.customer_storage_service.entity.Client;
import alexgr.customer_storage_service.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for managing {@link Client} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations
 * and includes a custom query for retrieving a client by its ID.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    /**
     * Retrieves a client by its ID.
     *
     * @param id the ID of the client to retrieve.
     * @return an {@link Optional} containing the {@link Client} if found,
     *         or an empty {@link Optional} if no client exists with the given ID.
     */
    Optional<Client> findClientById(Integer id);


}
