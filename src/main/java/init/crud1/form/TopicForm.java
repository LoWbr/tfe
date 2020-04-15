package init.crud1.form;

import init.crud1.entity.Activity;
import init.crud1.entity.SportsMan;

public class TopicForm {

    private String content;
    private SportsMan author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SportsMan getAuthor() {
        return author;
    }

    public void setAuthor(SportsMan author) {
        this.author = author;
    }
}
