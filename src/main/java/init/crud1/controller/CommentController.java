package init.crud1.controller;

import init.crud1.entity.Activity;
import init.crud1.entity.Comment;
import init.crud1.entity.NewsType;
import init.crud1.entity.SportsMan;
import init.crud1.form.CommentForm;
import init.crud1.service.ActivityService;
import init.crud1.service.NewsService;
import init.crud1.service.SportsManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller

public class CommentController {

    ActivityService activityService;
    SportsManService sportsManService;
    NewsService newsService;

    @Autowired
    public CommentController(ActivityService activityService, SportsManService sportsManService,
                             NewsService newsService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
        this.newsService = newsService;
    }

    @RequestMapping(value = "/createComment{id}", method = RequestMethod.GET)
    public String createComment(@RequestParam Long id, Model model, Principal principal) {
        model.addAttribute("current_activity", activityService.getSpecificActivity(id));
        model.addAttribute("commentForm",
                activityService.initiateCommentForm(activityService.getSpecificActivity(id),
                        sportsManService.findCurrentUser(principal.getName())));
        return "createComment";
    }

    @RequestMapping(value="/addComment", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("CommentForm") CommentForm commentForm) {
        Comment comment = new Comment(commentForm);
        this.activityService.createComment(comment);
        this.newsService.returnCommentEventNew(comment.getAuthor(), comment.getActivity(), NewsType.COMMENTED_EVENT);
        return "events";
    }

}
