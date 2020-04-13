package init.crud1.repository;

import init.crud1.entity.Activity;
import init.crud1.entity.SportsMan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

    @Query("Select event from Activity event where event.id = :id")
    Activity findSpecific(
            @Param("id") Long id);

    @Query("Select event from Activity event where event.creator = :id")
    List<Activity> findByCreator(
            @Param("id") SportsMan sportsMan);

    @Query("Select sportsman from SportsMan sportsman where sportsman not in (:list)")
    List<SportsMan> findNotRegistered(
            @Param("list") List<SportsMan> listNonInscrits);

}
