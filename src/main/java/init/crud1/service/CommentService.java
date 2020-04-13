package init.crud1.service;

import init.crud1.entity.Comment;
import init.crud1.entity.Statistic;
import init.crud1.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    //SaveComment
    public void saveComment(Comment comment){
        this.commentRepository.save(comment);
    }
}
