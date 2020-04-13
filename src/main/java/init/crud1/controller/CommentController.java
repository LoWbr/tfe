package init.crud1.controller;

import init.crud1.entity.Activity;
import init.crud1.entity.Comment;
import init.crud1.entity.SportsMan;
import init.crud1.form.CommentForm;
import init.crud1.repository.ActivityRepository;
import init.crud1.repository.CommentRepository;
import init.crud1.repository.SportsManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
/*
@RequestMapping("/commment")
*/
public class CommentController {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    SportsManRepository sportsManRepository;

    @Autowired
    CommentRepository commentRepository;

    @RequestMapping(value = "/createComment{id}", method = RequestMethod.GET)
    public String createComment(@RequestParam Long id, Model model, Principal principal) {
        Activity current_activity = activityRepository.findSpecific(id);
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
        CommentForm commentForm = new CommentForm();
        commentForm.setActivity(current_activity);
        commentForm.setAuthor(sportsMan);
        model.addAttribute("current_activity", current_activity);
        model.addAttribute("commentForm", commentForm);
        return "createComment";
    }

    @RequestMapping(value="/addComment", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("CommentForm") CommentForm commentForm) {
        Comment comment = new Comment(commentForm);
        this.commentRepository.save(comment);
        return "events";
    }

}
