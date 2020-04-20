package init.crud1.service;

import init.crud1.entity.*;
import init.crud1.form.ActivityForm;
import init.crud1.form.SearchActivityForm;
import init.crud1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ActivityService {

    ActivityRepository activityRepository;
    ActivityTypeRepository activityTypeRepository;
    StatisticRepository statisticRepository;
    CommentRepository commentRepository;
    LevelRepository levelRepository;
    NewsService newsService;
    SportsManService sportsManService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, ActivityTypeRepository activityTypeRepository,
                           StatisticRepository statisticRepository, CommentRepository commentRepository,
                           LevelRepository levelRepository, SportsManService sportsManService,
                           NewsService newsService) {
        this.activityRepository = activityRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.statisticRepository = statisticRepository;
        this.commentRepository = commentRepository;
        this.levelRepository = levelRepository;
        this.sportsManService = sportsManService;
        this.newsService = newsService;
    }

    //----ACTIVITY, QUERIES----//

    //AllEvents
    public Iterable<Activity> getAllActivities(){
        return this.activityRepository.findAll();
    }

    //FindSpecificEvent
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

    //SaveEvent
    public void saveActivity(Activity activity){
        this.activityRepository.save(activity);
    }

    //CreateEvent
    public void createActivity(ActivityForm activityForm, SportsMan sportsMan) throws ParseException {
        Activity activity = new Activity(activityForm, sportsMan);
        this.saveActivity(activity);
    }

    //UpdateEvent
    public void updateActivity(Activity activity, ActivityForm activityForm){
        activity.update(activityForm);
        this.saveActivity(activity);
    }

    //AddSportsManCandidate
    public void applyAsCandidate(Activity activity, SportsMan sportsMan){
        activity.getCandidate().add(sportsMan);
        this.newsService.returnApplicationEventNew(activity, sportsMan, NewsType.APPLY_FOR_EVENT);
        this.saveActivity(activity);
    }

    public void refuseBuyer(Activity activity, SportsMan sportsMan) {
        activity.getCandidate().remove(sportsMan);
        this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.REFUSED_REGISTRATION);
        this.saveActivity(activity);
    }

    //ManagingParticipants
    public void addOrRemoveParticipants(Activity activity, SportsMan sportsMan, boolean flag){
        if(flag){
            activity.addParticipant(sportsMan);
            activity.getCandidate().remove(sportsMan);
            this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.VALIDED_REGISTRATION);
            //Rajouter activity et creator
        }
        else{
            activity.removeParticipant(sportsMan);
            this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.CANCEL_REGISTRATION);
        }
        this.saveActivity(activity);
    }

    //Close Activity
    public void closeActivity(Activity activity) {
        activity.closeEvent();
        newsService.returnCancelledApplictionNewOrCloseEventNew(activity,NewsType.DONE_EVENT);
        for (SportsMan sportsman : activity.getRegistered()) {
            double durationInHours = (double) activity.getDuration() / 60;
            Integer energeticExpenditure = Math.toIntExact(Math.round(sportsman.getWeight() * durationInHours * activity.getActivity().getMet()));
            sportsman.setPoints(energeticExpenditure);
            if (sportsman.checkLevelStatus()){
                newsService.returnApplicationResultNewOrLevelUpNew(sportsman,NewsType.LEVEL_UP);
                Long new_place = sportsman.getLevel().getPlace()+1;
                sportsman.setLevel(sportsManService.findSpecificLevel(new_place));
            }
            sportsManService.saveUser(sportsman);
            Statistic statistic = new Statistic(sportsman, activity, energeticExpenditure);
            sportsManService.saveStatistic(statistic);
        }
        this.saveActivity(activity);
    }





    //AllActivityKinds
    public Iterable<ActivityType> getAllActivityTypes(){
        return this.activityTypeRepository.findAll();
    }

    //AllLevels
    public Iterable<Level> getAllLevels(){
        return this.levelRepository.findAll();
    }



    //FindCommentForActivity
    public Iterable<Comment> findCommentsForActivity(Activity activity){
        return this.commentRepository.findForEvent(activity);
    }

}
