package init.crud1.entity;

import init.crud1.form.ActivityForm;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = true, nullable = false)
    private Long id;

    @Column(name = "name", length = 60)
    private String name;

    @Column(name="description", columnDefinition="TEXT")
    private String description;

    @Column(name = "plannedTo")
    private LocalDate plannedTo;

/*    @Column(name = "hour", columnDefinition = "DATE")
    private LocalDateTime hour;*/

    @OneToOne
    @JoinColumn(name="fk_type_activity", referencedColumnName = "id", nullable = false)
    private ActivityType activity;

    @OneToOne
    @JoinColumn(name="fk_minimum_level", referencedColumnName = "id", nullable = false)
    private Level minimumLevel;

    @Column(name="duration", nullable = true)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name="fk_creator", referencedColumnName = "id", nullable = false)
    private SportsMan creator;

    @ManyToMany
    @JoinColumn(name="fk_user_candidate", referencedColumnName = "id", nullable = false)
    private List<SportsMan> candidate;

    @ManyToMany
    @JoinColumn(name="fk_user_registered", referencedColumnName = "id", nullable = false)
    private List<SportsMan> registered;

    @Column(name="open", nullable = false, columnDefinition = "boolean default true")
    private Boolean open;

    @Column(name="over", nullable = false, columnDefinition = "boolean default false")
    private Boolean over;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getActivityName() {
        return activity.getName();
    }

    public SportsMan getCreator() {
        return creator;
    }

    public void setCreator(SportsMan creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creator.getFirstName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPlannedTo() {
        return plannedTo;
    }

    public void setPlannedTo(LocalDate plannedTo) {
        this.plannedTo = plannedTo;
    }

   /* public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }*/

    public List<SportsMan> getRegistered() {
        return registered;
    }

    public void setRegistered(List<SportsMan> registered) {
        this.registered = registered;
    }

    public Level getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Level minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public List<SportsMan> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<SportsMan> candidate) {
        this.candidate = candidate;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getOver() {
        return over;
    }

    public void setOver(Boolean over) {
        this.over = over;
    }

    public Activity() {
    }

    public Activity(ActivityForm activityForm, SportsMan sportsMan) throws ParseException {
        this.name = activityForm.getName();
        this.description = activityForm.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.plannedTo = LocalDate.parse(activityForm.getPlannedTo(),formatter).plusDays(1);
        /*DateTimeFormatter sdf = DateTimeFormatter.ofPattern("hh:mm:ss");
        this.hour = LocalTime.parse(activityForm.getHour(), sdf);*/
        this.minimumLevel = activityForm.getMinimumLevel();
        this.duration = activityForm.getDuration();
        this.activity = activityForm.getActivity();
        this.creator = sportsMan;
        this.open = true;
        this.over = false;
    }

    public void addParticipant(SportsMan sportsMan) {
        this.registered.add(sportsMan);
    }

    public void removeParticipant(SportsMan sportsMan) {
        this.registered.remove(sportsMan);
    }

    public void update(ActivityForm activityForm) {
        this.name = activityForm.getName();
        this.activity = activityForm.getActivity();
        this.description = activityForm.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.plannedTo = LocalDate.parse(activityForm.getPlannedTo(),formatter).plusDays(1);
        this.duration = activityForm.getDuration();
        this.minimumLevel = activityForm.getMinimumLevel();
    }

    public void closeEvent() {
        this.setOver(true);
    }

    public boolean checkLevel(SportsMan sportsMan){
        if(sportsMan.getLevel().getPlace() >= this.minimumLevel.getPlace() )
            return true;
        else
            return false;
    }

    public boolean checkPresence(SportsMan sportsMan){
        if (this.getCandidate().contains(sportsMan) || this.getRegistered().contains(sportsMan))
            return false;
        else
            return true;
    }

}
