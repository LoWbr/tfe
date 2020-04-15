package init.crud1.repository;

import init.crud1.entity.Activity;
import init.crud1.entity.PromotionRequest;
import init.crud1.entity.Role;
import init.crud1.entity.SportsMan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionRequestRepository extends CrudRepository<PromotionRequest, Long> {

    @Query("Select promotionrequest from PromotionRequest promotionrequest where promotionrequest.candidate = :id")
    PromotionRequest findByCandidate(
            @Param("id") SportsMan sportsMan);

}
