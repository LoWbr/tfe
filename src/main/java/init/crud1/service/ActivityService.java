package init.crud1.service;

import init.crud1.entity.*;
import init.crud1.form.SearchActivityForm;
import init.crud1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    ActivityRepository activityRepository;
    ActivityTypeRepository activityTypeRepository;
    StatisticRepository statisticRepository;
    CommentRepository commentRepository;
    LevelRepository levelRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, ActivityTypeRepository activityTypeRepository, StatisticRepository statisticRepository, CommentRepository commentRepository, LevelRepository levelRepository) {
        this.activityRepository = activityRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.statisticRepository = statisticRepository;
        this.commentRepository = commentRepository;
        this.levelRepository = levelRepository;
    }

    //AllEvents
    public Iterable<Activity> getAllActivities(){
        return this.activityRepository.findAll();
    }

    //FindSpecificActivity
    public Activity getSpecificActivity(Long id){
        return this.activityRepository.findSpecific(id);
    }

    //FindByCreator
    public List<Activity> getAllOfTheSameCreator(SportsMan sportsMan){
        return this.activityRepository.findByCreator(sportsMan);
    }

    //FindForSearch
    public List<Activity> findForSearch(SearchActivityForm searchActivityForm){
        return this.activityRepository.filter(searchActivityForm.getActivity(), searchActivityForm.getMinimumLevel());
    }

    //AllActivityKinds
    public Iterable<ActivityType> getAllActivityTypes(){
        return this.activityTypeRepository.findAll();
    }

    //AllLevels
    public Iterable<Level> getAllLevels(){
        return this.levelRepository.findAll();
    }

    //SaveActivity
    public void saveActivity(Activity activity){
        this.activityRepository.save(activity);
    }

    //FindCommentForActivity
    public Iterable<Comment> findCommentsForActivity(Activity activity){
        return this.commentRepository.findForEvent(activity);
    }


}
