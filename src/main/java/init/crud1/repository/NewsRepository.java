package init.crud1.repository;

import init.crud1.entity.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {

}
