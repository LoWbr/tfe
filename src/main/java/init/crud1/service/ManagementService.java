package init.crud1.service;

import init.crud1.entity.*;
import init.crud1.form.ActivityTypeForm;
import init.crud1.form.LevelForm;
import init.crud1.form.TopicForm;
import init.crud1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManagementService {

    @Autowired
    PromotionRequestRepository promotionRequestRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    LevelRepository levelRepository;

    public void saveRequest(PromotionRequest promotionRequest){
        this.promotionRequestRepository.save(promotionRequest);
    }

    public PromotionRequest findSpecific(SportsMan sportsMan){
       return this.promotionRequestRepository.findByCandidate(sportsMan);
    }

    public void removeRequest(PromotionRequest promotionRequest){
        this.promotionRequestRepository.delete(promotionRequest);
    }

    public void saveNews(News news){
        this.newsRepository.save(news);
    }

    //Contains User
    public List<SportsMan> getPromotionCandidates(){
        List<SportsMan> candidates = new ArrayList<>();
        Iterable<PromotionRequest> allPromotions = this.promotionRequestRepository.findAll();
        for (PromotionRequest promotion: allPromotions) {
            candidates.add(promotion.getCandidate());
        }
        return candidates;
    }

    public void addTopic(SportsMan sportsMan, TopicForm topicForm) {

        Topic topic = new Topic(sportsMan, topicForm);
        this.topicRepository.save(topic);
    }

    public ActivityType findSpecificActivityType(Long id) {
        return this.activityTypeRepository.findSpecific(id);
    }

    public void createType(ActivityTypeForm activityTypeForm){
        ActivityType activityType = new ActivityType();
        activityType.update(activityTypeForm);
        this.saveType(activityType);
    }

    public void updateType(ActivityType activityType, ActivityTypeForm activityTypeForm){
        activityType.update(activityTypeForm);
        this.saveType(activityType);
    }


    public void saveType(ActivityType activityType) {
        this.activityTypeRepository.save(activityType);
    }

    public Level findSpecificLevel(Long id) {
        return this.levelRepository.findSpecific(id);
    }

    //AllLevels
    public Iterable<Level> getAllLevels(){
        return this.levelRepository.findAll();
    }


    public void updateLevel(LevelForm levelForm, Level level){
        level.update(levelForm);
        this.saveLevel(level);
    }

    public void saveLevel(Level level) {
        this.levelRepository.save(level);
    }

    public void getDBStatus() {
        System.out.println("Backup Started at " + new Date());

        Date backupDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String backupDateStr = format.format(backupDate);
        String dbNameList = "tfe";

        String fileName = "Daily_DB_Backup"; // default file name
        String folderPath = "/home/laurent/ultimateProjects/phase3/tfe_repo";
        File f1 = new File(folderPath);
        f1.mkdir(); // create folder if not exist

        String saveFileName = fileName + /*"_" + backupDateStr + */".sql";
        String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

        String executeCmd = "mysqldump -u " + "lolo" + " -p" + "lolo" + "  --databases " + dbNameList
                + " -r " + savePath;

        Process runtimeProcess = null;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int processComplete = 0;
        try {
            processComplete = runtimeProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (processComplete == 0) {
            System.out.println("Backup Complete at " + new Date());
        } else {
            System.out.println("Backup Failure");
        }

    }
}
