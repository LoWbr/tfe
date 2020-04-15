package init.crud1.service;

import init.crud1.entity.PromotionRequest;
import init.crud1.entity.SportsMan;
import init.crud1.repository.NewsRepository;
import init.crud1.repository.PromotionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    private NewsRepository newsRepository;
    private PromotionRequestRepository promotionRequestRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, PromotionRequestRepository promotionRequestRepository) {
        this.newsRepository = newsRepository;
        this.promotionRequestRepository = promotionRequestRepository;
    }


}
