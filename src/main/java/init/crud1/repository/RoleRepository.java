package init.crud1.repository;

import init.crud1.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository  extends CrudRepository<Role, Long> {

    @Query("Select role from Role role where role.id = :id")
    List<Role> findForInitialize(
            @Param("id") Long id);


}
