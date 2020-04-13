package init.crud1.repository;

import init.crud1.entity.SportsMan;
import init.crud1.entity.Statistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticRepository extends CrudRepository<Statistic, Long> {

    @Query("Select statistic from Statistic statistic where statistic.sportsMan = :id")
    List<Statistic> findBySportsMan(
            @Param("id") SportsMan id);

}
