package init.crud1.entity;

import init.crud1.form.ActivityTypeForm;

import javax.persistence.*;

@Entity
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = true, nullable = false)
    private Long id;

    @Column(name="name",length = 30)
    private String name;

    @Column(name="met"/*,precision = 4, scale = 2*/)
    private Double met;

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

    public Double getMet() {
        return met;
    }

    public void setMet(Double met) {
        this.met = met;
    }

    public ActivityType(){}

    public void update(ActivityTypeForm activityTypeForm) {
        this.name = activityTypeForm.getName();
        this.met = activityTypeForm.getMet();
    }
}
