package init.crud1.form;

import init.crud1.entity.ActivityType;
import init.crud1.entity.Level;

public class SearchActivityForm {

    private ActivityType activity;
    private Level minimumLevel;

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public Level getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Level minimumLevel) {
        this.minimumLevel = minimumLevel;
    }
}
