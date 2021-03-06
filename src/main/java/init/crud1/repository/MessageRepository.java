package init.crud1.repository;

import init.crud1.entity.Message;
import init.crud1.entity.SportsMan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("Select message from Message message where message.originator = :user")
    List<Message> findByCreator(
            @Param("user") SportsMan sportsman);

    @Query("Select message from Message message where :user member of message.addressee")
    List<Message> findByReceptor(
            @Param("user") SportsMan sportsman);

}
