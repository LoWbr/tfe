package init.crud1.repository;

import init.crud1.entity.Activity;
import init.crud1.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("Select comment from Comment comment where comment.activity = :id")
    List<Comment> findForEvent(
            @Param("id") Activity id);

}
