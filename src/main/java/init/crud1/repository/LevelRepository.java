package init.crud1.repository;

import init.crud1.entity.Activity;
import init.crud1.entity.Level;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LevelRepository extends CrudRepository<Level,Long> {

    @Query("Select level from Level level where level.place = :id")
    Level findSpecific(
            @Param("id") Long id);

}
