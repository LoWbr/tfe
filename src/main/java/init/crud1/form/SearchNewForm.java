package init.crud1.form;

import init.crud1.entity.NewsType;

public class SearchNewForm {

    private String nameSportsman;

    private NewsType newsType;

    public String getNameSportsman() {
        return nameSportsman;
    }

    public void setNameSportsman(String nameSportsman) {
        this.nameSportsman = nameSportsman;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}
