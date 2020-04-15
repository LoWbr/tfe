package init.crud1.service;

import init.crud1.entity.News;
import init.crud1.entity.PromotionRequest;
import init.crud1.entity.Role;
import init.crud1.entity.SportsMan;
import init.crud1.repository.NewsRepository;
import init.crud1.repository.PromotionRequestRepository;
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



}
