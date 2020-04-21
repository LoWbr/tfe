package init.crud1.form;

import init.crud1.entity.Activity;
import init.crud1.entity.SportsMan;

public class CommentForm {

    private String content;
    private SportsMan author;
    private Activity activity;

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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public CommentForm(SportsMan author, Activity activity) {
        this.author = author;
        this.activity = activity;
    }
}
