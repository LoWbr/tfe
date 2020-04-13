package init.crud1.form;

import init.crud1.entity.Activity;
import init.crud1.entity.ActivityType;
import init.crud1.entity.Level;
import init.crud1.entity.SportsMan;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityForm {

    private Long id;
    private String name;
    private String description;
    private String plannedTo;
/*    private String hour;*/
    private ActivityType activity;
    private Level minimumLevel;
    private Integer duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlannedTo() {
        return plannedTo;
    }

    public void setPlannedTo(String plannedTo) {
        this.plannedTo = plannedTo;
    }

   /* public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }*/

    public Level getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Level minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ActivityForm() {
    }

    public ActivityForm(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.description = activity.getDescription();
        this.plannedTo = activity.getPlannedTo().toString();
        this.activity = activity.getActivity();
        this.minimumLevel = activity.getMinimumLevel();
        this.duration = activity.getDuration();
    }
}
