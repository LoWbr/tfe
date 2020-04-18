package init.crud1.service;

import init.crud1.entity.News;
import init.crud1.entity.NewsType;
import init.crud1.entity.PromotionRequest;
import init.crud1.entity.SportsMan;
import init.crud1.form.SearchActivityForm;
import init.crud1.form.SearchNewForm;
import init.crud1.repository.NewsRepository;
import init.crud1.repository.PromotionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private NewsRepository newsRepository;
    private PromotionRequestRepository promotionRequestRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, PromotionRequestRepository promotionRequestRepository) {
        this.newsRepository = newsRepository;
        this.promotionRequestRepository = promotionRequestRepository;
    }

    public List<News> findForSearch(SearchNewForm searchNewForm){
        return this.newsRepository.filter(searchNewForm.getNameSportsman(), searchNewForm.getNewsType());
    }

    public List<NewsType> getAllNewsType(){
        List<NewsType> allTypes = new ArrayList<>();
        for (NewsType newsType:NewsType.values()) {
            allTypes.add(newsType);
        }
        return allTypes;
    }

    public Iterable<News> findAll(){
        return this.newsRepository.findAll();
    }
}
