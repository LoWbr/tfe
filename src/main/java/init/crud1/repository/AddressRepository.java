package init.crud1.repository;

import init.crud1.entity.Activity;
import init.crud1.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends CrudRepository<Address, Long> {
    @Query("Select address from Address address where address.id = :id")
    Address findSpecific(
            @Param("id") Long id);
}
