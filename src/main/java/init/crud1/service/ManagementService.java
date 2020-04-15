package init.crud1.service;

import init.crud1.entity.*;
import init.crud1.repository.ActivityTypeRepository;
import init.crud1.repository.NewsRepository;
import init.crud1.repository.PromotionRequestRepository;
import init.crud1.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void saveTopic(Topic topic) {
        this.topicRepository.save(topic);
    }

    public ActivityType findSpecificActivityType(Long id) {
        return this.activityTypeRepository.findSpecific(id);
    }

    public void saveType(ActivityType activityType) {
        this.activityTypeRepository.save(activityType);
    }
}
