package init.crud1.repository;

import init.crud1.entity.Activity;
import init.crud1.entity.ActivityType;
import init.crud1.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Long> {
    @Query("Select activitytype from ActivityType activitytype where activitytype.id = :id")
    ActivityType findSpecific(
            @Param("id") Long id);
}
